package com.app.eye.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMActivity
import com.app.eye.rx.checkSuccess
import com.app.eye.rx.loadImageCommon
import com.app.eye.rx.urlToMap
import com.app.eye.ui.adapter.TopicDetailAdapter
import com.app.eye.ui.mvp.contract.TopicDetailContract
import com.app.eye.ui.entity.ReplyVideoEntity
import com.app.eye.ui.entity.TopicDetailEntity
import com.app.eye.ui.mvp.presenter.TopicDetailPresenter
import com.app.eye.ui.mvvm.factory.InjectorUtil
import com.app.eye.ui.mvvm.viewmodel.TopicDetailViewModel
import com.app.eye.widgets.*
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.activity_topic_detail.*
import kotlinx.android.synthetic.main.activity_topic_detail.recycler_view
import kotlinx.android.synthetic.main.activity_topic_detail.status_view

class TopicDetailActivity : BaseVMActivity(), OnLoadMoreListener, View.OnClickListener {

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

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getTopicDetailVMFactory()
        ).get(TopicDetailViewModel::class.java)
    }

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
                        1, replyId = item.data.id, replyType = "topic"
                    )
                }
                R.id.tv_right_text -> {
                    TopicReplyActivity.startActivity(
                        0, videoId = id.toInt(), replyType = "topic"
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
            viewModel.onRefresh(id)
        }

    }

    override fun startObserver() {
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

        viewModel.topicEntityLiveData.observe(this, Observer {
            it.checkSuccess({ entity ->
                val replyVideoEntity = entity.replyVideoEntity
                val topicDetailEntity = entity.topicDetailEntity
                initTopicDetail(topicDetailEntity)
                initReplyVideo(replyVideoEntity)
            })
        })

        viewModel.replyVideoLiveData.observe(this, Observer {
            it.checkSuccess({ it ->
                initReplyVideo(it)
            })
        })
    }

    override fun reConnect() {
    }


    private fun initTopicDetail(entity: TopicDetailEntity) {
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

        ivBg.loadImageCommon(mContext, entity.headPicture)
        topicDetailAdapter.addHeaderView(headerView)
    }

    private fun initReplyVideo(entity: ReplyVideoEntity?) {
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

    override fun onLoadMore() {
        if (!nextPageUrl.isNullOrEmpty()) {
            isRefresh = false
            val map = nextPageUrl!!.urlToMap()
            viewModel.onLoadMore(map)
        }
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