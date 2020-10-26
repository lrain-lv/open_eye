package com.app.eye.ui.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.app.eye.R
import com.app.eye.base.BaseFragment
import com.app.eye.ui.adapter.TabFragmentAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import me.yokeyword.fragmentation.SupportFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationFragment : BaseFragment(), ViewPager.OnPageChangeListener {

    private val titleList = mutableListOf<String>("推送", "互动", "私信")
    override fun getLayoutRes(): Int = R.layout.fragment_notification

    override fun initView() {
        val fragmentList = mutableListOf<SupportFragment>().apply {
            add(PushFragment.newInstance())
            add(InteractFragment.newInstance())
            add(MessageFragment.newInstance())
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
            NotificationFragment().apply {

            }
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