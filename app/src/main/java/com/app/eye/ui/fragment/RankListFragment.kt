package com.app.eye.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.rx.urlToMap
import com.app.eye.ui.adapter.RankAdapter
import com.app.eye.ui.mvp.contract.RankContract
import com.app.eye.ui.mvp.model.entity.RankEntity
import com.app.eye.ui.mvp.presenter.RankPresenter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_rank_list.*

class RankListFragment : BaseMvpFragment<RankContract.Presenter, RankContract.View>(),
    RankContract.View, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener,
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
            mPresenter?.getRankRequest(
                type, hashMapOf()
            )
        }
    }

    override fun createPresenter(): RankContract.Presenter? = RankPresenter()

    override fun setRankResponse(type: Int, data: RankEntity?) {
        data ?: status_view.showEmptyView()
        nextPageUrl = data?.nextPageUrl
        if (isRefresh) {
            rankAdapter.loadMoreModule.isEnableLoadMore = true
            rankAdapter.setList(data?.itemList)
            if (TextUtils.isEmpty(nextPageUrl)) rankAdapter.loadMoreModule.loadMoreEnd()
        } else {
            rankAdapter.loadMoreModule.isEnableLoadMore = true
            rankAdapter.addData(data?.itemList!!)
            if (TextUtils.isEmpty(nextPageUrl)) {
                rankAdapter.loadMoreModule.loadMoreEnd()
            } else {
                rankAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        refresh_layout.isEnabled = true
    }

    override fun hideLoading() {
        refresh_layout.isRefreshing = false
        status_view.showContentView()
    }

    override fun onRefresh() {
        rankAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        mPresenter?.getRankRequest(
            type, hashMapOf()
        )
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            mPresenter?.getRankRequest(type, map)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }
}