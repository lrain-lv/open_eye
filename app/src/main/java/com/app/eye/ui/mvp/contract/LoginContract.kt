package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.LoginEntity
import io.reactivex.rxjava3.core.Observable

interface LoginContract {
    interface View : IBaseView {
        fun setLoginResponse(loginEntity: LoginEntity?)
    }

    interface Model : IModel {
        fun doLogin(name: String, pwd: String): Observable<LoginEntity>
    }

    interface Presenter : IPresenter<View> {
        fun doLogin(name: String, pwd: String)
    }
}