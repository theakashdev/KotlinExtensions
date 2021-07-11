package com.akashdev.kotlin_extensions

import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar


val ChipGroup.checkedChipText: String?
    get() = findViewById<Chip>(checkedChipId)?.text?.toString()

val ChipGroup.checkedChipTag: String?
    get() = findViewById<Chip>(checkedChipId)?.tag?.toString()

val ChipGroup.checkedChip: Chip?
    get() = findViewById<Chip>(checkedChipId)


fun View.toggleVisibilityWithAnimation() {
    animate().setDuration(150).alpha(if (isVisible) 0f else 1f)
    if (isVisible) hide() else showPopupWindow()
}

// View Visible
fun View.showPopupWindow(animate: Boolean? = false, duration: Long? = 300) {
    if (!isVisible) {
        if (animate == true) animate().setDuration(duration!!).alpha(1f)
        visibility = View.VISIBLE
    }
}

// View Invisible
fun View.hide(animate: Boolean? = false, duration: Long? = 300) {
    if (isVisible) {
        if (animate == true) animate().setDuration(duration!!).alpha(0f)
        visibility = View.GONE
    }
}


// View Invisible
fun View.invisible(animate: Boolean? = false, duration: Long? = 300) {
    if (isVisible) {
        if (animate == true) animate().setDuration(duration!!).alpha(0f)
        visibility = View.INVISIBLE
    }
}


fun View.disable() {
    this.isEnabled = false
    this.alpha = 0.5f
}


fun View.enable() {
    this.isEnabled = true
    this.alpha = 1f
}

//snackbar
fun View.snackbar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
}
