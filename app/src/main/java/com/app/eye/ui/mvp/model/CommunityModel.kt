package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.CommunityContract
import com.app.eye.ui.mvp.model.entity.ComRecEntity
import io.reactivex.rxjava3.core.Observable

class CommunityModel : CommunityContract.Model {
    override fun getRecRequest(map: Map<String, String>): Observable<ComRecEntity> {
        return RetrofitManager.service.getCommunityRec(map)
    }
}