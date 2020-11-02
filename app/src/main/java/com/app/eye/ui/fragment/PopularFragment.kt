package com.app.eye.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.rx.urlToMap
import com.app.eye.ui.activity.VideoDetailActivity
import com.app.eye.ui.adapter.PopularAdapter
import com.app.eye.ui.mvp.contract.PopularContract
import com.app.eye.ui.mvp.model.entity.RecentPopularEntity
import com.app.eye.ui.mvp.presenter.PopularPresenter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_popular.*

// TODO: Rename parameter arguments, choose names that match
private const val STRATEGY = "strategy"
private const val ID = "id"

class PopularFragment : BaseMvpFragment<PopularContract.Presenter, PopularContract.View>(),
    PopularContract.View, SwipeRefreshLayout.OnRefreshListener, OnItemClickListener,
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

    override fun createPresenter(): PopularContract.Presenter? = PopularPresenter()

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
            mPresenter?.getRecentShareCount(
                hashMapOf("id" to id!!, "strategy" to strategy!!)
            )
        }
    }

    override fun setRecentShareCountResponse(entity: RecentPopularEntity?) {
        entity ?: status_view.showEmptyView()
        nextPageUrl = entity?.nextPageUrl
        if (isRefresh) {
            popAdapter.loadMoreModule.isEnableLoadMore = true
            popAdapter.setList(entity?.itemList)
            if (TextUtils.isEmpty(nextPageUrl)) popAdapter.loadMoreModule.loadMoreEnd()
        } else {
            popAdapter.loadMoreModule.isEnableLoadMore = true
            popAdapter.addData(entity?.itemList!!)
            if (TextUtils.isEmpty(nextPageUrl)) {
                popAdapter.loadMoreModule.loadMoreEnd()
            } else {
                popAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        refresh_layout.isEnabled = true
    }

    override fun hideLoading() {
        refresh_layout.isRefreshing = false
        status_view.showContentView()
    }

    override fun onRefresh() {
        popAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        mPresenter?.getRecentShareCount(
            hashMapOf("id" to id!!, "strategy" to strategy!!)
        )
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            mPresenter?.getRecentShareCount(map)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = popAdapter.getItem(position)
        VideoDetailActivity.startActivity(item.data.content.data.id.toString())
    }
}