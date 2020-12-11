package com.app.eye.ui.adapter.provider

import androidx.databinding.DataBindingUtil
import com.app.eye.R
import com.app.eye.databinding.ItemInfoFollowCardLayoutBinding
import com.app.eye.databinding.ItemInfoVideoCardLayoutBinding
import com.app.eye.databinding.ItemReplyLayoutBinding
import com.app.eye.ui.entity.InteractItem
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class ReplyMsgProvider : BaseItemProvider<InteractItem>() {
    override val itemViewType: Int
        get() = 0
    override val layoutId: Int
        get() = R.layout.item_reply_layout

    override fun onViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ItemReplyLayoutBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: InteractItem) {
        val binding = DataBindingUtil.getBinding<ItemReplyLayoutBinding>(helper.itemView)
        binding?.item = item
        binding?.executePendingBindings()
    }
}