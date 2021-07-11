package com.akashdev.kotlin_extensions

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment

fun Fragment.inflate(
    layoutId: Int,
    viewGroup: ViewGroup? = null,
    attachToRoot: Boolean = false,
): View = inflate(layoutId, viewGroup, attachToRoot)


fun Fragment.openActivity(activity: Class<*>, flags: Int? = Intent.FLAG_ACTIVITY_NEW_TASK) {
    context?.openActivity(activity, flags)
}

fun Fragment.isPhoneLocked(): Boolean = context?.isPhoneLocked() == true

fun Fragment.isOnline() = context?.isOnline()

fun Fragment.getDrawable(resId: Int) = AppCompatResources.getDrawable(requireContext(), resId)

fun Fragment.defaultSmsApp(): String? = context?.defaultSmsApp()

fun Fragment.defaultCallApp(): String? = context?.defaultCallApp()

fun Fragment.getAllAppPackages(category: String? = Intent.CATEGORY_LAUNCHER): MutableList<ResolveInfo>? =
    context?.getAllAppPackages(category)

fun Fragment.defaultLauncherApp(): String? = context?.defaultLauncherApp()

fun Fragment.getAllKeyboardAppPackages(): List<String>? = context?.getAllKeyboardAppPackages()

fun Fragment.defaultKeyboard(): String? = context?.defaultKeyboard()

fun Fragment.getAppNameByPackage(pkgName: String): String? = context?.getAppNameByPackage(pkgName)

fun Fragment.isBrowserApp(packageName: String): Boolean = context?.isBrowserApp(packageName) == true

fun Fragment.getListOfBrowser() = context?.getListOfBrowser()

fun Fragment.hideSoftKeyboard(view: View) = context?.hideSoftKeyboard(view)

fun Fragment.openURL(url: String, flags: Int) = context?.openURL(url, flags)

fun Fragment.redirectToBrowser(pkgName: String, url: String) = context?.redirectToBrowser(pkgName, url)

fun Fragment.toast(text: String) = requireContext().toast(text)

fun Fragment.toast(resId: Int) = requireContext().toast(getString(resId))

fun Fragment.share(message: String, appId: String? = null) = context?.share(message, appId)

fun Fragment.isPackageInstalled(packageName: String) = context?.isPackageInstalled(packageName)

fun Fragment.isSystemApp(packageName: String) = context?.isSystemApp(packageName)

fun Fragment.email(emails: Array<String>, subject: String, message: String? = null) =
    context?.email(emails, subject, message)

fun Fragment.isNewlyInstallApp(packageName: String): Boolean =
    context?.isNewlyInstallApp(packageName) == true

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T = this.apply {
    arguments = Bundle().apply(argsBuilder)
}