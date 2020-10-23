package com.app.eye.ui.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseFragment
import com.app.eye.base.BaseMvpFragment
import com.app.eye.ui.adapter.DiscoverAdapter
import com.app.eye.ui.mvp.contract.FindContract
import com.app.eye.ui.mvp.model.entity.DiscoverEntity
import com.app.eye.ui.mvp.presenter.FindPresenter
import com.app.eye.widgets.MultipleStatusView
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_NO_NETWORK
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_daily.*
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.fragment_find.refresh_layout
import kotlinx.android.synthetic.main.fragment_find.status_view

class FindFragment : BaseMvpFragment<FindContract.Presenter, FindContract.View>(),
    FindContract.View, SwipeRefreshLayout.OnRefreshListener {

    companion object {
        @JvmStatic
        fun newInstance() =
            FindFragment().apply {}
    }

    override fun getLayoutRes(): Int = R.layout.fragment_find

    lateinit var discoverAdapter: DiscoverAdapter
    override fun initView() {

        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        discoverAdapter = DiscoverAdapter(mutableListOf())
        recycler_view.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        discoverAdapter.loadMoreModule.setOnLoadMoreListener { }
        recycler_view.adapter = discoverAdapter
    }

    override fun initData() {
        if (!NetworkUtils.isConnected()) {
            status_view.showNoNetworkView()

        } else {
            status_view.showLoadingView()
            recycler_view.post { mPresenter?.getDiscoveryData() }
        }
    }

    override fun onRefresh() {
        refresh_layout.isRefreshing = true
        mPresenter?.getDiscoveryData()
    }

    override fun createPresenter(): FindContract.Presenter? = FindPresenter()


    override fun setResponse(discoverEntity: DiscoverEntity) {
        if (discoverEntity.itemList.isEmpty()) {
            status_view.showEmptyView()
        } else {
            status_view.showContentView()
            discoverAdapter.setList(discoverEntity.itemList)
            discoverAdapter.loadMoreModule.loadMoreEnd(gone = false)
            discoverAdapter.banner.addBannerLifecycleObserver(this)
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
        status_view.showContentView()
        refresh_layout.isRefreshing = false
    }

    override fun reConnect() {
        if (status_view.getViewStatus() == STATUS_NO_NETWORK) {
            status_view.showLoadingView()
            mPresenter?.getDiscoveryData()
        }
    }
}