package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.BrandWallContract
import com.app.eye.ui.mvp.model.entity.BrandWallEntity
import io.reactivex.rxjava3.core.Observable

class BrandWallModel : BrandWallContract.Model {
    override fun getRequest(
        map: Map<String, String>,
        isRefresh: Boolean
    ): Observable<BrandWallEntity> = if (isRefresh) {
        RetrofitManager.service.getPage(map)
    } else {
        RetrofitManager.service.getCallMetroList(map)
    }


}