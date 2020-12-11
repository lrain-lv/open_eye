package com.app.eye.ui.adapter

import com.app.eye.R
import com.app.eye.ui.adapter.provider.EmptyProvider
import com.app.eye.ui.adapter.provider.InfoFollowCardProvider
import com.app.eye.ui.adapter.provider.InfoVideoCardProvider
import com.app.eye.ui.adapter.provider.ReplyMsgProvider
import com.app.eye.ui.entity.InteractItem
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

class InteractAdapter(data: MutableList<InteractItem>) :
    BaseProviderMultiAdapter<InteractItem>(data = data),LoadMoreModule {
    init {
        addItemProvider(EmptyProvider())
        addItemProvider(InfoFollowCardProvider())
        addItemProvider(InfoVideoCardProvider())
        addItemProvider(ReplyMsgProvider())
    }

    override fun getItemType(data: List<InteractItem>, position: Int): Int {
        val data1 = data[position].data
        return when (data1.dataType) {
            "ReplyMessageBean" -> 0
            "DynamicVideoCard"-> 1
            "DynamicFollowCard" -> 2
            else -> -1
        }
    }
}