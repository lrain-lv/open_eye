package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.ReplyVideoEntity
import com.app.eye.ui.mvp.model.entity.TopicDetailEntity
import io.reactivex.rxjava3.core.Observable

interface TopicDetailContract {
    interface View : IBaseView {
        fun setTopicDetailResponse(entity: TopicDetailEntity?)
        fun setReplyVideoResponse(entity: ReplyVideoEntity?)
    }

    interface Model : IModel {
        fun getTopicDetailRequest(id: String): Observable<TopicDetailEntity>
        fun getReplyVideoRequest(map: Map<String,String>): Observable<ReplyVideoEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getTopicDetailRequest(id: String)
        fun getReplyVideoRequest(map: Map<String,String>)
    }
}