package com.app.eye.rx

import android.content.Context
import android.widget.ImageView
import com.app.eye.widgets.transformations.BitmapTransformation
import com.app.eye.widgets.transformations.RoundedCornersTransformation
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


fun ImageView.loadImageCommon(context: Context, url: String?) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .into(this)
}

fun ImageView.loadImageCommonNoScaleType(context: Context, url: String?) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun ImageView.loadImageCircle(context: Context, url: String?, width: Float) {
    Glide.with(context)
        .load(url)
        .circleCrop()
        .override(SizeUtils.dp2px(width), SizeUtils.dp2px(width))
        .into(this)
}

fun ImageView.loadImageRound(
    context: Context,
    url: String?,
    radius: Float = 5f,
    isAnim: Boolean = false,
) {
    if (isAnim) {
        Glide.with(context)
            .load(url)
            .transform(
                CenterCrop(),
                RoundedCornersTransformation(SizeUtils.dp2px(radius), 0)
            )
            .transition(DrawableTransitionOptions.withCrossFade(100))
            .into(this)
    } else {
        Glide.with(context)
            .load(url)
            .transform(
                CenterCrop(),
                RoundedCornersTransformation(SizeUtils.dp2px(radius), 0)
            )
            .into(this)
    }
}

fun ImageView.loadImageRoundWithSize(
    context: Context,
    url: String?, width: Float,
    radius: Float = 5f,
) {
    Glide.with(context)
        .load(url)
        .override(SizeUtils.dp2px(width), SizeUtils.dp2px(width))
        .transform(
            CenterCrop(),
            RoundedCornersTransformation(SizeUtils.dp2px(radius), 0)
        )
        .into(this)
}

fun ImageView.loadImageWithTransform(
    context: Context,
    url: String?,
    transformation: BitmapTransformation,
) {
    Glide.with(context)
        .load(url)
        .transform(
            CenterCrop(),
            transformation
        )
        .into(this)

}




