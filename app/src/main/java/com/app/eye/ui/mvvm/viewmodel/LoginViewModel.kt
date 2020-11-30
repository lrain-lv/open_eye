package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.mvp.model.entity.LoginEntity

class LoginViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var loginEntity = MutableLiveData<EyeResult<LoginEntity>>()

    fun doLogin(map: Map<String, String>) {
        val username = map["username"]
        val pwd = map["pwd"]
        launchOnUi {
            val entity = serviceHelper.doLogin(username = username!!, password = pwd!!)
            loginEntity.value = entity
        }
    }
}