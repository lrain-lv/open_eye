package com.app.eye.binding

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R


@BindingAdapter("isRefresh")
fun SwipeRefreshLayout.isRefresh(isRefresh: Boolean) {
    isRefreshing = isRefresh
}

@BindingAdapter("onRefreshClick")
fun SwipeRefreshLayout.onRefreshClick(onClick: () -> Unit) {
    setOnRefreshListener { onClick.invoke() }
}

@BindingAdapter("colors")
fun SwipeRefreshLayout.colors(res: Int = R.array.google_colors) {
    val intArray = resources.getIntArray(R.array.google_colors)
    setColorSchemeColors(*intArray)
}

@BindingAdapter("isEnable")
fun SwipeRefreshLayout.isEnable(isEnable: Boolean) {
    isEnabled = isEnable
}