package com.app.eye.ui.fragment

import androidx.viewpager.widget.ViewPager
import com.app.eye.R
import com.app.eye.base.BaseFragment
import com.app.eye.rx.setOnClickListener
import com.app.eye.ui.activity.BrandWallActivity
import com.app.eye.ui.activity.SearchActivity
import com.app.eye.ui.adapter.TabFragmentAdapter
import com.app.eye.widgets.videoplayer.Jzvd
import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.fragment_home.*
import me.yokeyword.fragmentation.SupportFragment

class HomeFragment : BaseFragment(), ViewPager.OnPageChangeListener {

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun initView() {
        val titleList = mutableListOf<String>("发现", "推荐", "日报")
        val fragmentList = listOf<SupportFragment>(
            FindFragment.newInstance(),
            RecommendFragment.newInstance(),
            DailyFragment.newInstance()
        )
        val tabFragmentAdapter =
            TabFragmentAdapter(requireFragmentManager(), fragmentList, titleList)
        view_pager.apply {
            adapter = tabFragmentAdapter
            offscreenPageLimit = titleList.size
        }
        tab_layout.apply {
            setViewPager(view_pager)
            currentTab = 0
        }
        view_pager.addOnPageChangeListener(this)
        setOnClickListener(iv_search, iv_nav_card) {
            when (this.id) {
                R.id.iv_search -> {
                    ActivityUtils.startActivity(
                        SearchActivity::class.java, R.anim.in_from_top,
                        R.anim.top_slient
                    )
                }
                R.id.iv_nav_card -> {
                    ActivityUtils.startActivity(BrandWallActivity::class.java)
                }
            }
        }
    }

    override fun initData() {

    }

    override fun onDestroyView() {
        view_pager.removeOnPageChangeListener(this)
        super.onDestroyView()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        Jzvd.releaseAllVideos()
    }

    override fun useLazyLoad(): Boolean = true

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {

            }
    }

    override fun reConnect() {
    }

    override fun onPageScrollStateChanged(state: Int) {
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

}