package com.app.eye.ui.fragment

import androidx.viewpager.widget.ViewPager
import com.app.eye.R
import com.app.eye.base.BaseFragment
import com.app.eye.ui.adapter.TabFragmentAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import me.yokeyword.fragmentation.SupportFragment


/**
 * 社区
 */
class CommunityFragment : BaseFragment(), ViewPager.OnPageChangeListener {
    private val titleList = mutableListOf<String>("推荐", "关注")
    override fun getLayoutRes(): Int = R.layout.fragment_community
    override fun reConnect() {
    }

    override fun initView() {
        val fragmentList = mutableListOf<SupportFragment>().apply {
            add(ComRecFragment.newInstance())
            add(ComAttentionFragment.newInstance())
        }
        val fragmentAdapter = TabFragmentAdapter(childFragmentManager, fragmentList, titleList)
        view_pager.offscreenPageLimit = fragmentList.size
        view_pager.adapter = fragmentAdapter
        tab_layout.setViewPager(view_pager)
        tab_layout.currentTab = 0
        view_pager.addOnPageChangeListener(this)
    }


    override fun initData() {
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CommunityFragment().apply {

            }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        tab_layout.currentTab = position
    }

    override fun onPageScrollStateChanged(state: Int) {
    }
}