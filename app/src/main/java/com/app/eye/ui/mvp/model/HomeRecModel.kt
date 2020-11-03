package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.HomeRecContract
import com.app.eye.ui.mvp.model.entity.HomeRecEntity
import io.reactivex.rxjava3.core.Observable

class HomeRecModel : HomeRecContract.Model {

    override fun getRecData(map: Map<String, String>): Observable<HomeRecEntity> {
        return RetrofitManager.service.getHomeRec(map)
    }
}