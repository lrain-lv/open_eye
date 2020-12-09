package com.app.eye.binding

import androidx.databinding.BindingAdapter
import com.app.eye.widgets.*


@BindingAdapter("app:statusValue")
fun MultipleStatusView.statusValue(value: Int) {
    when (value) {
        STATUS_LOADING -> {
            this.showLoadingView()
        }
        STATUS_ERROR -> {
            this.showErrorView()
        }
        STATUS_EMPTY -> {
            this.showEmptyView()
        }
        STATUS_CONTENT -> {
            this.showContentView()
        }
    }
}