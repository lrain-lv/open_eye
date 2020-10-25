package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.LoginContract
import com.app.eye.ui.mvp.model.LoginModel
import com.app.eye.ui.mvp.model.entity.LoginEntity
import com.orhanobut.logger.Logger

class LoginPresenter : BasePresenter<LoginContract.Model, LoginContract.View>(),
    LoginContract.Presenter {
    override fun createModel(): LoginContract.Model =
        LoginModel()

    override fun doLogin(name: String, pwd: String) {
        model.doLogin(name, pwd)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setLoginResponse(it)
            }, {
                view?.setLoginResponse(LoginEntity(-1, null, it.message))
            })
    }
}