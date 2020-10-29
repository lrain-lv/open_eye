package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.RankEntity
import io.reactivex.rxjava3.core.Observable

interface RankContract {
    interface View : IBaseView {
        fun setRankResponse(type: Int, data: RankEntity?)
    }

    interface Model : IModel {
        fun getRankRequest(type: Int, map: Map<String, String>): Observable<RankEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getRankRequest(type: Int, map: Map<String, String>)
    }
}