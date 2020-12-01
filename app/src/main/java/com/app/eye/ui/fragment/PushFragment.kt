package com.app.eye.ui.fragment

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMFragment
import com.app.eye.event.ChangeTabEvent
import com.app.eye.rx.actionUrlToMap
import com.app.eye.rx.checkSuccess
import com.app.eye.ui.activity.WebActivity
import com.app.eye.ui.adapter.PushMessageAdapter
import com.app.eye.ui.mvvm.factory.InjectorUtil
import com.app.eye.ui.mvvm.viewmodel.PushViewModel
import com.app.eye.widgets.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_push.*
import org.greenrobot.eventbus.EventBus

class PushFragment : BaseVMFragment(),
    OnItemClickListener, OnLoadMoreListener,
    SwipeRefreshLayout.OnRefreshListener,
    MultipleStatusView.OnRetryClickListener {

    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getPushVMFactory()).get(
            PushViewModel::class.java
        )
    }

    private val pushMessageAdapter: PushMessageAdapter =
        PushMessageAdapter(mutableListOf())

    private var map = hashMapOf<String, String>()

    private var isRefresh = true

    private var start = 0

    override fun getLayoutRes(): Int = R.layout.fragment_push

    override fun reConnect() {

    }

    override fun initView() {
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        pushMessageAdapter.setOnItemClickListener(this)
        status_view.setOnRetryClickListener(this)
        pushMessageAdapter.loadMoreModule.setOnLoadMoreListener(this)
        pushMessageAdapter.loadMoreModule.isEnableLoadMore = false
        recycler_view.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = pushMessageAdapter

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
        viewModel.entityLiveData.observe(this, Observer {
            it.checkSuccess({ entity ->
                when (isRefresh) {
                    true -> {
                        pushMessageAdapter.loadMoreModule.isEnableLoadMore =
                            true
                        pushMessageAdapter.setList(entity.messageList)
                    }
                    false -> {
                        pushMessageAdapter.loadMoreModule.isEnableLoadMore =
                            true
                        pushMessageAdapter.addData(entity.messageList)
                        if (entity.nextPageUrl.isNullOrEmpty()) pushMessageAdapter.loadMoreModule.loadMoreEnd()
                        else pushMessageAdapter.loadMoreModule.loadMoreComplete()
                    }
                }
                refresh_layout.isEnabled = true
            },onError = {
                status_view.showErrorView()
            })
        })
    }

    override fun initData() {
        status_view.showLoadingView()
        recycler_view.post {
            isRefresh = true
            start = 0
            map["start"] = start.toString()
            map["num"] = "10"
            viewModel.onRefresh(map, true)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PushFragment().apply {
            }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
        status_view.showContentView()
        refresh_layout.isRefreshing = false
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = pushMessageAdapter.getItem(position)
        if (item.actionUrl.contains("homepage")) {
            EventBus.getDefault().post(ChangeTabEvent())
        } else if (item.actionUrl.contains("webview")) {
            WebActivity.startWebActivity(
                url = item.actionUrl.actionUrlToMap()["url"] ?: error(""),
                title = item.actionUrl.actionUrlToMap()["title"] ?: error("")
            )
        }
    }

    override fun onLoadMore() {
        refresh_layout.isEnabled = false
        isRefresh = false
        start += 10
        map["start"] = start.toString()
        map["num"] = "10"
        viewModel.onLoadMore(map)
    }

    override fun onRefresh() {
        pushMessageAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        start = 0
        map["start"] = start.toString()
        map["num"] = "10"
        viewModel.onRefresh(map)
    }


    override fun onRetryClick() {
        refresh_layout.isRefreshing = false
        refresh_layout.isEnabled = false
        status_view.showLoadingView()
        onRefresh()
    }

}