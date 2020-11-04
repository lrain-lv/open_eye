package com.app.eye.ui.mvp.model

import com.app.eye.ui.mvp.model.entity.TagVideoEntity
import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.TagVideoContract
import com.app.eye.ui.mvp.model.entity.TagIndexEntity
import io.reactivex.rxjava3.core.Observable

class TagVideoModel : TagVideoContract.Model {
    override fun getTagIndexRequest(id: String): Observable<TagIndexEntity> =
        RetrofitManager.service.getTagIndex(id)

    override fun getTagVideoRequest(map: Map<String, String>): Observable<TagVideoEntity> =
        RetrofitManager.service.getTagVideo(map)

    override fun getTagDynamicRequest(map: Map<String, String>): Observable<TagVideoEntity> =
        RetrofitManager.service.getTagDynamic(map)
}