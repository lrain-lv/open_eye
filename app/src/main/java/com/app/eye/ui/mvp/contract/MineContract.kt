package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.BadgeEntity
import io.reactivex.rxjava3.core.Observable
import java.util.*

interface MineContract {
    interface View : IBaseView {
        fun setMedalsData(entity: BadgeEntity?)

    }

    interface Model : IModel {
        fun getTagMedals(): Observable<BadgeEntity>
    }


    interface Presenter : IPresenter<View> {
        fun getTagMedals()
    }
}