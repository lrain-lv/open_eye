package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.PushContract
import com.app.eye.ui.mvp.model.entity.PushEntity
import io.reactivex.rxjava3.core.Observable

class PushModel : PushContract.Model {
    override fun getPushRequest(map: Map<String, String>): Observable<PushEntity> {
        return RetrofitManager.service.getPushMessage(map)
    }
}