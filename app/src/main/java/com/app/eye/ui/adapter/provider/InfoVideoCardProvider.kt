package com.app.eye.ui.adapter.provider

import androidx.databinding.DataBindingUtil
import com.app.eye.R
import com.app.eye.databinding.ItemInfoVideoCardLayoutBinding
import com.app.eye.ui.entity.InteractItem
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class InfoVideoCardProvider : BaseItemProvider<InteractItem>() {
    override val itemViewType: Int
        get() = 1
    override val layoutId: Int
        get() = R.layout.item_info_video_card_layout

    override fun onViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemInfoVideoCardLayoutBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: InteractItem) {
        val binding = DataBindingUtil.getBinding<ItemInfoVideoCardLayoutBinding>(helper.itemView)
        binding?.item = item
        binding?.executePendingBindings()
    }
}