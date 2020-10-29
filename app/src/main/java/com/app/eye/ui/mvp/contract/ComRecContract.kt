package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.ComAttentionEntity
import com.app.eye.ui.mvp.model.entity.ComRecEntity
import io.reactivex.rxjava3.core.Observable

interface CommunityContract {

    interface View : IBaseView {
        fun setComRecResponse(comRecEntity: ComRecEntity?)

        fun setComAttentionResponse(entity: ComAttentionEntity?)
    }

    interface Model : IModel {
        fun getRecRequest(map: Map<String, String>): Observable<ComRecEntity>
        fun getComAttentionRequest(map: Map<String, String>): Observable<ComAttentionEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getRecRequest(isRefresh: Boolean ,map: Map<String, String>)
        fun getComAttentionRequest(map: Map<String, String>)
    }
}