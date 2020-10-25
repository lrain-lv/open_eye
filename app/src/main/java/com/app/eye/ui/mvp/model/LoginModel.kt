package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.LoginContract
import com.app.eye.ui.mvp.model.entity.LoginEntity
import io.reactivex.rxjava3.core.Observable

class LoginModel : LoginContract.Model {
    override fun doLogin(name: String, pwd: String): Observable<LoginEntity> =
        RetrofitManager.service.doLogin(name, pwd)
}