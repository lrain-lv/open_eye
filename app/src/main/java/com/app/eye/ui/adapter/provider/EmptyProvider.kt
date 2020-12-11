package com.app.eye.ui.adapter.provider

import androidx.databinding.DataBindingUtil
import com.app.eye.R
import com.app.eye.databinding.ItemInfoFollowCardLayoutBinding
import com.app.eye.databinding.ItemInfoVideoCardLayoutBinding
import com.app.eye.databinding.ItemReplyLayoutBinding
import com.app.eye.ui.entity.InteractItem
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class EmptyProvider : BaseItemProvider<InteractItem>() {
    override val itemViewType: Int
        get() = -1
    override val layoutId: Int
        get() = R.layout.layout_none


    override fun convert(helper: BaseViewHolder, item: InteractItem) {

    }
}