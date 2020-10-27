package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.TopicContact
import com.app.eye.ui.mvp.model.entity.TabChildEntity
import com.app.eye.ui.mvp.model.entity.TagTabEntity
import com.app.eye.ui.mvp.model.entity.TopicListEntity
import io.reactivex.rxjava3.core.Observable

class TopicModel : TopicContact.Model {
    override fun getTabRequest(): Observable<TagTabEntity> =
        RetrofitManager.service.getTabTagList()

    override fun getTabChildRequest(id: Int, map: Map<String, String>): Observable<TabChildEntity> =
        RetrofitManager.service.getTabChild(id, map)

    override fun getTopicListRequest(map: Map<String, String>): Observable<TopicListEntity> =
        RetrofitManager.service.getTopicList(map)

}