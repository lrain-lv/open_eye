package com.app.eye.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.blankj.utilcode.util.SizeUtils


@BindingAdapter("url", "width", requireAll = true)
fun ImageView.loadCircle(url: String?, width: Float) {
    load(url) {
        size(SizeUtils.dp2px(width))
        transformations(CircleCropTransformation())
    }
}

@BindingAdapter("url")
fun ImageView.loadImageCommon(url: String?) {
    load(url)
}

@BindingAdapter("url", "radius", requireAll = true)
fun ImageView.loadImageRound(
    url: String?,
    radius: Float = 5f,
) {
    load(url) {
        transformations(
            RoundedCornersTransformation(
                SizeUtils.dp2px(radius).toFloat()
            )
        )
    }
}