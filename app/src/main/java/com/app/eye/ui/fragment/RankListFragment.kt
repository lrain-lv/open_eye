package com.app.eye.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMFragment
import com.app.eye.rx.checkSuccess
import com.app.eye.rx.urlToMap
import com.app.eye.ui.activity.VideoDetailActivity
import com.app.eye.ui.adapter.RankAdapter
import com.app.eye.ui.mvvm.viewmodel.BrandWallViewModel
import com.app.eye.ui.mvvm.viewmodel.RankViewModel
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_EMPTY
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_rank_list.recycler_view
import kotlinx.android.synthetic.main.fragment_rank_list.refresh_layout
import kotlinx.android.synthetic.main.fragment_rank_list.status_view
import org.koin.androidx.viewmodel.ext.android.viewModel

class RankListFragment : BaseVMFragment(), SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener,
    OnItemClickListener {
    private var type: Int = 0

    companion object {
        @JvmStatic
        fun newInstance(type: Int) =
            RankListFragment().apply {
                arguments = Bundle().apply {
                    putInt("type", type)
                }
            }
    }
    private val viewModel by viewModel<RankViewModel>()
    private var nextPageUrl: String? = ""

    private var isRefresh: Boolean = true
    private lateinit var rankAdapter: RankAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getInt("type", 0)
        }
    }

    override fun getLayoutRes(): Int = R.layout.fragment_rank_list

    override fun reConnect() {
    }

    override fun initView() {
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        rankAdapter = RankAdapter(mutableListOf())
        rankAdapter.apply {
            loadMoreModule.setOnLoadMoreListener(this@RankListFragment)
            loadMoreModule.isEnableLoadMore = false
            setOnItemClickListener(this@RankListFragment)
        }
        recycler_view.apply {
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = rankAdapter
        }
    }

    override fun initData() {
        isRefresh = true
        status_view.showLoadingView()
        refresh_layout.post {
            viewModel.onRefresh(type, hashMapOf(), isFirst = true)
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
            it.checkSuccess({ data ->
                nextPageUrl = data.nextPageUrl
                if (isRefresh) {
                    rankAdapter.loadMoreModule.isEnableLoadMore = true
                    rankAdapter.setList(data.itemList)
                    if (TextUtils.isEmpty(nextPageUrl)) rankAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    rankAdapter.loadMoreModule.isEnableLoadMore = true
                    rankAdapter.addData(data.itemList)
                    if (TextUtils.isEmpty(nextPageUrl)) {
                        rankAdapter.loadMoreModule.loadMoreEnd()
                    } else {
                        rankAdapter.loadMoreModule.loadMoreComplete()
                    }
                }
                refresh_layout.isEnabled = true
            }, {
                status_view.showErrorView()
            })
        })
    }

    override fun hideLoading() {
        refresh_layout.isRefreshing = false
        status_view.showContentView()
    }

    override fun onRefresh() {
        rankAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        viewModel.onRefresh(type, hashMapOf())
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            viewModel.onLoadMore(type, map)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = rankAdapter.getItem(position)
        VideoDetailActivity.startActivity(item.data.content.data.id.toString())
    }
}