package com.app.eye.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMFragment
import com.app.eye.rx.checkSuccess
import com.app.eye.rx.urlToMap
import com.app.eye.ui.activity.TagVideoActivity
import com.app.eye.ui.adapter.TopicChildAdapter
import com.app.eye.ui.mvvm.viewmodel.TopicViewModel
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_EMPTY
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_rec.refresh_layout
import kotlinx.android.synthetic.main.fragment_rec.status_view
import kotlinx.android.synthetic.main.fragment_topic_square_child.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopicSquareChildFragment : BaseVMFragment(), SwipeRefreshLayout.OnRefreshListener,
    OnLoadMoreListener,
    OnItemClickListener {

    companion object {
        @JvmStatic
        fun newInstance(id: Int) =
            TopicSquareChildFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                }
            }
    }

    private val viewModel by viewModel<TopicViewModel>()
    private var urlId = -1

    private var nextPageUrl: String? = ""

    private var isRefresh: Boolean = true
    private lateinit var topicChildAdapter: TopicChildAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)
        urlId = arguments?.getInt("id", -1)!!
        urlId = if (urlId == -1) 0 else urlId
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
        refresh_layout.isRefreshing = false
        status_view.showContentView()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_topic_square_child

    override fun reConnect() {

    }

    override fun initView() {
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        topicChildAdapter = TopicChildAdapter(mutableListOf())
        topicChildAdapter.loadMoreModule.setOnLoadMoreListener(this)
        topicChildAdapter.loadMoreModule.isEnableLoadMore = false
        recycler.adapter = topicChildAdapter
        recycler.setHasFixedSize(true)
        topicChildAdapter.setOnItemClickListener(this)
    }

    override fun initData() {
        isRefresh = true
        refresh_layout.post {
            viewModel.getTabChild(
                urlId,
                hashMapOf("isRecTab" to if (urlId == 0) "true" else "false"),
                isFirst = true,
                isLoadMore = false
            )
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

        viewModel.tabChildLiveData.observe(this, Observer {
            it.checkSuccess({ data ->
                nextPageUrl = data.nextPageUrl
                if (isRefresh) {
                    topicChildAdapter.loadMoreModule.isEnableLoadMore = true
                    topicChildAdapter.setList(data.itemList)
                    if (TextUtils.isEmpty(nextPageUrl)) topicChildAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    topicChildAdapter.loadMoreModule.isEnableLoadMore = true
                    topicChildAdapter.addData(data.itemList)
                    if (TextUtils.isEmpty(nextPageUrl)) {
                        topicChildAdapter.loadMoreModule.loadMoreEnd()
                    } else {
                        topicChildAdapter.loadMoreModule.loadMoreComplete()
                    }
                }
                refresh_layout.isEnabled = true
            }, onError = {
                status_view.showErrorView()
            })
        })
    }

    override fun onRefresh() {
        topicChildAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        viewModel.getTabChild(
            urlId,
            hashMapOf("isRecTab" to if (urlId == 0) "true" else "false"),
            isFirst = false,
            isLoadMore = false
        )
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            viewModel.getTabChild(
                urlId,
                map,
                isFirst = false,
                isLoadMore = true
            )
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = topicChildAdapter.getItem(position)
        TagVideoActivity.startActivity(
            item.data.id.toString(),
            item.data.title,
            item.data.icon,
            item.data.description
        )
    }
}