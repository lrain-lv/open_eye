package com.app.eye.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.eye.R
import com.app.eye.base.BaseMvpActivity
import com.app.eye.rx.setOnClickListener
import com.app.eye.rx.urlToMap
import com.app.eye.ui.adapter.TopicDetailAdapter
import com.app.eye.ui.adapter.VideoDetailHeaderAdapter
import com.app.eye.ui.mvp.contract.VideoDetailContract
import com.app.eye.ui.mvp.model.entity.ReplyVideoEntity
import com.app.eye.ui.mvp.model.entity.VideoDetailHeaderEntity
import com.app.eye.ui.mvp.model.entity.VrItem
import com.app.eye.ui.mvp.presenter.VideoDetailPresenter
import com.app.eye.widgets.EyeCommentDialog
import com.app.eye.widgets.NoScrollLinearLayoutManager
import com.app.eye.widgets.videoplayer.Jzvd
import com.app.eye.widgets.videoplayer.Jzvd.SCREEN_NORMAL
import com.app.eye.widgets.videoplayer.JzvdStd
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.gyf.immersionbar.BarHide
import kotlinx.android.synthetic.main.activity_video_detail.*

class VideoDetailActivity :
    BaseMvpActivity<VideoDetailContract.Presenter, VideoDetailContract.View>(),
    VideoDetailContract.View, OnLoadMoreListener {

    companion object {
        fun startActivity(id: String) {
            val bundle = Bundle().apply {
                putString("id", id)
            }
            ActivityUtils.startActivity(
                bundle,
                VideoDetailActivity::class.java,
                R.anim.in_from_bottom,
                R.anim.top_slient
            )
        }
    }

    private lateinit var id: String
    private var nextPageUrl: String? = ""
    private val topicDetailAdapter = TopicDetailAdapter(mutableListOf())
    private var isRefresh: Boolean = true
    private val videoDetailHeaderAdapter = VideoDetailHeaderAdapter(mutableListOf())

    private val totalList = mutableListOf<VrItem>()
    private val showList = mutableListOf<VrItem>()

    private lateinit var headerView: View
    private lateinit var tvTitle: TextView
    private lateinit var tvDec: TextView
    private lateinit var tvTag: TextView
    private lateinit var tvcount: TextView
    private lateinit var tare: TextView
    private lateinit var tautDec: TextView
    private lateinit var tvAuthTitle: TextView
    private lateinit var icon: ImageView
    private lateinit var recHeader: RecyclerView

    override fun getLayoutRes(): Int = R.layout.activity_video_detail

    override fun initView() {
        immersionBar.hideBar(BarHide.FLAG_HIDE_BAR).init()
        id = intent.extras?.getString("id", "")!!
        if (id.isEmpty()) onBackPressedSupport()
        Glide.with(this)
            .load(SPUtils.getInstance("eye").getString("avatar", ""))
            .error(R.mipmap.pgc_default_avatar)
            .centerCrop()
            .override(SizeUtils.dp2px(30f), SizeUtils.dp2px(30f))
            .into(iv_header)
        setOnClickListener(tv_count, iv_comment, tv_comment) {
            when (this.id) {
                R.id.tv_comment -> {
                    EyeCommentDialog.createCommentDialog()
                        .showDialog(supportFragmentManager)
                }
                R.id.iv_comment, R.id.tv_count -> {
                    recycler_view.smoothScrollToPosition(2)
                }
            }
        }
        initHeaderView()
        topicDetailAdapter.loadMoreModule.setOnLoadMoreListener(this)
        topicDetailAdapter.loadMoreModule.isEnableLoadMore = false
        recycler_view.adapter = topicDetailAdapter
        topicDetailAdapter.addChildClickViewIds(
            R.id.tv_get_converse,
            R.id.tv_reply,
            R.id.tv_right_text,
            R.id.tv_more
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
                        1, replyId = item.data.id, replyType = "video"
                    )
                }
                R.id.tv_right_text -> {
                    TopicReplyActivity.startActivity(
                        0, videoId = id.toInt(), replyType = "video"
                    )
                }
                R.id.tv_more -> {
                    videoDetailHeaderAdapter.setList(totalList)
                    headerView.findViewById<TextView>(R.id.tv_more).visibility = View.GONE
                    videoDetailHeaderAdapter.isShow = true
                }
            }
        }
    }

    override fun initData() {
        recycler_view.post {
            mPresenter?.getVideoDetailRequest(id)
            mPresenter?.getReplyVideoRequest(hashMapOf("videoId" to id, "type" to "video"))
        }
    }

    override fun createPresenter(): VideoDetailContract.Presenter? = VideoDetailPresenter()

    private fun initHeaderView() {
        headerView =
            layoutInflater.inflate(R.layout.layout_video_detail_header, recycler_view, false)
        tvTitle = headerView.findViewById<TextView>(R.id.tv_header_title)
        tvDec = headerView.findViewById<TextView>(R.id.tv_header_dec)
        tvTag = headerView.findViewById<TextView>(R.id.tv_header_tag)
        tvcount = headerView.findViewById<TextView>(R.id.tv_like_count)
        tare = headerView.findViewById<TextView>(R.id.tv_share_count)
        icon = headerView.findViewById<ImageView>(R.id.iv_header_icon)
        tautDec = headerView.findViewById<TextView>(R.id.tv_auth_dec)
        tvAuthTitle = headerView.findViewById<TextView>(R.id.tv_auth_title)
        recHeader = headerView.findViewById<RecyclerView>(R.id.recycler_video_header)
        headerView.findViewById<TextView>(R.id.tv_more).setOnClickListener {
            if (!videoDetailHeaderAdapter.isShow) {
                videoDetailHeaderAdapter.setList(totalList)
                it.visibility = View.GONE
            }
        }
    }

    override fun setVideoDetailResponse(entity: VideoDetailHeaderEntity) {
        val videoIndexEntity = entity.videoIndexEntity
        val videoRelatedEntity = entity.videoRelatedEntity
        tvTitle.text = videoIndexEntity.title
        tvDec.text = videoIndexEntity.description
        tvTag.text =
            "#${videoIndexEntity.category} / 开眼精选"
        tvcount.text =
            videoIndexEntity.consumption.collectionCount.toString()
        tare.text =
            videoIndexEntity.consumption.shareCount.toString()
        Glide.with(this)
            .load(videoIndexEntity.author.icon)
            .circleCrop()
            .into(icon)
        tautDec.text =
            videoIndexEntity.author.description
        tvAuthTitle.text = videoIndexEntity.author.name
        totalList.addAll(videoRelatedEntity.itemList.filter {
            TextUtils.equals("videoSmallCard", it.type)
        })
        showList.addAll(totalList.take(3))
        recHeader.layoutManager = NoScrollLinearLayoutManager(this)
        recHeader.adapter =
            videoDetailHeaderAdapter
        videoDetailHeaderAdapter.setList(showList)
        jzvd.setUp(videoIndexEntity.playUrl, "", SCREEN_NORMAL)
        Glide.with(this)
            .load(videoIndexEntity.cover.feed)
            .centerCrop()
            .into(jzvd.posterImageView)
    }

    override fun setReplyVideoResponse(entity: ReplyVideoEntity?) {
        entity ?: status_view.showEmptyView()
        nextPageUrl = entity?.nextPageUrl
        if (isRefresh) {
            topicDetailAdapter.loadMoreModule.isEnableLoadMore = true
            topicDetailAdapter.setList(entity?.itemList)
            topicDetailAdapter.addHeaderView(headerView)
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

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER)
    }

    override fun onBackPressedSupport() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressedSupport()
        overridePendingTransition(R.anim.top_slient, R.anim.out_from_bottom)
    }


    override fun onDestroy() {
        KeyboardUtils.fixAndroidBug5497(this)
        KeyboardUtils.fixSoftInputLeaks(this)
        super.onDestroy()
    }
}