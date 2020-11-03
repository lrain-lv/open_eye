package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.HomeRecEntity
import io.reactivex.rxjava3.core.Observable

interface HomeRecContract {

    interface View : IBaseView {
        fun setResponse(entity: HomeRecEntity?)
    }

    interface Model : IModel {
        fun getRecData(map: Map<String, String>): Observable<HomeRecEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getRecData(map: Map<String, String>)
    }
}