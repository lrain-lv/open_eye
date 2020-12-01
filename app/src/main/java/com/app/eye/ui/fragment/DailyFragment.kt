package com.app.eye.ui.fragment

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMFragment
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.rx.actionUrlToMap
import com.app.eye.rx.urlToMap
import com.app.eye.ui.activity.CategoryActivity
import com.app.eye.ui.activity.VideoDetailActivity
import com.app.eye.ui.activity.WebActivity
import com.app.eye.ui.adapter.DailyAdapter
import com.app.eye.ui.mvp.contract.DailyContract
import com.app.eye.ui.entity.ItemDaily
import com.app.eye.ui.mvp.presenter.DailyPresenter
import com.app.eye.ui.mvvm.factory.InjectorUtil
import com.app.eye.ui.mvvm.viewmodel.DailyViewModel
import com.app.eye.widgets.STATUS_NO_NETWORK
import com.blankj.utilcode.util.NetworkUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_daily.*

class DailyFragment : BaseVMFragment(), SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener,
    OnItemClickListener, OnItemChildClickListener {

    companion object {
        @JvmStatic
        fun newInstance() = DailyFragment()
    }

    private lateinit var dailyAdapter: DailyAdapter
    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getDailyVMFactory()).get(
            DailyViewModel::class.java
        )
    }

    override fun getLayoutRes(): Int = R.layout.fragment_daily
    override fun startObserver() {
        viewModel.isRefresh.observe(this, Observer {
            refresh_layout.isRefreshing = it
        })
        viewModel.paramData.observe(this, Observer {
            if (it is EyeResult.Success) {
                status_view.showContentView()
                val entity = it.data
                entity.itemList.isEmpty() ?: status_view.showEmptyView()
                nextPage = entity.nextPageUrl
                if (isRefresh) {
                    dailyAdapter.loadMoreModule.isEnableLoadMore = true
                    if (TextUtils.isEmpty(nextPage)) {
                        dailyAdapter.loadMoreModule.loadMoreEnd()
                    }
                    dailyAdapter.setList(entity.itemList)
                } else {
                    dailyAdapter.loadMoreModule.isEnableLoadMore = true
                    if (TextUtils.isEmpty(nextPage)) {
                        dailyAdapter.loadMoreModule.loadMoreEnd()
                    } else {
                        dailyAdapter.loadMoreModule.loadMoreComplete()
                    }
                    dailyAdapter.addData(entity.itemList)
                }
                refresh_layout.isEnabled = true
            } else {
                if (isRefresh) {
                    status_view.showErrorView()
                }
            }
        })
    }

    private var isRefresh: Boolean = true

    private var nextPage: String? = ""

    override fun initView() {
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        dailyAdapter = DailyAdapter(mutableListOf())
        recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = dailyAdapter
        dailyAdapter.apply {
            loadMoreModule.setOnLoadMoreListener(this@DailyFragment)
            loadMoreModule.isEnableLoadMore = false
            addChildClickViewIds(R.id.tv_right_text)
            setOnItemChildClickListener(this@DailyFragment)
            setOnItemClickListener(this@DailyFragment)
        }
    }

    override fun initData() {
        if (!NetworkUtils.isConnected()) {
            status_view.showNoNetworkView()
        } else {
            status_view.showLoadingView()
            recycler.post { viewModel.refresh(mapOf(), true) }
        }
    }

    override fun useLazyLoad(): Boolean = true

    override fun reConnect() {
        if (status_view.getViewStatus() == STATUS_NO_NETWORK) {
            status_view.showLoadingView()
            recycler.post { viewModel.refresh(mapOf()) }
        }
    }

    override fun onRefresh() {
        isRefresh = true
        recycler.post { viewModel.refresh(mapOf()) }
    }

    override fun onLoadMore() {
        isRefresh = false
        refresh_layout.isEnabled = false
        recycler.post { viewModel.loadMore(nextPage!!.urlToMap()) }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = dailyAdapter.getItem(position)
        when (item.itemType) {
            ItemDaily.DAILY_VIDEO -> {
                VideoDetailActivity.startActivity(item.data.header.id.toString())
            }
            ItemDaily.DAILY_INFORMATION -> {
                val actionUrl = item.data.actionUrl
                WebActivity.startWebActivity(
                    url = actionUrl!!.actionUrlToMap()["url"] ?: error(""),
                    title = actionUrl.actionUrlToMap()["title"] ?: error("")
                )
            }
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when (view.id) {
            R.id.tv_right_text -> {
                if (dailyAdapter.getItem(position).data.actionUrl?.contains("common")!!) {
                    CategoryActivity.startActivity("全部资讯", 2)
                }
            }
        }
    }
}