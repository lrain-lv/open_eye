package com.app.eye.ui.fragment

import androidx.viewpager.widget.ViewPager
import com.app.eye.R
import com.app.eye.base.BaseFragment
import com.app.eye.ui.adapter.TabFragmentAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import me.yokeyword.fragmentation.SupportFragment


class NotificationFragment : BaseFragment(), ViewPager.OnPageChangeListener {

    private val titleList = mutableListOf("推送", "私信")
    override fun getLayoutRes(): Int = R.layout.fragment_notification

    override fun initView() {
        val fragmentList = mutableListOf<SupportFragment>().apply {
            add(PushFragment.newInstance())
            add(MessageFragment.newInstance())
        }
        val fragmentAdapter = TabFragmentAdapter(childFragmentManager, fragmentList, titleList)
        view_pager.apply {
            addOnPageChangeListener(this@NotificationFragment)
            offscreenPageLimit = fragmentList.size
            adapter = fragmentAdapter
        }
        tab_layout.setViewPager(view_pager)
        tab_layout.currentTab = 0
    }


    override fun initData() {
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NotificationFragment()
    }

    override fun reConnect() {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        tab_layout.currentTab = position
    }

    override fun onPageScrollStateChanged(state: Int) {
    }
}