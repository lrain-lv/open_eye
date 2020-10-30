package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.LightTopicInternalEntity
import io.reactivex.rxjava3.core.Observable

interface LightTopicContract {
    interface View : IBaseView {
        fun setLightTopicResponse(entity: LightTopicInternalEntity?)
    }

    interface Model : IModel {
        fun getLightTopicRequest(id: Int): Observable<LightTopicInternalEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getLightTopicRequest(id: Int)
    }
}