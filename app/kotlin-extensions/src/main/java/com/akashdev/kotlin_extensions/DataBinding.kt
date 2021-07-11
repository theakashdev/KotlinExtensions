package com.akashdev.kotlin_extensions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


inline fun <reified T : ViewDataBinding?> ViewGroup?.bind(
    layout: Int, inflater: LayoutInflater? = null,
): T = DataBindingUtil.inflate(inflater ?: LayoutInflater.from(this?.context), layout, this, false)
