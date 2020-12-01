package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.entity.*
import com.app.eye.widgets.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.lang.Exception

class TopicDetailViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var replyVideoLiveData = MutableLiveData<EyeResult<ReplyVideoEntity>>()

    var topicEntityLiveData = MutableLiveData<EyeResult<TopicEntity>>()

    var statusLiveData = MutableLiveData<Int>()

    var refreshLiveData = MutableLiveData<Boolean>()


    fun onRefresh(id: String) {
        statusLiveData.value = STATUS_LOADING
        launchOnUi {
            val topicEntity = getRequest(id)
            if (topicEntity is EyeResult.Error) {
                statusLiveData.value = STATUS_ERROR
            } else {
                statusLiveData.value = STATUS_CONTENT
            }
            topicEntityLiveData.value = topicEntity
        }
    }

    private suspend fun getRequest(id: String) =
        withContext(Dispatchers.IO) {
            coroutineScope {
                val topicDetail = async { serviceHelper.getTopicDetailRequest(id) }
                val reply = async {
                    serviceHelper.getReplyVideoRequest(
                        hashMapOf(
                            "videoId" to id,
                            "type" to "topic"
                        )
                    )
                }
                val topicDetailResult = topicDetail.await()
                val replyResult = reply.await()
                if (topicDetailResult is EyeResult.Success && replyResult is EyeResult.Success) {
                    val topicEntity =
                        TopicEntity(
                            topicDetailEntity = topicDetailResult.data,
                            replyVideoEntity = replyResult.data
                        )
                    EyeResult.Success(topicEntity)
                } else {
                    EyeResult.Error(Exception())
                }
            }
        }


    fun onLoadMore(map: Map<String, String>, ) {
        launchOnUi {
            val result = serviceHelper.getReplyVideoRequest(map)
            replyVideoLiveData.value = result
        }
    }


    fun getReplyRequest(type: Int, map: Map<String, String>, isFirst: Boolean = false) {
        if (isFirst) {
            statusLiveData.value = STATUS_LOADING
        } else {
            statusLiveData.value = STATUS_CONTENT
            refreshLiveData.value = true
        }
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
            if (result is EyeResult.Error) {
                statusLiveData.value = STATUS_ERROR
            } else {
                statusLiveData.value = STATUS_CONTENT
            }
            refreshLiveData.value = false
        }

    }

    fun getReplyRequestMore(type: Int, map: Map<String, String>) {
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
            replyVideoLiveData.value = result
        }
    }
}