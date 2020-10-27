package com.app.eye.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpFragment
import com.app.eye.rx.urlToMap
import com.app.eye.ui.adapter.TopicChildAdapter
import com.app.eye.ui.mvp.contract.TopicContact
import com.app.eye.ui.mvp.model.entity.TabChildEntity
import com.app.eye.ui.mvp.model.entity.TagTabEntity
import com.app.eye.ui.mvp.model.entity.TopicListEntity
import com.app.eye.ui.mvp.presenter.TopicPresenter
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_rec.refresh_layout
import kotlinx.android.synthetic.main.fragment_rec.status_view
import kotlinx.android.synthetic.main.fragment_topic_square_child.*

class TopicSquareChildFragment : BaseMvpFragment<TopicContact.Presenter, TopicContact.View>(),
    TopicContact.View, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    companion object {
        @JvmStatic
        fun newInstance(id: Int) =
            TopicSquareChildFragment().apply {
                arguments = Bundle().apply {
                    putInt("id", id)
                }
            }
    }

    private var urlId = -1

    private var nextPageUrl: String? = ""

    private lateinit var topicChildAdapter: TopicChildAdapter

    private var isRefresh: Boolean = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        urlId = arguments?.getInt("id", -1)!!
        urlId = if (urlId == -1) 0 else urlId
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
        refresh_layout.isRefreshing = false
        status_view.showContentView()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_topic_square_child

    override fun reConnect() {

    }

    override fun initView() {
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        topicChildAdapter = TopicChildAdapter(mutableListOf())
        topicChildAdapter.loadMoreModule.setOnLoadMoreListener(this)
        topicChildAdapter.loadMoreModule.isEnableLoadMore = false
        recycler.adapter = topicChildAdapter
        recycler.setHasFixedSize(true)
//        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        isRefresh = true
        status_view.showLoadingView()
        refresh_layout.post {
            mPresenter?.getTabChildRequest(
                urlId,
                hashMapOf("isRecTab" to if (urlId == 0) "true" else "false")
            )
        }
    }

    override fun setTabResponse(data: TagTabEntity?) {
    }

    override fun setTabChildResponse(data: TabChildEntity?) {
        data ?: status_view.showEmptyView()
        nextPageUrl = data?.nextPageUrl
        Logger.e("ddd$nextPageUrl")
        if (isRefresh) {
            topicChildAdapter.loadMoreModule.isEnableLoadMore = true
            topicChildAdapter.setList(data?.itemList)
            if (TextUtils.isEmpty(nextPageUrl)) topicChildAdapter.loadMoreModule.loadMoreEnd()
        } else {
            topicChildAdapter.loadMoreModule.isEnableLoadMore = true
            topicChildAdapter.addData(data?.itemList!!)
            if (TextUtils.isEmpty(nextPageUrl)) {
                topicChildAdapter.loadMoreModule.loadMoreEnd()
            } else {
                topicChildAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        refresh_layout.isEnabled = true
    }

    override fun setTopicListResponse(data: TopicListEntity?) {

    }

    override fun createPresenter(): TopicContact.Presenter? = TopicPresenter()

    override fun onRefresh() {
        topicChildAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        mPresenter?.getTabChildRequest(
            urlId,
            hashMapOf("isRecTab" to if (urlId == 0) "true" else "false")
        )
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            mPresenter?.getTabChildRequest(urlId, map)
        }
    }
}