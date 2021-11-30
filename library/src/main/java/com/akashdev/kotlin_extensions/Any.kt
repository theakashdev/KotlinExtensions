package com.akashdev.kotlin_extensions

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import com.google.gson.Gson
import kotlin.math.roundToInt


/** returns integer dimensional value from the integer px value. */
internal val Int.dp: Int
    @JvmSynthetic inline get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
        Resources.getSystem().displayMetrics
    ).roundToInt()


internal val <E> List<E>?.countOrZero: Int
    get() = this?.size ?: 0

fun <A, B> Pair<A, B>?.isNotNull(): Boolean {
    return this != null
}

fun <A, B> Pair<A, B>?.isNull(): Boolean {
    return this == null
}

inline fun <reified T> jsonStringToObject(jsonString: String): T {
    return Gson().fromJson(jsonString, T::class.java)
}

val <T : Any> T.TAG get() = this::class.simpleName


fun getInstallerPackageName(context: Context, packageName: String): String? {
    return runCatching {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            return context.packageManager.getInstallSourceInfo(packageName).installingPackageName
        @Suppress("DEPRECATION")
        context.packageManager.getInstallerPackageName(packageName)
    }.getOrNull()
}
