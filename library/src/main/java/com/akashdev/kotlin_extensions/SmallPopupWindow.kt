package com.akashdev.kotlin_extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat


fun Context.showPopupWindow(
    anchorView: View,
    textResId: Int? = null,
    text: String? = null,
    background: Int? = null
) {
    val drawable = BubbleDrawable().also {
        it.setTriangleWidth(40F)
        it.setTriangleHeight(25F)
        it.setCorners(floatArrayOf(20f, 20f, 20f, 20f))
        it.setStrokeWidth(0f)
        it.setSolidColor(ContextCompat.getColor(this, background ?: android.R.color.holo_blue_dark))
        it.setTriangleBias(0.925f)
        it.setTriangleLocation(BubbleDrawable.locTop)
    }
    showPopupWindow(
        anchorView = anchorView,
        drawable = drawable,
        message = textResId?.let { getString(it) } ?: text!!
    )
}


fun Context.showPopupWindow(
    anchorView: View,
    drawable: Drawable,
    message: String
) {
    val inflater = LayoutInflater.from(this)
    val layout = inflater.inflate(R.layout.info_popup_window, null)
    val textView = layout.findViewById<TextView>(R.id.txtFirstMessage)
    textView.background = drawable
    textView.text = message

    val popup = PopupWindow(
        anchorView,
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT
    )

    popup.contentView = layout

    //animation
    val fade = Fade()
    fade.duration = 300
    popup.enterTransition = fade

    // Closes the popup window when touch outside of it - when looses focus
    popup.isOutsideTouchable = true
    popup.isFocusable = true

    popup.showAsDropDown(anchorView, 0, -20)
}
