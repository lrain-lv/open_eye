package com.app.eye.ui.fragment

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
import com.app.eye.ui.activity.VideoDetailActivity
import com.app.eye.ui.adapter.PopularAdapter
import com.app.eye.ui.mvvm.factory.InjectorUtil
import com.app.eye.ui.mvvm.viewmodel.PopularViewModel
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_EMPTY
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.fragment_popular.refresh_layout
import kotlinx.android.synthetic.main.fragment_popular.status_view

// TODO: Rename parameter arguments, choose names that match
private const val STRATEGY = "strategy"
private const val ID = "id"

class PopularFragment : BaseVMFragment(), SwipeRefreshLayout.OnRefreshListener, OnItemClickListener,
    OnLoadMoreListener {
    companion object {
        @JvmStatic
        fun newInstance(strategy: String, id: String) =
            PopularFragment().apply {
                arguments = Bundle().apply {
                    putString(STRATEGY, strategy)
                    putString(ID, id)
                }
            }
    }

    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getPopularVMFactory()).get(
            PopularViewModel::class.java
        )
    }
    private var strategy: String? = ""
    private var id: String? = ""

    private var nextPageUrl: String? = ""

    private var isRefresh: Boolean = true
    private lateinit var popAdapter: PopularAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            strategy = it.getString(STRATEGY)
            id = it.getString(ID)
        }
    }


    override fun getLayoutRes(): Int = R.layout.fragment_popular

    override fun reConnect() {
    }

    override fun initView() {
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        popAdapter = PopularAdapter(mutableListOf())
        popAdapter.loadMoreModule.setOnLoadMoreListener(this)
        popAdapter.loadMoreModule.isEnableLoadMore = false
        recycler.adapter = popAdapter
        popAdapter.setOnItemClickListener(this)
    }

    override fun initData() {
        isRefresh = true
        status_view.showLoadingView()
        refresh_layout.post {
            viewModel.onRefresh(hashMapOf("id" to id!!, "strategy" to strategy!!), isFirst = true)
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

        viewModel.entityLiveData.observe(this, Observer {
            it.checkSuccess(onSuccess = { entity ->
                nextPageUrl = entity.nextPageUrl
                if (isRefresh) {
                    popAdapter.loadMoreModule.isEnableLoadMore = true
                    popAdapter.setList(entity.itemList)
                    if (TextUtils.isEmpty(nextPageUrl)) popAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    popAdapter.loadMoreModule.isEnableLoadMore = true
                    popAdapter.addData(entity.itemList)
                    if (TextUtils.isEmpty(nextPageUrl)) {
                        popAdapter.loadMoreModule.loadMoreEnd()
                    } else {
                        popAdapter.loadMoreModule.loadMoreComplete()
                    }
                }
                refresh_layout.isEnabled = true

            }, onError = { status_view.showErrorView() })
        })
    }

    override fun onRefresh() {
        popAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        viewModel.onRefresh(hashMapOf("id" to id!!, "strategy" to strategy!!), isFirst = false)
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            viewModel.onLoadMore(map)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = popAdapter.getItem(position)
        VideoDetailActivity.startActivity(item.data.content.data.id.toString())
    }
}