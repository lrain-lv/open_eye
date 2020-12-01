package com.app.eye.ui.fragment

import android.app.Activity
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMFragment
import com.app.eye.rx.checkSuccess
import com.app.eye.ui.adapter.TabFragmentAdapter
import com.app.eye.ui.mvp.contract.TopicContact
import com.app.eye.ui.mvp.presenter.TopicPresenter
import com.app.eye.ui.mvvm.factory.InjectorUtil
import com.app.eye.ui.mvvm.viewmodel.TopicViewModel
import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.fragment_topic_square.*
import me.yokeyword.fragmentation.SupportFragment

class TopicSquareFragment : BaseVMFragment(), ViewPager.OnPageChangeListener {

    companion object {
        @JvmStatic
        fun newInstance() = TopicSquareFragment()
    }

    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getTopicVMFactory()).get(
            TopicViewModel::class.java
        )
    }
    private var titles: MutableList<String> = mutableListOf()
    private var fragments: MutableList<SupportFragment> = mutableListOf()

    private lateinit var tabFragmentAdapter: TabFragmentAdapter

    override fun getLayoutRes(): Int = R.layout.fragment_topic_square

    override fun reConnect() {
    }

    override fun initView() {
        tab_layout.visibility = View.GONE
    }

    override fun initData() {
        tab_layout.post {
            viewModel.getTab()
        }
    }

    override fun startObserver() {
        viewModel.tabLiveData.observe(this, Observer {
            it.checkSuccess({ data ->
                tab_layout.visibility = View.VISIBLE
                val tabList = data.tabInfo.tabList
                tabList.forEach { tab ->
                    titles.add(tab.name)
                    fragments.add(TopicSquareChildFragment.newInstance(id = tab.id))
                }
                tabFragmentAdapter = TabFragmentAdapter(childFragmentManager, fragments, titles)
                view_pager.adapter = tabFragmentAdapter
                tab_layout.setViewPager(view_pager)
                tab_layout.currentTab = 0
                tab_layout.getTitleView(0).paint.isFakeBoldText = true
                view_pager.offscreenPageLimit = fragments.size
                view_pager.addOnPageChangeListener(this)
            }, onError = {
                ActivityUtils.finishActivity(mContext as Activity)
            })
        })
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        tab_layout.currentTab = position
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

}