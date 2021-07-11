package com.akashdev.kotlin_extensions

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("android:goneUnless")
fun goneUnless(view: View, visibility: Int) {
    if (visibility == View.VISIBLE) view.hide() else view.show()
}

@BindingAdapter("android:invisibleUnless")
fun invisibleUnless(view: View, visibility: Int) {
    if (visibility == View.VISIBLE) view.invisible() else view.show()
}

@BindingAdapter("drawableStart")
fun drawableStart(view: TextView, drawable: Drawable) {
    view.setDrawable(start = drawable)
}

@BindingAdapter("drawableEnd")
fun drawableEnd(view: TextView, drawable: Drawable) {
    view.setDrawable(end = drawable)
}

@BindingAdapter("drawableTop")
fun drawableTop(view: TextView, drawable: Drawable) {
    view.setDrawable(top = drawable)
}

@BindingAdapter("drawableBottom")
fun drawableBottom(view: TextView, drawable: Drawable) {
    view.setDrawable(bottom = drawable)
}


@BindingAdapter("hideErrorOnTextChanged")
fun hideErrorOnTextChanged(editText: EditText, boolean: Boolean) {
    editText.doAfterTextChanged { editText.error = null }
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

@BindingAdapter("loadImageFromURL", "placeholder")
fun loadImageFromURL(imageView: ImageView, url: String?, placeholder: Drawable?) {
    Glide.with(imageView)
        .load(url)
        .placeholder(placeholder)
        .into(imageView)
}

@BindingAdapter("imageResource", "imageDrawable")
fun imageResource(imageView: ImageView, resource: Int?, drawable: Drawable?) {
    resource?.let { imageView.setImageResource(it) }
    drawable?.let { imageView.setImageDrawable(it) }

}

