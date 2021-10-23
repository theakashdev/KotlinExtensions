package com.akashdev.kotlin_extensions

import android.content.Context
import android.os.Build
import com.google.gson.Gson


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
