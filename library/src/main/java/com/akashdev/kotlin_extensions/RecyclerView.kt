package com.akashdev.kotlin_extensions

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.hideViewOnScroll(view: View) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0 && view.isVisible) {
                view.hide()
            } else if (dy < 0 && !view.isVisible) {
                view.show()
            }
        }
    })
}
