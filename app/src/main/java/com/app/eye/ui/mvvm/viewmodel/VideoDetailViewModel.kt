package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.rx.isSuccess
import com.app.eye.ui.entity.ReplyVideoEntity
import com.app.eye.ui.entity.VideoDetailHeaderEntity
import com.app.eye.ui.entity.VideoEntity
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.lang.Exception

class VideoDetailViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var videoEntityLiveData = MutableLiveData<EyeResult<VideoEntity>>()
    var replyVideoEntityLiveData = MutableLiveData<EyeResult<ReplyVideoEntity>>()

    var statusLiveData = MutableLiveData<Int>()

    fun onGetVideoDetail(id: String, map: Map<String, String>) {
        statusLiveData.value = STATUS_LOADING
        launchOnUi {
            val request = getRequest(id, map)
            videoEntityLiveData.value = request
            if (request.isSuccess()) {
                statusLiveData.value = STATUS_CONTENT
            } else {
                statusLiveData.value = STATUS_ERROR
            }

        }
    }

    private suspend fun getRequest(id: String, map: Map<String, String>) =
        withContext(Dispatchers.IO) {
            coroutineScope {
                val videoIndexDeferred = async { serviceHelper.getVideoIndex(id) }
                val videoRelatedDeferred = async { serviceHelper.getVideoRelated(id) }
                val replyVideoDeferred = async { serviceHelper.getReplyVideoRequest(map) }
                val videoIndex = videoIndexDeferred.await()
                val videoRelated = videoRelatedDeferred.await()
                val replyVideo = replyVideoDeferred.await()
                if (videoIndex.isSuccess() && videoIndex.isSuccess() && replyVideo.isSuccess()) {
                    val videoDetailHeaderEntity = VideoDetailHeaderEntity(
                        (videoIndex as EyeResult.Success).data,
                        (videoRelated as EyeResult.Success).data
                    )
                    val videoEntity =
                        VideoEntity(videoDetailHeaderEntity, (replyVideo as EyeResult.Success).data)
                    EyeResult.Success(videoEntity)
                } else {
                    EyeResult.Error(Exception())
                }
            }
        }

    fun onLoadMore(map: Map<String, String>) {
        launchOnUi {
            val result = serviceHelper.getReplyVideoRequest(map)
            replyVideoEntityLiveData.value = result
        }
    }
}