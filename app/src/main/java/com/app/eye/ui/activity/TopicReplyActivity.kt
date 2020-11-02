package com.app.eye.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseMvpActivity
import com.app.eye.rx.urlToMap
import com.app.eye.ui.adapter.TopicReplyAdapter
import com.app.eye.ui.mvp.contract.TopicDetailContract
import com.app.eye.ui.mvp.model.entity.ReplyVideoEntity
import com.app.eye.ui.mvp.model.entity.TopicDetailEntity
import com.app.eye.ui.mvp.presenter.TopicDetailPresenter
import com.blankj.utilcode.util.ActivityUtils
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.activity_topic_reply.*

class TopicReplyActivity :
    BaseMvpActivity<TopicDetailContract.Presenter, TopicDetailContract.View>(),
    TopicDetailContract.View, View.OnClickListener, OnLoadMoreListener,
    SwipeRefreshLayout.OnRefreshListener {

    companion object {
        fun startActivity(
            type: Int = 0,
            videoId: Int = -1,
            replyId: Long = -1,
            replyType: String
        ) {
            val bundle = Bundle().apply {
                putInt("type", type) //0 查看hot 1 查看对话
                putInt("videoId", videoId)
                putLong("replyId", replyId)
                putString("replyType", replyType)
            }
            ActivityUtils.startActivity(
                bundle, TopicReplyActivity::class.java,
                R.anim.in_from_bottom, R.anim.top_slient
            )
        }
    }

    private var type = 0
    private var videoId = -1
    private var replyId = -1L
    private var replyType = ""

    private var nextPageUrl: String? = ""

    private val topicDetailAdapter = TopicReplyAdapter(mutableListOf())

    private var isRefresh: Boolean = true

    override fun getLayoutRes(): Int = R.layout.activity_topic_reply

    override fun initView() {
        immersionBar.fitsSystemWindows(true)
            .statusBarDarkFont(false)
            .statusBarColor(R.color.black)
            .init()
        type = intent?.extras!!.getInt("type", 0)
        videoId = intent?.extras!!.getInt("videoId", -1)
        replyId = intent?.extras!!.getLong("replyId", -1)
        replyType = intent?.extras!!.getString("replyId", "")
        initSwipeRefreshLayout(refresh_layout)
        refresh_layout.setOnRefreshListener(this)
        tv_title.text = if (type == 0) "查看热门评论" else "查看全部对话"
        iv_arrow.setOnClickListener(this)
        initAdapter()
    }

    private fun initAdapter() {
        recycler.layoutManager = LinearLayoutManager(mContext)
        topicDetailAdapter.loadMoreModule.setOnLoadMoreListener(this)
        topicDetailAdapter.loadMoreModule.isEnableLoadMore = false
        recycler.adapter = topicDetailAdapter
    }

    override fun initData() {
        recycler.post {
            when (type) {
                0 -> {
                    mPresenter?.getReplyHotRequest(
                        hashMapOf(
                            "videoId" to videoId.toString(),
                            "type" to replyType
                        )
                    )
                }
                1 -> {
                    mPresenter?.getReplyConversationRequest(hashMapOf("replyId" to replyId.toString(),
                        "type" to replyType))
                }
            }

        }

    }

    override fun reConnect() {
    }

    override fun createPresenter(): TopicDetailContract.Presenter? = TopicDetailPresenter()

    override fun setTopicDetailResponse(entity: TopicDetailEntity?) {
    }

    override fun setReplyVideoResponse(entity: ReplyVideoEntity?) {
    }

    override fun setReplyConversationResponse(entity: ReplyVideoEntity?) {
        entity ?: status_view.showEmptyView()
        nextPageUrl = entity?.nextPageUrl
        if (isRefresh) {
            topicDetailAdapter.loadMoreModule.isEnableLoadMore = true
            topicDetailAdapter.setList(entity?.itemList)
            if (TextUtils.isEmpty(nextPageUrl)) topicDetailAdapter.loadMoreModule.loadMoreEnd()
        } else {
            topicDetailAdapter.loadMoreModule.isEnableLoadMore = true
            topicDetailAdapter.addData(entity?.itemList!!)
            if (TextUtils.isEmpty(nextPageUrl)) {
                topicDetailAdapter.loadMoreModule.loadMoreEnd()
            } else {
                topicDetailAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        refresh_layout.isEnabled = true
    }

    override fun setReplyHotResponse(entity: ReplyVideoEntity?) {
        entity ?: status_view.showEmptyView()
        nextPageUrl = entity?.nextPageUrl
        if (isRefresh) {
            topicDetailAdapter.loadMoreModule.isEnableLoadMore = true
            topicDetailAdapter.setList(entity?.itemList)
            if (TextUtils.isEmpty(nextPageUrl)) topicDetailAdapter.loadMoreModule.loadMoreEnd()
        } else {
            topicDetailAdapter.loadMoreModule.isEnableLoadMore = true
            topicDetailAdapter.addData(entity?.itemList!!)
            if (TextUtils.isEmpty(nextPageUrl)) {
                topicDetailAdapter.loadMoreModule.loadMoreEnd()
            } else {
                topicDetailAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        refresh_layout.isEnabled = true
    }

    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        overridePendingTransition(R.anim.top_slient, R.anim.out_from_bottom)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_arrow -> {
                onBackPressedSupport()
            }
        }
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            refresh_layout.isEnabled = false
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            when (type) {
                0 -> {
                    mPresenter?.getReplyHotRequest(map)
                }
                1 -> {
                    mPresenter?.getReplyConversationRequest(map)
                }
            }
        }
    }

    override fun onRefresh() {
        topicDetailAdapter.loadMoreModule.isEnableLoadMore = false
        isRefresh = true
        when (type) {
            0 -> {
                mPresenter?.getReplyHotRequest(
                    hashMapOf(
                        "videoId" to videoId.toString(),
                        "type" to replyType
                    )
                )
            }
            1 -> {
                mPresenter?.getReplyConversationRequest(hashMapOf("replyId" to replyId.toString(),
                    "type" to replyType))
            }
        }
    }

    override fun hideLoading() {
        status_view.showContentView()
        refresh_layout.isRefreshing = false
    }
}