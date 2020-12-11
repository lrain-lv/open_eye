package com.app.eye.ui.adapter.provider

import androidx.databinding.DataBindingUtil
import com.app.eye.R
import com.app.eye.databinding.ItemInfoFollowCardLayoutBinding
import com.app.eye.databinding.ItemInfoVideoCardLayoutBinding
import com.app.eye.ui.entity.InteractItem
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class InfoFollowCardProvider : BaseItemProvider<InteractItem>() {
    override val itemViewType: Int
        get() =2
    override val layoutId: Int
        get() = R.layout.item_info_follow_card_layout

    override fun onViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemInfoFollowCardLayoutBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: InteractItem) {
        val binding = DataBindingUtil.getBinding<ItemInfoFollowCardLayoutBinding>(helper.itemView)
        binding?.item = item
        binding?.executePendingBindings()
    }
}