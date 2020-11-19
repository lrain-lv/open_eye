package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.BrandListEntity
import com.app.eye.ui.mvp.model.entity.BrandListItemX
import com.app.eye.ui.mvp.model.entity.BrandWallEntity
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import java.util.*

interface BrandWallContract {
    interface View : IBaseView {
        fun setBrandWallData(entity: BrandWallEntity?)
        fun setBrandListData(list: MutableList<BrandListItemX>,indexList:MutableList<String>)
    }

    interface Model : IModel {
        fun getRequest(map: Map<String, String>, isRefresh: Boolean): Observable<BrandWallEntity>
        fun getListRequest(): Observable<BrandListEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getRequest(map: Map<String, String>, isRefresh: Boolean)
        fun getListRequest()
    }
}