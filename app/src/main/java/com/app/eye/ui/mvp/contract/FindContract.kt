package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.DiscoverEntity
import io.reactivex.rxjava3.core.Observable

interface FindContract {

    interface View : IBaseView {
        fun setResponse(discoverEntity: DiscoverEntity)
    }

    interface Model : IModel {
        fun getDiscoveryData(): Observable<DiscoverEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getDiscoveryData()
    }
}