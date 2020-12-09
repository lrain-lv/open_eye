package com.app.eye.ui.mvvm.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.app.eye.R
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.rx.urlToMap
import com.app.eye.ui.activity.TopicReplyActivity
import com.app.eye.ui.entity.*
import com.app.eye.widgets.*
import com.blankj.utilcode.util.ActivityUtils
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.lang.Exception

class TopicReplyViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {

    private val replyLiveData = MutableLiveData<EyeResult<ReplyVideoEntity>>()

    val replyVideoLiveData = replyLiveData

    val enableLoadMore = MutableLiveData<Boolean>(false)

    val enableRefreshLiveData = MutableLiveData<Boolean>(true)

    val statusLiveData = MutableLiveData<Int>()

    val refreshLiveData = MutableLiveData<Boolean>()

    private var nextPageUrl: String? = ""

    var isRefresh = true

    fun getReplyRequest(
        type: Int,
        videoId: String,
        replyType: String,
        replyId: String,
        isFirst: Boolean = false
    ) {
        enableLoadMore.value = false
        isRefresh = true
        if (isFirst) {
            statusLiveData.value = STATUS_LOADING
        } else {
            statusLiveData.value = STATUS_CONTENT
            refreshLiveData.value = true
        }
        val map = if (type == 0) hashMapOf(
            "videoId" to videoId,
            "type" to replyType
        ) else hashMapOf(
            "replyId" to replyId,
            "type" to replyType
        )
        launchOnUi {
            val result = when (type) {
                0 -> {
                    val replyHot = serviceHelper.getReplyHot(map)
                    replyVideoLiveData.value = replyHot
                    replyHot
                }
                else -> {
                    val replyConversation = serviceHelper.getReplyConversation(map)
                    replyVideoLiveData.value = replyConversation
                    replyConversation
                }
            }
            enableLoadMore.value = true
            if (result is EyeResult.Success) {
                nextPageUrl = result.data.nextPageUrl
                statusLiveData.value = STATUS_CONTENT
            } else {
                statusLiveData.value = STATUS_ERROR
            }
            refreshLiveData.value = false
        }
    }

    fun getReplyRequestMore(type: Int) {
        isRefresh = false
        enableRefreshLiveData.value = false
        isRefresh = false
        val map = nextPageUrl!!.urlToMap()
        launchOnUi {
            val result = when (type) {
                0 -> {
                    val replyHot = serviceHelper.getReplyHot(map)
                    replyVideoLiveData.value = replyHot
                    replyHot
                }
                else -> {
                    val replyConversation = serviceHelper.getReplyConversation(map)
                    replyVideoLiveData.value = replyConversation
                    replyConversation
                }
            }
            nextPageUrl = (result as EyeResult.Success).data.nextPageUrl
            replyVideoLiveData.value = result
            enableRefreshLiveData.value = true
        }
    }

    fun click(view: View) {
        ActivityUtils.finishActivity(
            TopicReplyActivity::class.java,
            R.anim.top_slient,
            R.anim.out_from_bottom
        )
    }
}