package com.app.eye.binding

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:isVisible")
fun View.isVisible(b: Boolean) {
    visibility = if (b) View.VISIBLE else View.GONE
}