package com.app.eye.rx

import android.content.Context
import android.view.ViewTreeObserver
import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.app.eye.AppHelper
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide


fun ImageView.loadImageCommon(url: String?) {
    load(url)
    val into = Glide.with(AppHelper.mContext)
        .load(url)
        .into(this)
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




