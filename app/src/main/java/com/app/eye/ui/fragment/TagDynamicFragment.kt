package com.app.eye.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseFragment
import com.app.eye.base.BaseMvpFragment
import com.app.eye.rx.urlToMap
import com.app.eye.ui.adapter.TagVideoAdapter
import com.app.eye.ui.mvp.contract.TagVideoContract
import com.app.eye.ui.mvp.model.entity.TagIndexEntity
import com.app.eye.ui.mvp.model.entity.TagVideoEntity
import com.app.eye.ui.mvp.presenter.TagVideoPresenter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_tag_video.*

class TagDynamicFragment : BaseMvpFragment<TagVideoContract.Presenter, TagVideoContract.View>(),
    TagVideoContract.View, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener,
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
        status_view.showLoadingView()
        refresh_layout.post {
            mPresenter?.getTagDynamicRequest(
                hashMapOf("id" to id)
            )
        }
    }

    override fun createPresenter(): TagVideoContract.Presenter? = TagVideoPresenter()

    override fun setTagIndexResponse(entity: TagIndexEntity?) {
    }

    override fun setTagVideoResponse(entity: TagVideoEntity?) {
    }

    override fun setTagDynamicResponse(entity: TagVideoEntity?) {
        entity ?: status_view.showEmptyView()
        nextPageUrl = entity?.nextPageUrl
        if (isRefresh) {
            tagVideoAdapter.loadMoreModule.isEnableLoadMore = true
            tagVideoAdapter.setList(entity?.itemList)
            if (TextUtils.isEmpty(nextPageUrl)) tagVideoAdapter.loadMoreModule.loadMoreEnd()
        } else {
            tagVideoAdapter.loadMoreModule.isEnableLoadMore = true
            tagVideoAdapter.addData(entity?.itemList!!)
            if (TextUtils.isEmpty(nextPageUrl)) {
                tagVideoAdapter.loadMoreModule.loadMoreEnd()
            } else {
                tagVideoAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        refresh_layout.isEnabled = true
    }

    override fun hideLoading() {
        refresh_layout.isRefreshing = false
        status_view.showContentView()
    }

    override fun onRefresh() {
        tagVideoAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        mPresenter?.getTagDynamicRequest(
            hashMapOf("id" to id)
        )
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            mPresenter?.getTagDynamicRequest(map)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }
}