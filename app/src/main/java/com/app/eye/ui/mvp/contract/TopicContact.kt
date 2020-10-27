package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.TabChildEntity
import com.app.eye.ui.mvp.model.entity.TagTabEntity
import com.app.eye.ui.mvp.model.entity.TopicListEntity
import io.reactivex.rxjava3.core.Observable

interface TopicContact {
    interface View : IBaseView {
        fun setTabResponse(data: TagTabEntity?)

        fun setTabChildResponse(data: TabChildEntity?)

        fun setTopicListResponse(data: TopicListEntity?)
    }

    interface Model : IModel {
        fun getTabRequest(): Observable<TagTabEntity>

        fun getTabChildRequest(id: Int, map: Map<String, String>): Observable<TabChildEntity>
        fun getTopicListRequest(map: Map<String, String>): Observable<TopicListEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getTabRequest()

        fun getTabChildRequest(id: Int, map: Map<String, String>)
        fun getTopicListRequest(map: Map<String, String>)
    }
}