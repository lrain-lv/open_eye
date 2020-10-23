package com.app.eye.widgets

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.app.eye.MainApp
import com.app.eye.R
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView
import com.chad.library.adapter.base.util.getItemView
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class EyeLoadMoreView : BaseLoadMoreView() {
    override fun getRootView(parent: ViewGroup): View =
        parent.getItemView(R.layout.layout_eye_load_more)

    override fun getLoadingView(holder: BaseViewHolder): View =
        holder.getView(R.id.load_more_loading_view)

    override fun getLoadComplete(holder: BaseViewHolder): View =
        holder.getView(R.id.load_more_load_complete_view)

    override fun getLoadEndView(holder: BaseViewHolder): View {
        val layout = holder.getView<FrameLayout>(R.id.load_more_load_end_view)
        val textView = layout.findViewById<TextView>(R.id.tv_end)
        textView.typeface =
            Typeface.createFromAsset(MainApp.mContext.assets, "fonts/Lobster-1.4.otf")
        return layout
    }

    override fun getLoadFailView(holder: BaseViewHolder): View =
        holder.getView(R.id.load_more_load_fail_view)

}