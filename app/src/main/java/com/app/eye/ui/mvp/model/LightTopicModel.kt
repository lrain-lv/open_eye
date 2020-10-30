package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.LightTopicContract
import com.app.eye.ui.mvp.model.entity.LightTopicInternalEntity
import io.reactivex.rxjava3.core.Observable

class LightTopicModel : LightTopicContract.Model {
    override fun getLightTopicRequest(id: Int): Observable<LightTopicInternalEntity> =
        RetrofitManager.service.getLightTopicInternal(id)


}