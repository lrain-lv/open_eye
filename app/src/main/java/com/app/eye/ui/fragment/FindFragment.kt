package com.app.eye.ui.fragment

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.base.mvvm.BaseVMFragment
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.rx.actionUrlToMap
import com.app.eye.ui.activity.*
import com.app.eye.ui.adapter.DiscoverAdapter
import com.app.eye.ui.mvp.contract.FindContract
import com.app.eye.ui.mvp.model.entity.DiscoverEntity
import com.app.eye.ui.mvp.model.entity.Item
import com.app.eye.ui.mvp.presenter.FindPresenter
import com.app.eye.ui.mvvm.factory.InjectorUtil
import com.app.eye.ui.mvvm.viewmodel.FindViewModel
import com.app.eye.widgets.STATUS_NO_NETWORK
import com.app.eye.widgets.videoplayer.AutoPlayScrollListener
import com.app.eye.widgets.videoplayer.Jzvd
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.NetworkUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_find.*
import org.w3c.dom.Entity

class FindFragment : BaseVMFragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        @JvmStatic
        fun newInstance() =
            FindFragment().apply {}
    }

    private val viewModel: FindViewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getFindVMFactory()
        ).get(FindViewModel::class.java)
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
                    TagVideoActivity.startActivity(
                        item.data.id.toString(),
                        item.data.title,
                        item.data.icon,
                        item.data.description
                    )
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
                            CategoryActivity.startActivity(
                                header!!.title,
                                if (actionUrl.contains("categories")) 0 else 1
                            )
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
                        Jzvd.CURRENT_JZVD.jzDataSource.currentUrl
                    )
                ) {
                    if (Jzvd.CURRENT_JZVD != null && (Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN)) {
                        Jzvd.releaseAllVideos()
                    }
                }
            }
        })
        recycler_view.addOnScrollListener(AutoPlayScrollListener())
    }

    override fun startObserver() {
        viewModel.paramData.observe(viewLifecycleOwner, Observer {
            if (it is EyeResult.Success) {
                val entity = it.data
                if (entity.itemList.isEmpty()) {
                    status_view.showEmptyView()
                } else {
                    status_view.showContentView()
                    discoverAdapter.setList(entity.itemList)
                    discoverAdapter.loadMoreModule.loadMoreEnd(gone = false)
                }
            } else {
                status_view.showEmptyView()
            }
        })

        viewModel.isRefresh.observe(viewLifecycleOwner, Observer {
            refresh_layout.isRefreshing = it
        })
    }

    override fun initData() {
        if (!NetworkUtils.isConnected()) {
            status_view.showNoNetworkView()
        } else {
            status_view.showLoadingView()
            recycler_view.post { viewModel.refresh(true) }
        }
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        Jzvd.releaseAllVideos()
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {

    }

    override fun reConnect() {
        if (status_view.getViewStatus() == STATUS_NO_NETWORK) {
            status_view.showLoadingView()
            viewModel.refresh()
        }
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos();
    }
}