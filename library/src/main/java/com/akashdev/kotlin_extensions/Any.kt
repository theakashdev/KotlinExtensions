package com.akashdev.kotlin_extensions

import com.google.gson.Gson
import java.util.*


fun <A, B> Pair<A, B>?.isNotNull(): Boolean {
    return this != null
}

fun <A, B> Pair<A, B>?.isNull(): Boolean {
    return this == null
}

inline fun <reified T> jsonStringToObject(jsonString: String): T {
    return Gson().fromJson(jsonString, T::class.java)
}

fun currentClockMinutes(): Int {
    val calendar = Calendar.getInstance(Locale.getDefault())
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minutes = calendar.get(Calendar.MINUTE)
    return hour * 60 + minutes
}

val <T : Any> T.TAG get() = this::class.simpleName
