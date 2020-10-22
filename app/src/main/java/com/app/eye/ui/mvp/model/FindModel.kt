package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.FindContract
import com.app.eye.ui.mvp.model.entity.DiscoverEntity
import io.reactivex.rxjava3.core.Observable

class FindModel : FindContract.Model {
    override fun getDiscoveryData(): Observable<DiscoverEntity> {
        return RetrofitManager.service.getDiscovery()
    }

}