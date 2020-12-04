package com.app.eye.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMFragment
import com.app.eye.rx.checkSuccess
import com.app.eye.rx.urlToMap
import com.app.eye.ui.adapter.TagVideoAdapter
import com.app.eye.ui.mvvm.viewmodel.TagVideoViewModel
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_EMPTY
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_tag_video.recycler_view
import kotlinx.android.synthetic.main.fragment_tag_video.refresh_layout
import kotlinx.android.synthetic.main.fragment_tag_video.status_view
import org.koin.androidx.viewmodel.ext.android.viewModel

class TagDynamicFragment : BaseVMFragment(), SwipeRefreshLayout.OnRefreshListener,
    OnLoadMoreListener,
    OnItemClickListener {

    override fun getLayoutRes(): Int = R.layout.fragment_topic_detail

    companion object {
        @JvmStatic
        fun newInstance(id: String) =
            TagDynamicFragment().apply {
                arguments = Bundle().apply {
                    putString("id", id)
                }
            }
    }

    private val viewModel by viewModel<TagVideoViewModel>()

    private lateinit var id: String

    private var nextPageUrl: String? = ""

    private lateinit var tagVideoAdapter: TagVideoAdapter

    private var isRefresh: Boolean = true


    override fun reConnect() {
    }

    override fun initView() {
        id = arguments?.getString("id")!!
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        tagVideoAdapter = TagVideoAdapter(mutableListOf())
        tagVideoAdapter.apply {
            loadMoreModule.setOnLoadMoreListener(this@TagDynamicFragment)
            setOnItemClickListener(this@TagDynamicFragment)
            loadMoreModule.isEnableLoadMore = false
        }
        recycler_view.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = tagVideoAdapter
    }

    override fun initData() {
        isRefresh = true
        refresh_layout.post {
            viewModel.onRefresh(1, hashMapOf("id" to id), isFirst = true)
        }
    }

    override fun startObserver() {
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
        viewModel.entityLiveData.observe(this, Observer {
            it.checkSuccess({ entity ->
                nextPageUrl = entity.nextPageUrl
                if (isRefresh) {
                    tagVideoAdapter.loadMoreModule.isEnableLoadMore = true
                    tagVideoAdapter.setList(entity.itemList)
                    if (TextUtils.isEmpty(nextPageUrl)) tagVideoAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    tagVideoAdapter.loadMoreModule.isEnableLoadMore = true
                    tagVideoAdapter.addData(entity.itemList)
                    if (TextUtils.isEmpty(nextPageUrl)) {
                        tagVideoAdapter.loadMoreModule.loadMoreEnd()
                    } else {
                        tagVideoAdapter.loadMoreModule.loadMoreComplete()
                    }
                }
                refresh_layout.isEnabled = true
            }, {
                status_view.showErrorView()
            })
        })
    }


    override fun hideLoading() {
        refresh_layout.isRefreshing = false
        status_view.showContentView()
    }

    override fun onRefresh() {
        tagVideoAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        viewModel.onRefresh(1, hashMapOf("id" to id), isFirst = false)
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            viewModel.onLoadMore(1, map)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }
}