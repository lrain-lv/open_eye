package com.app.eye.ui.activity

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMActivity
import com.app.eye.rx.checkSuccess
import com.app.eye.ui.adapter.MedalAdapter
import com.app.eye.ui.mvp.contract.MineContract
import com.app.eye.ui.mvp.presenter.MinePresenter
import com.app.eye.ui.mvvm.factory.InjectorUtil
import com.app.eye.ui.mvvm.viewmodel.BadgeViewModel
import com.app.eye.widgets.*
import com.app.eye.widgets.itemdecoration.LayoutMarginDecoration
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import kotlinx.android.synthetic.main.activity_badge.*
import kotlinx.android.synthetic.main.activity_badge.recycler_view
import kotlinx.android.synthetic.main.activity_badge.refresh_layout
import kotlinx.android.synthetic.main.activity_badge.status_view

class BadgeActivity : BaseVMActivity(), SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

    private val medalAdapter = MedalAdapter(mutableListOf())

    private val viewModel by lazy {
        ViewModelProvider(this, InjectorUtil.getBadgeVMFactory()).get(
            BadgeViewModel::class.java
        )
    }

    override fun getLayoutRes(): Int = R.layout.activity_badge
    override fun initView() {
        immersionBar.statusBarColor(R.color.white)
            .statusBarDarkFont(true)
            .fitsSystemWindows(true)
            .init()
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        tool_bar.setNavigationOnClickListener { onBackPressedSupport() }
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@BadgeActivity, 2)
            addItemDecoration(LayoutMarginDecoration(2, SizeUtils.dp2px(2f)))
            adapter = medalAdapter
        }
        medalAdapter.loadMoreModule.setOnLoadMoreListener { }
        medalAdapter.loadMoreModule.isEnableLoadMore = false
        medalAdapter.setOnItemClickListener(this)
    }

    override fun initData() {
        recycler_view.post {
            viewModel.onRefresh(isFirst = true)
        }
    }

    override fun startObserver() {
        viewModel.entityLiveData.observe(this, Observer {
            it.checkSuccess(
                { entity ->
                    medalAdapter.loadMoreModule.isEnableLoadMore = true
                    medalAdapter.setList(entity.itemList)
                    medalAdapter.loadMoreModule.loadMoreEnd()
                }, {
                    status_view.showEmptyView()
                }
            )
        })
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
    }

    override fun onRefresh() {
        viewModel.onRefresh()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = medalAdapter.getItem(position)
        MedalDialog.createDialog(item)
            .showDialog(supportFragmentManager)
    }
}