package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.ReplyVideoEntity
import com.app.eye.ui.mvp.model.entity.VideoDetailHeaderEntity
import io.reactivex.rxjava3.core.Observable

interface VideoDetailContract {
    interface View : IBaseView {
        fun setVideoDetailResponse(entity: VideoDetailHeaderEntity)
        fun setReplyVideoResponse(entity: ReplyVideoEntity?)
        fun setReplyConversationResponse(entity: ReplyVideoEntity?)
        fun setReplyHotResponse(entity: ReplyVideoEntity?)
    }

    interface Model : IModel {
        fun getVideoDetailRequest(id: String): Observable<VideoDetailHeaderEntity>
        fun getReplyVideoRequest(map: Map<String, String>): Observable<ReplyVideoEntity>
        fun getReplyConversationRequest(map: Map<String, String>): Observable<ReplyVideoEntity>
        fun getReplyHotRequest(map: Map<String, String>): Observable<ReplyVideoEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getVideoDetailRequest(id: String)
        fun getReplyVideoRequest(map: Map<String, String>)
        fun getReplyConversationRequest(map: Map<String, String>)
        fun getReplyHotRequest(map: Map<String, String>)
    }
}