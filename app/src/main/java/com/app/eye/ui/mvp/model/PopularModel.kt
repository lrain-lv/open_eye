package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.PopularContract
import com.app.eye.ui.mvp.model.entity.RecentPopularEntity
import io.reactivex.rxjava3.core.Observable

class PopularModel : PopularContract.Model {
    override fun getRecentShareCount(map: Map<String, String>): Observable<RecentPopularEntity> =
        RetrofitManager.service.getShareCount(map)

}