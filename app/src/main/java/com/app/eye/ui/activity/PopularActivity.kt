package com.app.eye.ui.activity

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.app.eye.R
import com.app.eye.base.BaseActivity
import com.app.eye.ui.adapter.TopicFragmentAdapter
import com.app.eye.ui.fragment.PopularFragment
import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.activity_rank_avtivity.*
import me.yokeyword.fragmentation.SupportFragment

class PopularActivity : BaseActivity(), ViewPager.OnPageChangeListener {

    companion object {
        fun startActivity(id: String) {
            val bundle = Bundle().apply {
                putString("id", id)
            }
            ActivityUtils.startActivity(bundle, PopularActivity::class.java)
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_popular

    override fun initView() {
        val id = intent.extras?.getString("id", "")
        if (id.isNullOrEmpty()) onBackPressedSupport()
        immersionBar.statusBarDarkFont(true).fitsSystemWindows(true).init()
        val titles = mutableListOf<String>("近期热门", "最受欢迎")
        val fragments = mutableListOf<SupportFragment>()
            .apply {
                add(PopularFragment.newInstance("recentReleaseShareCount", id!!))
                add(PopularFragment.newInstance("shareCount", id))
            }
        val adapter = TopicFragmentAdapter(supportFragmentManager, fragments, titles)
        view_pager.offscreenPageLimit = titles.size
        view_pager.adapter = adapter
        view_pager.currentItem = 0
        tab_layout.setViewPager(view_pager)
        tab_layout.currentTab = 0
        view_pager.addOnPageChangeListener(this)

        tab_layout.getTitleView(0).paint.isFakeBoldText = true
    }

    override fun initData() {
    }

    override fun reConnect() {
    }

    override fun onPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) {
    }

    override fun onPageSelected(position: Int) {
        tab_layout.currentTab = position
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

}