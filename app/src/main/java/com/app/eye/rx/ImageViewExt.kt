package com.app.eye.rx

import android.content.Context
import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.blankj.utilcode.util.SizeUtils


fun ImageView.loadImageCommon(url: String?) {
    load(url)
}

fun ImageView.loadImageCircle(url: String?, width: Float) {
    load(url) {
        size(SizeUtils.dp2px(width))
        transformations(CircleCropTransformation())
    }
}

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

fun ImageView.loadImageRoundWithSize(
    url: String?, width: Float,
    radius: Float = 5f,
) {
    load(url) {
        size(SizeUtils.dp2px(width))
        transformations(RoundedCornersTransformation(SizeUtils.dp2px(radius).toFloat()))
    }
}

fun ImageView.loadImageWithTransform(
    url: String?,
    transformation: Transformation,
) {
    load(url) {
        transformations(transformation)
    }
}




