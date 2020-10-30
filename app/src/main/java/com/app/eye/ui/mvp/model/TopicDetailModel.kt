package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.TopicDetailContract
import com.app.eye.ui.mvp.model.entity.ReplyVideoEntity
import com.app.eye.ui.mvp.model.entity.TopicDetailEntity
import io.reactivex.rxjava3.core.Observable

class TopicDetailModel : TopicDetailContract.Model {
    override fun getTopicDetailRequest(id: String): Observable<TopicDetailEntity> =
        RetrofitManager.service.getTopicDetailRequest(id)

    override fun getReplyVideoRequest(map: Map<String,String>): Observable<ReplyVideoEntity> =
        RetrofitManager.service.getReplyVideoRequest(map)

}