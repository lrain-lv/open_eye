package com.app.eye.ui.activity

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.app.eye.R
import com.app.eye.base.BaseActivity
import com.app.eye.ui.adapter.GalleryAdapter
import com.app.eye.ui.mvp.model.entity.ComRecEntity
import com.app.eye.widgets.OnViewPagerListener
import com.app.eye.widgets.ViewPagerLayoutManager
import com.app.eye.widgets.videoplayer.Jzvd
import com.blankj.utilcode.util.ActivityUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : BaseActivity(), View.OnClickListener, OnViewPagerListener {

    companion object {
        fun startGalleryActivity(data: ComRecEntity, pos: Int) {
            val bundle = Bundle().apply {
                putSerializable("data", data)
                putInt("pos", pos)
            }
            ActivityUtils.startActivity(bundle,
                GalleryActivity::class.java,
                R.anim.in_from_bottom,
                R.anim.top_slient)
        }
    }

    private lateinit var data: ComRecEntity
    private lateinit var adapter: GalleryAdapter
    private var pos: Int = 0

    override fun getLayoutRes(): Int = R.layout.activity_gallery

    override fun initView() {
        val extras = intent.extras!!
        data = extras.getSerializable("data") as ComRecEntity
        pos = extras.getInt("pos")
        immersionBar
            .statusBarDarkFont(false)
            .statusBarColor(R.color.black, 0f)
            .init()
        initListener()
        val manager = ViewPagerLayoutManager(this)
        manager.setOnViewPagerListener(this)
        adapter = GalleryAdapter(data.itemList)
        view_pager2.adapter = adapter
        view_pager2.offscreenPageLimit = 1
        view_pager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        view_pager2.currentItem = 0
    }

    private fun initListener() {
        iv_close.setOnClickListener(this)
    }

    override fun initData() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_close -> {
                onBackPressedSupport()
            }
        }
    }

    override fun onBackPressedSupport() {
        Jzvd.releaseAllVideos()
        super.onBackPressedSupport()
        overridePendingTransition(R.anim.top_slient, R.anim.out_from_bottom)
    }

    override fun onInitComplete() {
        Logger.e("---onInitComplete")
    }

    override fun onPageRelease(isNext: Boolean, position: Int) {
        Logger.e("---onPageRelease--${position}")
    }

    override fun onPageSelected(position: Int, isBottom: Boolean) {
        Logger.e("---onPageSelected")
    }
}