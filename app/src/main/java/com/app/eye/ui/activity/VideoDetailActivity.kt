package com.app.eye.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.eye.R
import com.app.eye.base.mvvm.BaseVMActivity
import com.app.eye.rx.*
import com.app.eye.ui.adapter.TopicDetailAdapter
import com.app.eye.ui.adapter.VideoDetailHeaderAdapter
import com.app.eye.ui.entity.ReplyVideoEntity
import com.app.eye.ui.entity.VideoDetailHeaderEntity
import com.app.eye.ui.entity.VrItem
import com.app.eye.ui.mvvm.viewmodel.TopicViewModel
import com.app.eye.ui.mvvm.viewmodel.VideoDetailViewModel
import com.app.eye.widgets.*
import com.app.eye.widgets.videoplayer.Jzvd
import com.app.eye.widgets.videoplayer.Jzvd.SCREEN_NORMAL
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.gyf.immersionbar.BarHide
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.activity_video_detail.recycler_view
import kotlinx.android.synthetic.main.activity_video_detail.status_view
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoDetailActivity : BaseVMActivity(), OnLoadMoreListener {

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

    private val spUtils by lazy { SPUtils.getInstance("eye") }

    private val viewModel by viewModel<VideoDetailViewModel>()

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
        val extras = intent.extras!!
        id = extras.getString("id", "")!!
        if (id.isEmpty()) onBackPressedSupport()
        iv_header.load(spUtils.getString("avatar", "")) {
            size(30f.dp2px().toInt())
            error(R.mipmap.pgc_default_avatar)
        }
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
            viewModel.onGetVideoDetail(id, hashMapOf("videoId" to id, "type" to "video"))
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
        viewModel.replyVideoEntityLiveData.observe(this, Observer {
            it.checkSuccess({ entity ->

            })
        })
        viewModel.videoEntityLiveData.observe(this, Observer {
            it.checkSuccess({ entity ->
                val videoDetailHeaderEntity = entity.videoDetailHeaderEntity
                val replyVideoEntity = entity.replyVideoEntity
                setVideoDetailResponse(videoDetailHeaderEntity)
                setReplyVideoResponse(replyVideoEntity)
            })
        })
    }


    private fun initHeaderView() {
        headerView =
            layoutInflater.inflate(R.layout.layout_video_detail_header, recycler_view, false)
        headerView.apply {
            tvTitle = findViewById<TextView>(R.id.tv_header_title)
            tvDec = findViewById<TextView>(R.id.tv_header_dec)
            tvTag = findViewById<TextView>(R.id.tv_header_tag)
            tvcount = findViewById<TextView>(R.id.tv_like_count)
            tare = findViewById<TextView>(R.id.tv_share_count)
            icon = findViewById<ImageView>(R.id.iv_header_icon)
            tautDec = findViewById<TextView>(R.id.tv_auth_dec)
            tvAuthTitle = findViewById<TextView>(R.id.tv_auth_title)
            recHeader = findViewById<RecyclerView>(R.id.recycler_video_header)
            findViewById<TextView>(R.id.tv_more).setOnClickListener {
                if (!videoDetailHeaderAdapter.isShow) {
                    videoDetailHeaderAdapter.setList(totalList)
                    it.visibility = View.GONE
                }
            }
        }

    }

    private fun setVideoDetailResponse(entity: VideoDetailHeaderEntity) {
        val videoIndexEntity = entity.videoIndexEntity
        val videoRelatedEntity = entity.videoRelatedEntity
        videoIndexEntity.apply {
            iv_blur.loadImageCommon(this.cover.blurred)
            tvTitle.text = this.title
            tvDec.text = this.description
            tvTag.text =
                "#${this.category} / 开眼精选"
            tvcount.text =
                this.consumption.collectionCount.toString()
            tare.text =
                this.consumption.shareCount.toString()
            icon.loadImageCircle(this.author.icon, 35f)
            tautDec.text =
                this.author.description
            tvAuthTitle.text = this.author.name
            jzvd.setUp(this.playUrl, "", SCREEN_NORMAL)
            jzvd.posterImageView.loadImageCommon(this.cover.feed)
        }
        totalList.addAll(videoRelatedEntity.itemList.filter {
            TextUtils.equals("videoSmallCard", it.type)
        })
        showList.addAll(totalList.take(3))
        recHeader.apply {
            setHasFixedSize(true)
            layoutManager = NoScrollLinearLayoutManager(this@VideoDetailActivity)
            adapter =
                videoDetailHeaderAdapter
        }
        videoDetailHeaderAdapter.setList(showList)
    }

    private fun setReplyVideoResponse(entity: ReplyVideoEntity) {
        nextPageUrl = entity.nextPageUrl
        if (isRefresh) {
            topicDetailAdapter.loadMoreModule.isEnableLoadMore = true
            topicDetailAdapter.setList(entity.itemList)
            topicDetailAdapter.addHeaderView(headerView)
            if (TextUtils.isEmpty(nextPageUrl)) topicDetailAdapter.loadMoreModule.loadMoreEnd()
        } else {
            topicDetailAdapter.loadMoreModule.isEnableLoadMore = true
            topicDetailAdapter.addData(entity.itemList)
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


}