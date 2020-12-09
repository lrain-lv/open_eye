package com.app.eye.binding

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R


@BindingAdapter("app:isRefresh")
fun SwipeRefreshLayout.isRefresh(isRefresh: Boolean) {
    isRefreshing = isRefresh
}

@BindingAdapter("app:onRefreshClick")
fun SwipeRefreshLayout.onRefreshClick(onClick: () -> Unit) {
    setOnRefreshListener { onClick.invoke() }
}

@BindingAdapter("app:schemeColors")
fun SwipeRefreshLayout.schemeColors(resId : Int) {
    val intArray = resources.getIntArray(resId)
    setColorSchemeColors(*intArray)
}

@BindingAdapter("app:isEnable")
fun SwipeRefreshLayout.isEnable(isEnable: Boolean) {
    isEnabled = isEnable
}