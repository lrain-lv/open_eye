package com.app.eye.ui.fragment

import android.app.Activity
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.ui.adapter.TabFragmentAdapter
import com.app.eye.ui.mvp.contract.TopicContact
import com.app.eye.ui.mvp.model.entity.TabChildEntity
import com.app.eye.ui.mvp.model.entity.TagTabEntity
import com.app.eye.ui.mvp.model.entity.TopicListEntity
import com.app.eye.ui.mvp.presenter.TopicPresenter
import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.fragment_topic_square.*
import me.yokeyword.fragmentation.SupportFragment

class TopicSquareFragment : BaseMvpFragment<TopicContact.Presenter, TopicContact.View>(),
    TopicContact.View, ViewPager.OnPageChangeListener {

    companion object {
        @JvmStatic
        fun newInstance() = TopicSquareFragment()
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
            mPresenter?.getTabRequest()
        }
    }

    override fun createPresenter(): TopicContact.Presenter? = TopicPresenter()

    override fun setTabResponse(data: TagTabEntity?) {
        data ?: ActivityUtils.finishActivity(mContext as Activity)
        tab_layout.visibility = View.VISIBLE
        val tabList = data!!.tabInfo.tabList
        tabList.forEach {
            titles.add(it.name)
            fragments.add(TopicSquareChildFragment.newInstance(id = it.id))
        }
        tabFragmentAdapter = TabFragmentAdapter(childFragmentManager, fragments, titles)
        view_pager.adapter = tabFragmentAdapter
        tab_layout.setViewPager(view_pager)
        tab_layout.currentTab = 0
        tab_layout.getTitleView(0).paint.isFakeBoldText = true
        view_pager.offscreenPageLimit = fragments.size
        view_pager.addOnPageChangeListener(this)
    }

    override fun setTabChildResponse(data: TabChildEntity?) {
    }

    override fun setTopicListResponse(data: TopicListEntity?) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        tab_layout.currentTab = position
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

}