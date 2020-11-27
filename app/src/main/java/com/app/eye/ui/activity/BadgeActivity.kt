package com.app.eye.ui.activity

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseActivity
import com.app.eye.base.BaseMvpActivity
import com.app.eye.ui.adapter.MedalAdapter
import com.app.eye.ui.mvp.contract.MineContract
import com.app.eye.ui.mvp.model.entity.BadgeEntity
import com.app.eye.ui.mvp.presenter.MinePresenter
import com.app.eye.widgets.MedalDialog
import com.app.eye.widgets.itemdecoration.LayoutMarginDecoration
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import kotlinx.android.synthetic.main.activity_badge.*

class BadgeActivity : BaseMvpActivity<MineContract.Presenter, MineContract.View>(),
    MineContract.View, SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

    private val medalAdapter = MedalAdapter(mutableListOf())

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
            mPresenter?.getTagMedals()
        }
    }

    override fun createPresenter(): MineContract.Presenter? = MinePresenter()

    override fun setMedalsData(entity: BadgeEntity?) {
        if (entity == null) {
            status_view.showEmptyView()
        } else {
            medalAdapter.loadMoreModule.isEnableLoadMore = true
            medalAdapter.setList(entity.itemList)
            medalAdapter.loadMoreModule.loadMoreEnd()
        }
    }

    override fun hideLoading() {
        super.hideLoading()
        status_view.showContentView()
        refresh_layout.isRefreshing = false
    }

    override fun onRefresh() {
        mPresenter?.getTagMedals()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = medalAdapter.getItem(position)
        MedalDialog.createDialog(item)
            .showDialog(supportFragmentManager)
    }
}