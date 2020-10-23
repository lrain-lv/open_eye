package com.app.eye.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.ui.adapter.DailyAdapter
import com.app.eye.ui.mvp.contract.DailyContract
import com.app.eye.ui.mvp.model.entity.DailyEntity
import com.app.eye.ui.mvp.presenter.DailyPresenter
import com.app.eye.widgets.STATUS_NO_NETWORK
import com.blankj.utilcode.util.NetworkUtils
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_daily.*
import kotlinx.android.synthetic.main.fragment_daily.refresh_layout
import kotlinx.android.synthetic.main.fragment_daily.status_view
import kotlinx.android.synthetic.main.fragment_find.*

class DailyFragment : BaseMvpFragment<DailyContract.Presenter, DailyContract.View>(),
    DailyContract.View, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    companion object {
        @JvmStatic
        fun newInstance() = DailyFragment()
    }

    private lateinit var dailyAdapter: DailyAdapter

    override fun getLayoutRes(): Int = R.layout.fragment_daily

    private var isRefresh: Boolean = true

    private var nextPage: String? = ""

    override fun initView() {
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        dailyAdapter = DailyAdapter(mutableListOf())
        recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = dailyAdapter
        dailyAdapter.loadMoreModule.setOnLoadMoreListener(this)
    }

    override fun initData() {
        if (!NetworkUtils.isConnected()) {
            status_view.showNoNetworkView()
        } else {
            status_view.showLoadingView()
            recycler.post { mPresenter?.getDailyData(isRefresh) }
        }
    }

    override fun createPresenter(): DailyContract.Presenter? = DailyPresenter()

    override fun setDailyEntity(entity: DailyEntity?) {
        entity ?: status_view.showErrorView()
        entity?.itemList?.isEmpty() ?: status_view.showEmptyView()
        nextPage = entity?.nextPageUrl
        if (isRefresh) {
            if (TextUtils.isEmpty(nextPage)) {
                dailyAdapter.loadMoreModule.loadMoreEnd()
            }
            dailyAdapter.setList(entity?.itemList)
        } else {
            if (TextUtils.isEmpty(nextPage)) {
                dailyAdapter.loadMoreModule.loadMoreEnd()
            } else {
                dailyAdapter.loadMoreModule.loadMoreComplete()
            }
            dailyAdapter.addData(entity?.itemList!!)
        }
        refresh_layout.isEnabled = true
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
        status_view.showContentView()
        refresh_layout.isRefreshing = false
    }
    
    override fun useLazyLoad(): Boolean = true
    override fun reConnect() {
        if (status_view.getViewStatus() == STATUS_NO_NETWORK) {
            status_view.showLoadingView()
            mPresenter?.getDailyData(true)
        }
    }

    override fun onRefresh() {
        isRefresh = true
        mPresenter?.getDailyData()
    }

    override fun onLoadMore() {
        isRefresh = false
        refresh_layout.isEnabled = false
        mPresenter?.getDailyData(isRefresh, stringToMp(nextPage!!))
    }


    private fun stringToMp(s: String): Map<String, String> {
        var map = hashMapOf<String, String>()
        val index = s.indexOf("?")
        val result = s.substring(index + 1)
        val split = result.split("&")
        split.forEach {
            map[it.split("=")[0]] = it.split("=")[1]
        }
        return map
    }


}