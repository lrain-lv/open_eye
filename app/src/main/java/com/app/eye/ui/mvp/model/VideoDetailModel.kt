package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.VideoDetailContract
import com.app.eye.ui.mvp.model.entity.ReplyVideoEntity
import com.app.eye.ui.mvp.model.entity.VideoDetailHeaderEntity
import com.app.eye.ui.mvp.model.entity.VideoIndexEntity
import com.app.eye.ui.mvp.model.entity.VideoRelatedEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction

class VideoDetailModel : VideoDetailContract.Model {
    override fun getVideoDetailRequest(id: String): Observable<VideoDetailHeaderEntity> {
        return Observable.zip(RetrofitManager.service.getVideoIndex(id),
            RetrofitManager.service.getVideoRelated(id),
            BiFunction<VideoIndexEntity, VideoRelatedEntity, VideoDetailHeaderEntity> { t1, t2 ->
                VideoDetailHeaderEntity(t1!!,
                    t2!!)
            })
    }

    override fun getReplyVideoRequest(map: Map<String, String>): Observable<ReplyVideoEntity> =
        RetrofitManager.service.getReplyVideoRequest(map)

    override fun getReplyConversationRequest(map: Map<String, String>): Observable<ReplyVideoEntity> =
        RetrofitManager.service.getReplyConversation(map)

    override fun getReplyHotRequest(map: Map<String, String>): Observable<ReplyVideoEntity> =
        RetrofitManager.service.getReplyHot(map)
}