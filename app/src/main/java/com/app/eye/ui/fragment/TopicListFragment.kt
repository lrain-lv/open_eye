package com.app.eye.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.rx.urlToMap
import com.app.eye.ui.adapter.TopicListAdapter
import com.app.eye.ui.mvp.contract.TopicContact
import com.app.eye.ui.mvp.model.entity.TabChildEntity
import com.app.eye.ui.mvp.model.entity.TagTabEntity
import com.app.eye.ui.mvp.model.entity.TopicListEntity
import com.app.eye.ui.mvp.presenter.TopicPresenter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_rec.refresh_layout
import kotlinx.android.synthetic.main.fragment_rec.status_view
import kotlinx.android.synthetic.main.fragment_topic_square_child.*

class TopicListFragment : BaseMvpFragment<TopicContact.Presenter, TopicContact.View>(),
    TopicContact.View,
    SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    companion object {
        @JvmStatic
        fun newInstance() =
            TopicListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private var nextPageUrl: String? = ""

    private lateinit var topicListAdapter: TopicListAdapter

    private var isRefresh: Boolean = true

    override fun showLoading() {
    }

    override fun hideLoading() {
        refresh_layout.isRefreshing = false
        status_view.showContentView()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_topic_list

    override fun reConnect() {

    }

    override fun initView() {
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        topicListAdapter = TopicListAdapter(mutableListOf())
        topicListAdapter.loadMoreModule.setOnLoadMoreListener(this)
        topicListAdapter.loadMoreModule.isEnableLoadMore = false
        recycler.adapter = topicListAdapter
        recycler.setHasFixedSize(true)
    }

    override fun initData() {
        isRefresh = true
        status_view.showLoadingView()
        refresh_layout.post {
            mPresenter?.getTopicListRequest(
                hashMapOf()
            )
        }
    }

    override fun setTabResponse(data: TagTabEntity?) {
    }

    override fun setTabChildResponse(data: TabChildEntity?) {
    }

    override fun setTopicListResponse(data: TopicListEntity?) {
        data ?: status_view.showEmptyView()
        nextPageUrl = data?.nextPageUrl
        Logger.e("ddd$nextPageUrl")
        if (isRefresh) {
            topicListAdapter.loadMoreModule.isEnableLoadMore = true
            topicListAdapter.setList(data?.itemList)
            if (TextUtils.isEmpty(nextPageUrl)) topicListAdapter.loadMoreModule.loadMoreEnd()
        } else {
            topicListAdapter.loadMoreModule.isEnableLoadMore = true
            topicListAdapter.addData(data?.itemList!!)
            if (TextUtils.isEmpty(nextPageUrl)) {
                topicListAdapter.loadMoreModule.loadMoreEnd()
            } else {
                topicListAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        refresh_layout.isEnabled = true
    }

    override fun createPresenter(): TopicContact.Presenter? = TopicPresenter()

    override fun onRefresh() {
        topicListAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        mPresenter?.getTopicListRequest(
            hashMapOf()
        )
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            mPresenter?.getTopicListRequest(map)
        }
    }
}