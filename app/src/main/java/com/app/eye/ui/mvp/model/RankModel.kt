package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.RankContract
import com.app.eye.ui.mvp.model.entity.RankEntity
import io.reactivex.rxjava3.core.Observable

class RankModel : RankContract.Model {
    //0 weekly  1 monthly  2 historical
    override fun getRankRequest(type: Int, map: Map<String, String>): Observable<RankEntity> =
        RetrofitManager.service.getRankList(if (type == 0) "weekly" else if (type == 1) "monthly" else "historical",
            map)
}