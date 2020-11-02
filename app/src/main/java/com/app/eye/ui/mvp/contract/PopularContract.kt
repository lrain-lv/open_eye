package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.RecentPopularEntity
import io.reactivex.rxjava3.core.Observable

interface PopularContract {
    interface View : IBaseView {
        fun setRecentShareCountResponse(entity: RecentPopularEntity?)
    }

    interface Model : IModel {
        fun getRecentShareCount(map: Map<String, String>): Observable<RecentPopularEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getRecentShareCount(map: Map<String, String>)
    }

}