package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.PushEntity
import io.reactivex.rxjava3.core.Observable

interface PushContract {
    interface View : IBaseView {

        fun setPushResponse(pushEntity: PushEntity?)

    }

    interface Model : IModel {
        fun getPushRequest(map: Map<String, String>): Observable<PushEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getPushRequest(map: Map<String, String>)
    }


}