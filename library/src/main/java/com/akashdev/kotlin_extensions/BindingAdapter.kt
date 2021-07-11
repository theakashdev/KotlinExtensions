package com.akashdev.kotlin_extensions

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("android:goneUnless")
fun goneUnless(view: View, visibility: Int) {
    if (visibility == View.VISIBLE) view.hide() else view.showPopupWindow()
}

@BindingAdapter("android:invisibleUnless")
fun invisibleUnless(view: View, visibility: Int) {
    if (visibility == View.VISIBLE) view.invisible() else view.showPopupWindow()
}

@BindingAdapter("drawableEnd")
fun drawableEnd(view: TextView, drawable: Drawable) {
    view.setDrawable(right = drawable)
}


@BindingAdapter("hideErrorOnTextChanged")
fun hideErrorOnTextChanged(editText: EditText, boolean: Boolean) {
    editText.doAfterTextChanged { editText.error = null }
}

@BindingAdapter("checked")
fun switchChecked(switch: SwitchCompat, checked: Boolean) {
    switch.isChecked = checked
}

@BindingAdapter("startMarquee")
fun startMarquee(textView: TextView, _isSelected: Boolean) {
    with(textView) {
        ellipsize = TextUtils.TruncateAt.MARQUEE
        marqueeRepeatLimit = -1
        isSelected = _isSelected
        isSingleLine = true
        isFocusableInTouchMode = true
    }
}

@BindingAdapter("loadImageFromURL")
fun loadImageFromURL(imageView: ImageView, url: String?, placeholder: Drawable?) {
    Glide.with(imageView)
        .load(url)
        .placeholder(placeholder)
        .into(imageView)
}

@BindingAdapter("imageResource")
fun imageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}


@BindingAdapter("imageResource")
fun imageResource(imageView: ImageView, drawable: Drawable) {
    imageView.setImageDrawable(drawable)
}

