package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.BrandWallEntity
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import java.util.*

interface BrandWallContract {
    interface View : IBaseView {
        fun setBrandWallData(entity: BrandWallEntity?)
    }

    interface Model : IModel {
        fun getRequest(map: Map<String, String>, isRefresh: Boolean): Observable<BrandWallEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getRequest(map: Map<String, String>, isRefresh: Boolean)
    }
}