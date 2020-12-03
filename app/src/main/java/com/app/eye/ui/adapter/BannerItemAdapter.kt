package com.app.eye.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.app.eye.rx.loadImageRound
import com.app.eye.ui.entity.ItemX
import com.blankj.utilcode.util.SizeUtils
import com.youth.banner.adapter.BannerAdapter


class BannerItemAdapter(dataList: MutableList<ItemX>) :
    BannerAdapter<ItemX, BannerItemAdapter.MyHolder>(dataList) {

    class MyHolder constructor(itemView: ImageView) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView
    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        val imageView = ImageView(parent!!.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
//        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return MyHolder(imageView)
    }

    override fun onBindView(holder: MyHolder?, data: ItemX?, position: Int, size: Int) {
        holder!!.imageView.load(data?.data?.image!!){
            transformations(RoundedCornersTransformation(SizeUtils.dp2px(5f).toFloat()))
        }
    }
}