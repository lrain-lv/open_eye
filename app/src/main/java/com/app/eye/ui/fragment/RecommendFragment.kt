package com.app.eye.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Switch
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.base.mvvm.BaseVMFragment
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.rx.urlToMap
import com.app.eye.ui.activity.*
import com.app.eye.ui.adapter.HomeRecAdapter
import com.app.eye.ui.mvp.contract.HomeRecContract
import com.app.eye.ui.mvp.model.entity.HomeRecEntity
import com.app.eye.ui.mvp.model.entity.HomeRecItem
import com.app.eye.ui.mvp.presenter.HomeRecPresenter
import com.app.eye.ui.mvvm.factory.InjectorUtil
import com.app.eye.ui.mvvm.factory.RecommendVMFactory
import com.app.eye.ui.mvvm.viewmodel.RecommendViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_recommend.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.jvm.internal.ReflectionFactory

class RecommendFragment : BaseVMFragment(),
    SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener,
    OnItemClickListener, OnItemChildClickListener {

    private val viewModel: RecommendViewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getRecommendVMFactory()
        ).get(RecommendViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RecommendFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private var nextPageUrl: String? = ""

    private var isRefresh: Boolean = true

    private lateinit var homeRecAdapter: HomeRecAdapter
    override fun getLayoutRes(): Int = R.layout.fragment_recommend

    override fun initView() {
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        homeRecAdapter = HomeRecAdapter(mutableListOf())
        recycler_view.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        homeRecAdapter.apply {
            loadMoreModule.setOnLoadMoreListener(this@RecommendFragment)
            loadMoreModule.isEnableLoadMore = false
            setOnItemClickListener { adapter, view, position ->

            }
            addChildClickViewIds(R.id.tv_right_text)
            setOnItemClickListener(this@RecommendFragment)
            setOnItemChildClickListener(this@RecommendFragment)
        }
        recycler_view.adapter = homeRecAdapter
    }

    override fun initData() {
        if (!NetworkUtils.isConnected()) {
            status_view.showNoNetworkView()
        } else {
            status_view.showLoadingView()
            recycler_view.post { viewModel.refresh(true, mapOf("page" to "0")) }
        }
    }
    override fun startObserver() {
        viewModel.entityLiveData.observe(this, Observer {
            if (it is EyeResult.Success) {
                status_view.showContentView()
                val entity = it.data
                nextPageUrl = entity.nextPageUrl
                if (isRefresh) {
                    homeRecAdapter.loadMoreModule.isEnableLoadMore = true
                    homeRecAdapter.setList(entity.itemList)
                    if (TextUtils.isEmpty(nextPageUrl)) homeRecAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    homeRecAdapter.loadMoreModule.isEnableLoadMore = true
                    homeRecAdapter.addData(entity.itemList)
                    if (TextUtils.isEmpty(nextPageUrl)) {
                        homeRecAdapter.loadMoreModule.loadMoreEnd()
                    } else {
                        homeRecAdapter.loadMoreModule.loadMoreComplete()
                    }
                }
                refresh_layout.isEnabled = true
            } else {
                if (isRefresh) {
                    status_view.showEmptyView()
                } else {
                    homeRecAdapter.loadMoreModule.isEnableLoadMore = true
                    homeRecAdapter.loadMoreModule.loadMoreFail()
                }
            }
        })

        viewModel.isRefresh.observe(this, Observer {
            refresh_layout.isRefreshing = it
        })
    }

    override fun onRefresh() {
        isRefresh = true
        homeRecAdapter.loadMoreModule.isEnableLoadMore = false
        viewModel.refresh(map = mapOf("page" to "0"))
    }


    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            viewModel.loadMore(map)
        }

    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = homeRecAdapter.getItem(position)
        when (item.type) {
            "followCard" -> {
                val id = item.data.content.data.id.toString()
                VideoDetailActivity.startActivity(id)
            }
            "videoSmallCard" -> {
                val id = item.data.id.toString()
                VideoDetailActivity.startActivity(id)
            }
            "briefCard" -> {
//                if (StringUtils.equals("TagBriefCard",
//                        data.dataType)
//                ) HomeRecItem.HOME_TAG_BRIEF_CARD else HomeRecItem.HOME_TOPIC_BRIEF_CARD
            }

        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = homeRecAdapter.getItem(position)
        val data = item.data
        val header = item.data.header
        val actionUrl: String? =
            if (!data.actionUrl.isNullOrEmpty()) data.actionUrl else if (
                !header?.actionUrl.isNullOrEmpty()
            ) header?.actionUrl else ""
        actionUrl ?: return
        when (view.id) {
            R.id.tv_right_text -> {
                when {
                    actionUrl.contains("common/") -> {
                        CategoryActivity.startActivity("全部资讯", 2)
                    }
                    actionUrl.contains("tag/") -> {
                        val s = actionUrl.substring(17)
                        val id = s.substring(0, s.indexOf("/"))
                        TagVideoActivity.startActivity(
                            id,
                            item.data.title,
                            "",
                            item.data.description ?: ""
                        )
                    }
                    actionUrl.contains("ranklist/") -> {
                        ActivityUtils.startActivity(RankActivity::class.java)
                    }
                    actionUrl.contains("community/") -> {
                        TopicSquareActivity.startTopicSquareActivity("主题")
                    }
                    else -> {

                    }
//                        actionUrl.contains("webview") -> {
//                            WebActivity.startWebActivity(
//                                url = actionUrl.actionUrlToMap()["url"] ?: error(""),
//                                title = actionUrl.actionUrlToMap()["title"] ?: error("")
//                            )
//                        }
//                        else -> {
//                            CategoryActivity.startActivity(header!!.title,
//                                if (actionUrl.contains("categories")) 0 else 1)
//                        }
                }
            }
        }
    }
}