package com.akashdev.kotlin_extensions

import android.util.Patterns
import android.webkit.URLUtil
import androidx.core.text.trimmedLength
import java.net.URLDecoder
import java.util.*

fun String.findAnyOfWithRegex(strings: List<String>): String? {
    return strings.find { string ->
        if (string.contains(".*")) this.contains(string.toRegex()) else this.contains(string, true)
    }?.removeSpecialChar()
}

fun String.isValidEmail(): Boolean = isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidURL() = URLUtil.isValidUrl(this) && Patterns.WEB_URL.matcher(this).matches()

fun String.removeSpecialChar(removeSpaces: Boolean? = false): String {
    val str = replace("%(?![0-9a-fA-F]{2})".toRegex(), "")
    val decoded = URLDecoder.decode(str, "UTF-8")
    val regex = "[\"\\]\\[\\\\\$*+-.,~_;`^':!?<>/|›#&@{}()]".toRegex()

    val replacementOfSpaces = if (removeSpaces == true) "" else " "
    return decoded.lowercase().replace(regex, "").replace("\\s+".toRegex(), replacementOfSpaces)
}



val String.intValue get() = this.replace("\\D+".toRegex(), "")

fun String.capitalizeFirstLetter(): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}

fun String.removeWhitespaces() = replace(" ", "")

/**
 *
 * Returns Appended three dots(...) at the end of the string if string length > [n]
 * Or Returns string without dots(...) if string length <= [n]
 * */
fun String.takeWithDotted(n: Int): String {
    return if (trimmedLength() > n) "${take(n)}..." else this
}


