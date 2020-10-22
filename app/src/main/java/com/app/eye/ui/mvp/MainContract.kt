package com.app.eye.ui.mvp

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody

interface MainContract {

    interface View : IBaseView {
        fun getData()

        fun getResponse(s: String)
    }

    interface Model : IModel {
        fun getData():Observable<ResponseBody>
    }

    interface Presenter : IPresenter<View> {
        fun getData()
    }
}