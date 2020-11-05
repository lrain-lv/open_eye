package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.CategoryEntity
import com.app.eye.ui.mvp.model.entity.InformationEntity
import com.app.eye.ui.mvp.model.entity.RecFriendEntity
import com.app.eye.ui.mvp.model.entity.SpecialTopicEntity
import io.reactivex.rxjava3.core.Observable

interface CategoryContract {
    interface View : IBaseView {
        fun setCategoryResponse(data: CategoryEntity?)

        fun setSpecialTopicResponse(data: SpecialTopicEntity?)

        fun setInformationResponse(data: InformationEntity?)

        fun setRecFriendResponse(entity: RecFriendEntity?)
    }

    interface Model : IModel {
        fun getCategoryRequest(): Observable<CategoryEntity>
        fun getSpecialTopicRequest(map: Map<String, String>): Observable<SpecialTopicEntity>
        fun getInformationRequest(map: Map<String, String>): Observable<InformationEntity>
        fun getRecFriendRequest(map: Map<String, String>): Observable<RecFriendEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getCategoryRequest()
        fun getSpecialTopicRequest(map: Map<String, String>)
        fun getInformationRequest(map: Map<String, String>)

        fun getRecFriendRequest(map: Map<String, String>)
    }
}