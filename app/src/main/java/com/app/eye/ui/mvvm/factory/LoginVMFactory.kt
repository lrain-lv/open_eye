package com.app.eye.ui.mvvm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.mvp.model.LoginModel
import com.app.eye.ui.mvvm.viewmodel.DailyViewModel
import com.app.eye.ui.mvvm.viewmodel.FindViewModel
import com.app.eye.ui.mvvm.viewmodel.LoginViewModel
import com.app.eye.ui.mvvm.viewmodel.RecommendViewModel

class LoginVMFactory(private val serviceHelper: ServiceHelper) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(serviceHelper) as T
    }
}