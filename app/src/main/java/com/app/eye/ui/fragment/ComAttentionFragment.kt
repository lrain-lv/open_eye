package com.app.eye.ui.fragment

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
import com.app.eye.ui.adapter.AttentionAdapter
import com.app.eye.ui.mvvm.viewmodel.BrandWallViewModel
import com.app.eye.ui.mvvm.viewmodel.CommunityViewModel
import com.app.eye.widgets.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_com_attention.recycler_view
import kotlinx.android.synthetic.main.fragment_com_attention.refresh_layout
import kotlinx.android.synthetic.main.fragment_com_attention.status_view
import org.koin.androidx.viewmodel.ext.android.viewModel

class ComAttentionFragment : BaseVMFragment(), SwipeRefreshLayout.OnRefreshListener,
    MultipleStatusView.OnRetryClickListener, OnLoadMoreListener, OnItemClickListener {
    companion object {
        @JvmStatic
        fun newInstance() =
            ComAttentionFragment().apply {

            }
    }

    private val viewModel by viewModel<CommunityViewModel>()
    private var nextPageUrl: String? = ""

    private var isRefresh: Boolean = true
    private lateinit var attentionAdapter: AttentionAdapter
    override fun getLayoutRes(): Int = R.layout.fragment_com_attention

    override fun reConnect() {
    }

    override fun initView() {
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        attentionAdapter = AttentionAdapter(mutableListOf())
        attentionAdapter.loadMoreModule.setOnLoadMoreListener(this)
        attentionAdapter.loadMoreModule.isEnableLoadMore = false
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = attentionAdapter
        attentionAdapter.setOnItemClickListener(this)
    }

    override fun initData() {
        isRefresh = true
        status_view.showLoadingView()
        refresh_layout.post {
            viewModel.onRefresh(1, hashMapOf(), isFirst = true)
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
        viewModel.comAttLiveData.observe(this, Observer {
            it.checkSuccess(
                onSuccess = { entity ->
                    nextPageUrl = entity.nextPageUrl
                    if (isRefresh) {
                        attentionAdapter.loadMoreModule.isEnableLoadMore = true
                        attentionAdapter.setList(entity.itemList)
                        if (TextUtils.isEmpty(nextPageUrl)) attentionAdapter.loadMoreModule.loadMoreEnd()
                    } else {
                        attentionAdapter.loadMoreModule.isEnableLoadMore = true
                        attentionAdapter.addData(entity.itemList)
                        if (TextUtils.isEmpty(nextPageUrl)) {
                            attentionAdapter.loadMoreModule.loadMoreEnd()
                        } else {
                            attentionAdapter.loadMoreModule.loadMoreComplete()
                        }
                    }
                    refresh_layout.isEnabled = true
                },
                onError = { status_view.showErrorView() }
            )
        })
    }

    override fun onRefresh() {
        attentionAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        viewModel.onRefresh(1, hashMapOf())
    }

    override fun onRetryClick() {
        attentionAdapter.loadMoreModule.isEnableLoadMore = false
        status_view.showLoadingView()
        isRefresh = true
        viewModel.onRefresh(1, hashMapOf())
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            viewModel.onLoadMore(1, map)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }
}