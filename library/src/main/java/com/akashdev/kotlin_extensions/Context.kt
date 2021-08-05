package com.akashdev.kotlin_extensions

import android.app.KeyguardManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.provider.Browser
import android.provider.Settings
import android.provider.Telephony
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.net.toUri

fun Context.openActivity(activity: Class<*>, flags: Int? = null): Intent {
    return Intent(this, activity).apply {
        flags?.let { this.flags = it }
        startActivity(this)
    }
}

fun Context.openActivity(action: String, flags: Int? = null): Intent {
    return Intent(action).apply {
        flags?.let { this.flags = it }
        startActivity(this)
    }
}

fun Context.isPhoneLocked(): Boolean {
    return (getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager).isKeyguardLocked
}


fun Context.isOnline(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return cm.activeNetwork?.let {
        val nc = cm.getNetworkCapabilities(it)
        nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    } ?: false
}

fun Context.defaultSmsApp(): String? = Telephony.Sms.getDefaultSmsPackage(this)

fun Context.defaultCallingApp(): String? {
    val intent = Intent(Intent.ACTION_DIAL).addCategory(Intent.CATEGORY_DEFAULT)
    val resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
    return resolveInfo?.activityInfo?.packageName
}

fun Context.getAllAppPackages(category: String? = Intent.CATEGORY_LAUNCHER): MutableList<ResolveInfo> {
    val intent = Intent(Intent.ACTION_MAIN).apply { addCategory(category) }
    return packageManager.queryIntentActivities(intent, 0)
}

fun Context.defaultLauncherApp(): String? {
    val intent = Intent(Intent.ACTION_MAIN).apply { addCategory(Intent.CATEGORY_HOME) }
    val resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
    return resolveInfo?.activityInfo?.packageName
}

fun Context.getAllKeyboardAppPackages(): List<String> {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return inputMethodManager.enabledInputMethodList
        .map { it.packageName }
        .minus("com.google.android.googlequicksearchbox")
}

fun Context.defaultKeyboard(): String {
    return Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
        .split("/")[0]
}

fun Context.getAppNameByPackage(pkgName: String): String? {
    return try {
        val packageManager = packageManager
        val info = packageManager.getApplicationInfo(pkgName, PackageManager.GET_META_DATA)
        packageManager.getApplicationLabel(info).toString()
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }
}

fun Context.getAppIconByPackageName(packageName: String) = try {
    packageManager.getApplicationIcon(packageName)
} catch (e: PackageManager.NameNotFoundException) {
    null
}

fun Context.isBrowserApp(packageName: String): Boolean {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("http://www.google.com")
    intent.setPackage(packageName)
    val resolveInfo = packageManager.resolveActivity(intent, 0)

    return resolveInfo?.activityInfo?.packageName == packageName
}

fun Context.getInstallBrowsersList(): List<String> = try {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("http://www.google.com")
    val browserList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
    browserList.map { it.activityInfo.packageName }
} catch (e: Exception) {
    emptyList()
}

//Open URL
fun Context.openURL(url: String, flags: Int? = null) {
    try {
        Intent(Intent.ACTION_VIEW, url.toUri()).apply {
            flags?.let { this.flags = it }
            startActivity(this)
        }
    } catch (e: ActivityNotFoundException) {
        toast("URL not valid")
    }
}


fun Context.redirectToBrowser(packageName: String, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        setPackage(packageName)
        putExtra(Browser.EXTRA_APPLICATION_ID, packageName)
    }
    try {
        startActivity(intent)
    } catch (e: Exception) {
        Log.d(TAG, "redirectBrowser: error=${e.message}")
    }
}

//Toast
fun Context.toast(data: Any) = Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show()
fun Context.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()
fun Context.toast(resId: Int) = toast(getString(resId))


// Share
fun Context.share(message: String, appId: String? = null) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, message.replace("\\n", "\n"))
    intent.putExtra(Intent.EXTRA_TITLE, message.replace("\\n", "\n"))
    appId?.let { intent.setPackage(it) }

    when {
        appId.isNullOrEmpty() -> startActivity(intent)
        isPackageInstalled(appId) -> startActivity(intent)
        else -> toast("App not installed")
    }
}


// Check Package Installed
fun Context.isPackageInstalled(packageName: String) = try {
    packageManager.getPackageInfo(packageName, 0)
    true
} catch (e: PackageManager.NameNotFoundException) {
    false
}


fun Context.isSystemApp(packageName: String) = try {
    val info = packageManager.getApplicationInfo(packageName, 0)
    info.flags and ApplicationInfo.FLAG_SYSTEM != 0
} catch (e: PackageManager.NameNotFoundException) {
    false
}


// Send Email
fun Context.email(emails: Array<String>, subject: String, message: String? = null) {
    Intent(Intent.ACTION_SENDTO, "mailto:".toUri()).run {
        putExtra(Intent.EXTRA_EMAIL, emails)
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, message)
        if (resolveActivity(packageManager) != null) {
            startActivity(this)
        }
    }
}

fun Context.isNewlyInstallApp(packageName: String): Boolean {
    return try {
        val firstInstallTime = packageManager.getPackageInfo(packageName, 0).firstInstallTime
        val lastUpdateTime = packageManager.getPackageInfo(packageName, 0).lastUpdateTime
        firstInstallTime == lastUpdateTime
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        true
    }
}

fun Context.hideSoftKeyboard(view: View) {
    if (view.hasFocus()) {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText) imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Context.getDrawable(resId: Int) = AppCompatResources.getDrawable(this, resId)

fun Context.inflate(
    layoutId: Int,
    viewGroup: ViewGroup? = null,
    attachToRoot: Boolean = false,
): View = LayoutInflater.from(this).inflate(layoutId, viewGroup, attachToRoot)
