package com.app.eye.ui.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.InfoItem
import com.app.eye.ui.mvp.model.entity.ItemDaily.Companion.DAILY_INFORMATION
import com.app.eye.ui.mvp.model.entity.ItemDaily.Companion.DAILY_TEXT
import com.app.eye.widgets.NoScrollLinearLayoutManager
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class InformationAdapter(data: MutableList<InfoItem>) :
    BaseMultiItemQuickAdapter<InfoItem, BaseViewHolder>(data), LoadMoreModule {
    init {
        addItemType(DAILY_TEXT, R.layout.layout_discover_text_card)
        addItemType(DAILY_INFORMATION, R.layout.layout_daily_infomation_card)
    }

    override fun convert(holder: BaseViewHolder, item: InfoItem) {
        when (item.itemType) {
            DAILY_TEXT -> {
                holder.setText(R.id.tv_title, item.data.text)
                    .setGone(R.id.tv_right_text, true)
            }
            DAILY_INFORMATION -> {
                val data = item.data
                val ivCover = holder.getView<ImageView>(R.id.iv_cover)
                val recycler = holder.getView<RecyclerView>(R.id.recycler)
                Glide.with(context)
                    .load(data.backgroundImage)
                    .transform(
                        CenterCrop(),
                        RoundedCornersTransformation(
                            SizeUtils.dp2px(5f),
                            0,
                            RoundedCornersTransformation.CornerType.TOP
                        )
                    ).into(ivCover)
                val adapter = DailyInfoAdapter(data.titleList)
                recycler.layoutManager = NoScrollLinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL, false)
                recycler.adapter = adapter
            }
        }
    }
}