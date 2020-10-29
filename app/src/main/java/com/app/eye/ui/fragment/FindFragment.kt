package com.app.eye.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.ui.activity.CategoryActivity
import com.app.eye.ui.activity.RankActivity
import com.app.eye.ui.activity.TopicSquareActivity
import com.app.eye.ui.adapter.DiscoverAdapter
import com.app.eye.ui.mvp.contract.FindContract
import com.app.eye.ui.mvp.model.entity.DiscoverEntity
import com.app.eye.ui.mvp.presenter.FindPresenter
import com.app.eye.widgets.STATUS_NO_NETWORK
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.NetworkUtils
import kotlinx.android.synthetic.main.fragment_find.*

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
        discoverAdapter.addChildClickViewIds(R.id.tv_right_text)
        discoverAdapter.setOnItemChildClickListener { _, view, position ->
            val item = discoverAdapter.getItem(position)

            val data = item.data
            val header = item.data.header
            var actionUrl: String? = if (!data.actionUrl.isNullOrEmpty()) data.actionUrl else if (
                !header?.actionUrl.isNullOrEmpty()
            ) header?.actionUrl else ""

            actionUrl ?: return@setOnItemChildClickListener
            when (view.id) {
                R.id.tv_right_text -> {
                    if (actionUrl.contains("ranklist")) {
                        ActivityUtils.startActivity(RankActivity::class.java)
                    } else if (actionUrl.contains("community")) {
                        TopicSquareActivity.startTopicSquareActivity("主题")
                    } else {
                        CategoryActivity.startActivity(header!!.title,
                            if (actionUrl.contains("categories")) 0 else 1)
                    }
                }
            }
        }
        recycler_view.adapter = discoverAdapter
//        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                if (!_mActivity.isFinishing) {
//                    if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING) {
//                        Glide.with(_mActivity)
//                            .resumeRequests()
//                    } else {
//                        Glide.with(_mActivity)
//                            .pauseRequests()
//                    }
//                }
//            }
//        })
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