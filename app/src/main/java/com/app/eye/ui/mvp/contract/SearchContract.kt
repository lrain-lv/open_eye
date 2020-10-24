package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.HotSearchEntity
import com.app.eye.ui.mvp.model.entity.SearchEntity
import io.reactivex.rxjava3.core.Observable

interface SearchContract {
    interface View : IBaseView {
        fun setHotSearchData(dataList: MutableList<HotSearchEntity>)

        fun setPreSearchResult(dataList: MutableList<HotSearchEntity>)

        fun setSearchResult(searchEntity: SearchEntity)
    }

    interface Model : IModel {
        fun getHotData(): Observable<MutableList<String>>

        fun doPreSearchData(query: String): Observable<MutableList<String>>

        fun doSearch(query: String): Observable<SearchEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getHotData()

        fun doPreSearchData(query: String)

        fun doSearch(query: String)
    }
}