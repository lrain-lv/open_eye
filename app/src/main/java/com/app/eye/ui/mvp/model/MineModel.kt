package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.MineContract
import com.app.eye.ui.mvp.model.entity.BadgeEntity
import io.reactivex.rxjava3.core.Observable

class MineModel : MineContract.Model {
    override fun getTagMedals(): Observable<BadgeEntity> {
        return RetrofitManager.service.getTagMedals()
    }

}