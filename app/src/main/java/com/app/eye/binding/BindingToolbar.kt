package com.app.eye.binding

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

@BindingAdapter( "app:resId", "app:onNavClick", requireAll = true)
fun Toolbar.title( resId: Int, action: () -> Unit) {
    setNavigationIcon(resId)
    setNavigationOnClickListener { action.invoke() }
}