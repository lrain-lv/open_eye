package com.app.eye.ui.activity

import android.os.Bundle
import android.text.TextUtils
import com.app.eye.R
import com.app.eye.base.mvvm.BaseDataBindActivity
import com.app.eye.databinding.ActivityTopicReplyBinding
import com.app.eye.rx.checkSuccess
import com.app.eye.ui.adapter.TopicReplyAdapter
import com.app.eye.ui.mvvm.viewmodel.TopicReplyViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopicReplyActivity :
    BaseDataBindActivity(), OnLoadMoreListener {

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

    private val topicDetailAdapter = TopicReplyAdapter(mutableListOf())


    private val viewModel by viewModel<TopicReplyViewModel>()

    private val binding by binding<ActivityTopicReplyBinding>(R.layout.activity_topic_reply)

    override fun initView() {
        immersionBar.fitsSystemWindows(true)
            .statusBarDarkFont(false)
            .statusBarColor(R.color.black)
            .init()
        intent?.extras?.apply {
            type = getInt("type", 0)
            videoId = getInt("videoId", -1)
            replyId = getLong("replyId", -1)
            replyType = getString("replyType", "")
        }
        binding.apply {
            lifecycleOwner = this@TopicReplyActivity
            model = viewModel
            vId = videoId.toString()
            rId = replyId.toString()
            rType = replyType
            pType = type
        }
        initAdapter()
    }

    private fun initAdapter() {
        topicDetailAdapter.loadMoreModule.setOnLoadMoreListener(this)
        binding.adapter = topicDetailAdapter
    }

    override fun initData() {
        binding.recycler.post {
            viewModel.getReplyRequest(
                type,
                videoId = videoId.toString(),
                replyType = replyType,
                replyId = replyId.toString(),
                isFirst = true
            )
        }
    }

    override fun startObserver() {
        viewModel.apply {
            replyVideoLiveData.observe(this@TopicReplyActivity, {
                it.checkSuccess(
                    { entity ->
                        if (viewModel.isRefresh) {
                            topicDetailAdapter.loadMoreModule.isEnableLoadMore = true
                            topicDetailAdapter.setList(entity.itemList)
                            if (TextUtils.isEmpty(entity.nextPageUrl)) topicDetailAdapter.loadMoreModule.loadMoreEnd()
                        } else {
                            topicDetailAdapter.loadMoreModule.isEnableLoadMore = true
                            topicDetailAdapter.addData(entity.itemList)
                            if (TextUtils.isEmpty(entity.nextPageUrl)) {
                                topicDetailAdapter.loadMoreModule.loadMoreEnd()
                            } else {
                                topicDetailAdapter.loadMoreModule.loadMoreComplete()
                            }
                        }
                    }
                )
            })
            enableLoadMore.observe(this@TopicReplyActivity, {
                topicDetailAdapter.loadMoreModule.isEnableLoadMore = it
            })
        }
    }

    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        overridePendingTransition(
            R.anim.top_slient,
            R.anim.out_from_bottom
        )
    }

    override fun onLoadMore() {
        viewModel.getReplyRequestMore(type)
    }

}