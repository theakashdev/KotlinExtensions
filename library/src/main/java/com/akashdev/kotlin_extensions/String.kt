package com.akashdev.kotlin_extensions

import android.util.Patterns
import android.webkit.URLUtil
import java.net.URLDecoder


fun String.findAnyOfWithRegex(strings: List<String>): String? {
    return strings.find { if (it.contains("*")) this.contains(it.toRegex()) else this.contains(it) }
        ?.removeSpecialChar()
}

fun String.isValidEmail(): Boolean = isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidURL() = URLUtil.isValidUrl(this) && Patterns.WEB_URL.matcher(this).matches()

fun String.removeSpecialChar(): String {
    val str = replace("%(?![0-9a-fA-F]{2})".toRegex(), "")
    val decoded = URLDecoder.decode(str, "UTF-8")
    val specialCharRegex = "[ \"\\]\\[12345678\\\\90~!@#$^&*()+-_;',./:<>?{}|›]".toRegex()
    return decoded.lowercase().replace(specialCharRegex, "")
}


val String.intValue get() = this.replace("\\D+".toRegex(), "")
