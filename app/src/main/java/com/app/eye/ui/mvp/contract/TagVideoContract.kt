package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.TagIndexEntity
import com.app.eye.ui.mvp.model.entity.TagVideoEntity
import io.reactivex.rxjava3.core.Observable

interface TagVideoContract {
    interface View : IBaseView {
        fun setTagIndexResponse(entity: TagIndexEntity?)

        fun setTagVideoResponse(entity: TagVideoEntity?)
    }

    interface Model : IModel {
        fun getTagIndexRequest(id: String): Observable<TagIndexEntity>
        fun getTagVideoRequest(map: Map<String, String>): Observable<TagVideoEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getTagIndexRequest(id: String)
        fun getTagVideoRequest(map: Map<String, String>)
    }
}