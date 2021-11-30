package com.akashdev.kotlin_extensions

import android.view.View
import android.widget.ScrollView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.hideViewOnScroll(view: View) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0 && view.isVisible) {
                view.hideWithAnimation(500)
            } else if (dy < 0 && !view.isVisible) {
                view.showWithAnimation(500)
            }
        }
    })
}

fun ScrollView.hideViewOnScroll(view: View) {
    setOnScrollChangeListener { _, scrollX, scrollY, _, oldScrollY ->
        if (scrollY > oldScrollY) {
            if (scrollY > 0 && view.isVisible) {
                view.hideWithAnimation(500)
            } else if (scrollY < 0 && !view.isVisible) {
                view.showWithAnimation(500)
            }
        }
    }
}
