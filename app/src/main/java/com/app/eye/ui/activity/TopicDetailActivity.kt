package com.app.eye.ui.activity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.eye.R
import com.app.eye.base.BaseMvpActivity
import com.app.eye.rx.urlToMap
import com.app.eye.ui.adapter.TopicDetailAdapter
import com.app.eye.ui.mvp.contract.TopicDetailContract
import com.app.eye.ui.mvp.model.entity.ReplyVideoEntity
import com.app.eye.ui.mvp.model.entity.TopicDetailEntity
import com.app.eye.ui.mvp.presenter.TopicDetailPresenter
import com.app.eye.widgets.BottomDialog
import com.app.eye.widgets.EyeCommentDialog
import com.app.eye.widgets.KeyboardChangeListener
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_topic_detail.*

class TopicDetailActivity :
    BaseMvpActivity<TopicDetailContract.Presenter, TopicDetailContract.View>(),
    TopicDetailContract.View, OnLoadMoreListener, View.OnClickListener {

    companion object {
        fun startActivity(id: String) {
            val bundle = Bundle().apply {
                putString("id", id)
            }
            ActivityUtils.startActivity(bundle, TopicDetailActivity::class.java)
        }
    }

    private lateinit var id: String
    private lateinit var headerView: View
    private var mDistance = 0
    private val barHeight = SizeUtils.dp2px(48f).toFloat()
    private var nextPageUrl: String? = ""
    private val topicDetailAdapter = TopicDetailAdapter(mutableListOf())

    override fun getLayoutRes(): Int = R.layout.activity_topic_detail
    private var isRefresh: Boolean = true
    override fun initView() {
        id = intent.extras?.getString("id", "")!!
        if (id.isEmpty()) onBackPressedSupport()
        immersionBar.fitsSystemWindows(true)
            .statusBarColor(R.color.black).init()
        iv_back.setOnClickListener { onBackPressedSupport() }
        initHeaderView()
        iv_publish.setOnClickListener(this)
        topicDetailAdapter.loadMoreModule.setOnLoadMoreListener(this)
        topicDetailAdapter.loadMoreModule.isEnableLoadMore = false
        recycler_view.adapter = topicDetailAdapter
        topicDetailAdapter.addChildClickViewIds(
            R.id.tv_get_converse,
            R.id.tv_reply,
            R.id.tv_right_text
        )
        topicDetailAdapter.setOnItemChildClickListener { _, view, position ->
            val item = topicDetailAdapter.getItem(position)
            when (view.id) {
                R.id.tv_reply -> {
                    EyeCommentDialog.createCommentDialog(item.data.user.nickname)
                        .showDialog(supportFragmentManager)
                }
                R.id.tv_get_converse -> {
                    TopicReplyActivity.startActivity(
                        1, replyId = item.data.id
                    )
                }
                R.id.tv_right_text -> {
                    TopicReplyActivity.startActivity(
                        0, videoId = id.toInt()
                    )
                }
            }
        }
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                mDistance += dy
                val percent: Float = mDistance.toFloat() / barHeight //百分比
                if (percent > 1 && (tv_title_dd.alpha == 1.toFloat() && view_bg.alpha == 1.toFloat())) return
                iv_back.setImageResource(if (percent > 0.5) R.mipmap.icon_back_black else R.mipmap.icon_back_white)
                iv_share.setImageResource(if (percent > 0.5) R.mipmap.icon_share_grey else R.mipmap.icon_share_white)
                view_bg.alpha = if (percent > 1) 1.toFloat() else percent
                tv_title_dd.alpha = if (percent > 1) 1.toFloat() else percent
            }
        })
    }

    private fun initHeaderView() {
        headerView =
            layoutInflater.inflate(R.layout.layout_topic_detail_header, recycler_view, false)
    }

    override fun initData() {
        recycler_view.post {
            mPresenter?.getTopicDetailRequest(id)
            mPresenter?.getReplyVideoRequest(hashMapOf("videoId" to id, "type" to "topic"))
        }

    }

    override fun reConnect() {
    }

    override fun createPresenter(): TopicDetailContract.Presenter? = TopicDetailPresenter()

    override fun setTopicDetailResponse(entity: TopicDetailEntity?) {
        headerView.findViewById<TextView>(R.id.tv_title_header).text = entity?.title
        headerView.findViewById<TextView>(R.id.tv_count).text =
            "${entity?.viewCount}人浏览 | ${entity?.joinCount}人讨论"
        headerView.findViewById<TextView>(R.id.tv_content_header).text = entity?.content
        val layout = headerView.findViewById<LinearLayout>(R.id.layout_container)
        entity?.tagList?.forEach {
            val tv = TextView(mContext)
            tv.text = "#${it.name}"
            tv.setTextColor(Color.WHITE)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(SizeUtils.dp2px(15f), 0, 0, 0)
            tv.layoutParams = params
            layout.addView(tv)
        }
        val ivBg = headerView.findViewById<ImageView>(R.id.iv_bg)
        Glide.with(mContext)
            .load(entity?.headPicture)
            .into(ivBg)
        topicDetailAdapter.addHeaderView(headerView)
    }

    override fun setReplyVideoResponse(entity: ReplyVideoEntity?) {
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
    }

    override fun setReplyConversationResponse(entity: ReplyVideoEntity?) {
    }

    override fun setReplyHotResponse(entity: ReplyVideoEntity?) {
    }

    override fun hideLoading() {
        status_view.showContentView()
    }

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            mPresenter?.getReplyVideoRequest(map)
        }
    }

    override fun onDestroy() {
        KeyboardUtils.fixAndroidBug5497(this)
        KeyboardUtils.fixSoftInputLeaks(this)
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_publish -> {
                EyeCommentDialog.createCommentDialog()
                    .showDialog(supportFragmentManager)
            }
        }
    }
}