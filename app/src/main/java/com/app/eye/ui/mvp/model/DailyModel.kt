package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.DailyContract
import com.app.eye.ui.mvp.model.entity.DailyEntity
import io.reactivex.rxjava3.core.Observable

class DailyModel : DailyContract.Model {
    override fun getDailyData(isRefresh: Boolean, map: Map<String, String>): Observable<DailyEntity> {
      return RetrofitManager.service.getDaily(map)
    }
}