package com.akashdev.kotlin_extensions

import android.graphics.drawable.Drawable
import android.widget.TextView

val TextView.value get() = text.toString().trim()

fun TextView.setDrawable(
    start: Int? = 0,
    top: Int? = 0,
    end: Int? = 0,
    bottom: Int? = 0
) = setCompoundDrawablesWithIntrinsicBounds(start!!, top!!, end!!, bottom!!)

fun TextView.setDrawable(
    start: Drawable? = null,
    top: Drawable? = null,
    end: Drawable? = null,
    bottom: Drawable? = null
) = setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
