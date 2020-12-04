package com.app.eye.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMFragment
import com.app.eye.rx.checkSuccess
import com.app.eye.rx.urlToMap
import com.app.eye.ui.activity.TopicDetailActivity
import com.app.eye.ui.adapter.TopicListAdapter
import com.app.eye.ui.mvvm.viewmodel.TagVideoViewModel
import com.app.eye.ui.mvvm.viewmodel.TopicViewModel
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_EMPTY
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_rec.refresh_layout
import kotlinx.android.synthetic.main.fragment_rec.status_view
import kotlinx.android.synthetic.main.fragment_topic_square_child.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopicListFragment : BaseVMFragment(),
    SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    companion object {
        @JvmStatic
        fun newInstance() =
            TopicListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private val viewModel by viewModel<TopicViewModel>()

    private var nextPageUrl: String? = ""

    private lateinit var topicListAdapter: TopicListAdapter

    private var isRefresh: Boolean = true


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
        topicListAdapter.setOnItemClickListener { adapter, view, position ->
            val item = topicListAdapter.getItem(position)
            val actionUrl = item.data.actionUrl
            if (actionUrl.contains("topic/detail")) {
                TopicDetailActivity.startActivity(item.data.id.toString())
            }
        }
        recycler.setHasFixedSize(true)
    }

    override fun initData() {
        isRefresh = true
        status_view.showLoadingView()
        refresh_layout.post {
            viewModel.getTopicList(hashMapOf(), isFirst = true, isLoadMore = false)
        }
    }

    override fun startObserver() {
        viewModel.refreshLiveData.observe(this, Observer {
            refresh_layout.isRefreshing = it
        })
        viewModel.statusLiveData.observe(this, Observer {
            when (it) {
                STATUS_LOADING -> {
                    status_view.showLoadingView()
                }
                STATUS_ERROR -> {
                    status_view.showErrorView()
                }
                STATUS_EMPTY -> {
                    status_view.showEmptyView()
                }
                STATUS_CONTENT -> {
                    status_view.showContentView()
                }
            }
        })

        viewModel.topicListLiveData.observe(this, Observer {
            it.checkSuccess({ data ->
                nextPageUrl = data.nextPageUrl
                if (isRefresh) {
                    topicListAdapter.loadMoreModule.isEnableLoadMore = true
                    topicListAdapter.setList(data.itemList)
                    if (TextUtils.isEmpty(nextPageUrl)) topicListAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    topicListAdapter.loadMoreModule.isEnableLoadMore = true
                    topicListAdapter.addData(data.itemList)
                    if (TextUtils.isEmpty(nextPageUrl)) {
                        topicListAdapter.loadMoreModule.loadMoreEnd()
                    } else {
                        topicListAdapter.loadMoreModule.loadMoreComplete()
                    }
                }
                refresh_layout.isEnabled = true
            }, onError = {
                status_view.showErrorView()
            })
        })
    }

    override fun onRefresh() {
        topicListAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        viewModel.getTopicList(hashMapOf(), isFirst = false, isLoadMore = false)
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            viewModel.getTopicList(map, isFirst = false, isLoadMore = true)
        }
    }
}