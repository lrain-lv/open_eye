//package com.app.eye.ui.mvvm.viewmodel
//
//import androidx.lifecycle.MutableLiveData
//import com.app.eye.base.mvvm.BaseViewModel
//import com.app.eye.http.mvvm.EyeResult
//import com.app.eye.http.mvvm.ServiceHelper
//import com.app.eye.ui.mvp.model.entity.*
//import com.app.eye.widgets.*
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.async
//import kotlinx.coroutines.coroutineScope
//import kotlinx.coroutines.withContext
//
//class TopicDetailViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
//    var topicDetailLiveData = MutableLiveData<EyeResult<TopicDetailEntity>>()
//    var replyVideoLiveData = MutableLiveData<EyeResult<ReplyVideoEntity>>()
//
//    var statusLiveData = MutableLiveData<Int>()
//
//
//    fun onRefresh(id: String, type: String) {
//        statusLiveData.value = STATUS_LOADING
//        launchOnUi {
//            val result = getRequest(id)
//            if (result is EyeResult.Error) {
//                statusLiveData.value = STATUS_ERROR
//            } else {
//                statusLiveData.value = STATUS_CONTENT
//            }
//
//        }
//    }
//
//    suspend fun getRequest(id: String) : EyeResult<TopicEntity> {
//        withContext(Dispatchers.IO) {
//            coroutineScope {
//                val topicDetail = async { serviceHelper.getTopicDetailRequest(id) }
//                val reply = async {
//                    serviceHelper.getReplyVideoRequest(
//                        hashMapOf(
//                            "videoId" to id,
//                            "type" to "topic"
//                        )
//                    )
//                }
//                val topicDetailResult = topicDetail.await()
//                val replyResult = reply.await()
//                if (topicDetailResult is EyeResult.Success && replyResult is EyeResult.Success) {
//                    val topicEntity =
//                        TopicEntity(
//                            topicDetailEntity = topicDetailResult.data,
//                            replyVideoEntity = replyResult.data
//                        )
//                    topicEntity
//                }
//            }
//        }
//    }
//
//    fun onLoadMore(map: Map<String, String>, ) {
//        launchOnUi {
//            val result = serviceHelper.getPushMessage(map)
//            entityLiveData.value = result
//        }
//    }
//}