package com.app.eye.ui.fragment

import androidx.viewpager.widget.ViewPager
import com.app.eye.R
import com.app.eye.base.BaseFragment
import com.app.eye.ui.activity.SearchActivity
import com.app.eye.ui.adapter.TabFragmentAdapter
import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.fragment_home.*
import me.yokeyword.fragmentation.SupportFragment


class HomeFragment : BaseFragment() {

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun initView() {
        val titleList = mutableListOf<String>("发现", "日报")
        val fragmentList = listOf<SupportFragment>(
            FindFragment.newInstance(),
            DailyFragment.newInstance()
        )
        val tabFragmentAdapter =
            TabFragmentAdapter(requireFragmentManager(), fragmentList, titleList)
        view_pager.adapter = tabFragmentAdapter
        view_pager.offscreenPageLimit = 3
        tab_layout.setViewPager(view_pager)
        tab_layout.currentTab = 0
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
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
        })
        iv_search.setOnClickListener {
            ActivityUtils.startActivity(SearchActivity::class.java)
        }
    }

    override fun initData() {

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

}