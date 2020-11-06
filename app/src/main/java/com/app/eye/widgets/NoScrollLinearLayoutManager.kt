package com.app.eye.widgets

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NoScrollLinearLayoutManager(
    context: Context,
    orientation: Int = RecyclerView.VERTICAL,
    reverse: Boolean = false,
) : LinearLayoutManager(context, orientation, reverse) {

    override fun canScrollVertically(): Boolean {
        return false
    }

}