package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.DailyEntity
import io.reactivex.rxjava3.core.Observable

interface DailyContract {

    interface View : IBaseView {
        fun setDailyEntity(entity: DailyEntity?)
    }

    interface Model : IModel {
        fun getDailyData(
            isRefresh: Boolean = true,
            map: Map<String, String> = hashMapOf()
        ): Observable<DailyEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getDailyData(isRefresh: Boolean = true, map: Map<String, String> = hashMapOf())
    }
}