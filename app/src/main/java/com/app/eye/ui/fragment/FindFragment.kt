package com.app.eye.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.rx.actionUrlToMap
import com.app.eye.ui.activity.*
import com.app.eye.ui.adapter.DiscoverAdapter
import com.app.eye.ui.mvp.contract.FindContract
import com.app.eye.ui.mvp.model.entity.DiscoverEntity
import com.app.eye.ui.mvp.model.entity.Item
import com.app.eye.ui.mvp.presenter.FindPresenter
import com.app.eye.widgets.STATUS_NO_NETWORK
import com.app.eye.widgets.videoplayer.AutoPlayScrollListener
import com.app.eye.widgets.videoplayer.Jzvd
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

    private lateinit var discoverAdapter: DiscoverAdapter
    override fun initView() {
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        discoverAdapter = DiscoverAdapter(mutableListOf())
        recycler_view.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        discoverAdapter.loadMoreModule.setOnLoadMoreListener { }
        discoverAdapter.setOnItemClickListener { adapter, view, position ->
            val item = discoverAdapter.getItem(position)
            when (item.itemType) {
                Item.DISCOVER_VIDEO_SMALL_CARD -> {
                    VideoDetailActivity.startActivity(item.data.id.toString())
                }
                Item.DISCOVER_BRIEF_CARD -> {
                    TagVideoActivity.startActivity(item.data.id.toString(),
                        item.data.title,
                        item.data.icon,
                        item.data.description)
                }
            }
        }
        discoverAdapter.addChildClickViewIds(R.id.tv_right_text)
        discoverAdapter.setOnItemChildClickListener { _, view, position ->
            val item = discoverAdapter.getItem(position)
            val data = item.data
            val header = item.data.header
            val actionUrl: String? = if (!data.actionUrl.isNullOrEmpty()) data.actionUrl else if (
                !header?.actionUrl.isNullOrEmpty()
            ) header?.actionUrl else ""
            actionUrl ?: return@setOnItemChildClickListener
            when (view.id) {
                R.id.tv_right_text -> {
                    when {
                        actionUrl.contains("ranklist") -> {
                            ActivityUtils.startActivity(RankActivity::class.java)
                        }
                        actionUrl.contains("community") -> {
                            TopicSquareActivity.startTopicSquareActivity("主题")
                        }
                        actionUrl.contains("webview") -> {
                            WebActivity.startWebActivity(
                                url = actionUrl.actionUrlToMap()["url"] ?: error(""),
                                title = actionUrl.actionUrlToMap()["title"] ?: error("")
                            )
                        }
                        else -> {
                            CategoryActivity.startActivity(header!!.title,
                                if (actionUrl.contains("categories")) 0 else 1)
                        }
                    }
                }
            }
        }
        recycler_view.adapter = discoverAdapter
        recycler_view.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {}
            override fun onChildViewDetachedFromWindow(view: View) {
                val jzvd: Jzvd? = view.findViewById(R.id.jzvd)
                if (jzvd != null && Jzvd.CURRENT_JZVD != null && jzvd.jzDataSource.containsTheUrl(
                        Jzvd.CURRENT_JZVD.jzDataSource.currentUrl)
                ) {
                    if (Jzvd.CURRENT_JZVD != null && (Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN)) {
                        Jzvd.releaseAllVideos()
                    }
                }
            }
        })
        recycler_view.addOnScrollListener(AutoPlayScrollListener())
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

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        Jzvd.releaseAllVideos()
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

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos();
    }
}