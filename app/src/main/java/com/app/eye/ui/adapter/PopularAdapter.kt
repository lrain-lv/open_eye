package com.app.eye.ui.adapter

import android.graphics.Color
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.PopItem
import com.app.eye.ui.mvp.model.entity.PopTag
import com.app.eye.widgets.videoplayer.EyeVideoPlayer
import com.app.eye.widgets.videoplayer.JzViewOutlineProvider
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.TimeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.util.*

class PopularAdapter(data: MutableList<PopItem>) :
    BaseQuickAdapter<PopItem, BaseViewHolder>(data = data, layoutResId =
    R.layout.layout_light_topic_item), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: PopItem) {
        val data = item.data.content.data
        val tagLayout = holder.getView<LinearLayout>(R.id.layout_tag)
        val header = holder.getView<ImageView>(R.id.iv_header)
        val tvDate = holder.getView<TextView>(R.id.tv_date)
        val s1 = "${TimeUtils.millis2String(data.releaseTime, "yyyy/MM/dd")} 发布: "
        val s2 = data.title
        val create = SpanUtils.with(tvDate)
            .append(s1)
            .append(s2).setBold().setForegroundColor(Color.BLACK)
            .create()
        var time: String = ""
        if (data.duration < 60) {
            time = String.format(Locale.getDefault(), "00:%02d", data.duration % 60)
        } else if (data.duration < 3600) {
            time = String.format(
                Locale.getDefault(),
                "%02d:%02d",
                data.duration / 60,
                data.duration % 60
            )
        }
        holder.setText(R.id.tv_name, data.author.name)
            .setGone(R.id.tv_content, data.description.isNullOrEmpty())
            .setText(R.id.tv_content, data.description)
            .setText(R.id.tv_like_count,
                data.consumption.collectionCount.toString())
            .setText(R.id.tv_reply_count, data.consumption.replyCount.toString())
            .setGone(R.id.layout_tag, data.tags.isNullOrEmpty())
            .setText(R.id.tv_date, create)
            .setText(R.id.tv_duration, time)

        if (!data.tags.isNullOrEmpty()) {
            val tags =
                if (data.tags.size > 2) data.tags.take(2) as MutableList<PopTag> else data.tags
            tags.forEach {
                val tv = TextView(context)
                tv.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    SizeUtils.dp2px(30f))
                tv.setPadding(SizeUtils.dp2px(5f), 0, SizeUtils.dp2px(5f), 0)
                tv.text = it.name
                tagLayout.addView(tv)
            }
        }
        Glide.with(context)
            .load(item.data.header.icon)
            .circleCrop()
            .override(SizeUtils.dp2px(45f), SizeUtils.dp2px(45f))
            .into(header)
        val jzvdStd = holder.getView<EyeVideoPlayer>(R.id.jzvd)
        jzvdStd.outlineProvider = JzViewOutlineProvider(SizeUtils.dp2px(5f).toFloat())
        jzvdStd.clipToOutline = true
        jzvdStd.setUp(data.playUrl, "")
        Glide.with(context)
            .load(data.cover.feed)
            .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
            .into(jzvdStd.thumbImageView)
    }
}