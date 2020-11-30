package com.app.eye.ui.mvvm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.mvvm.viewmodel.DailyViewModel
import com.app.eye.ui.mvvm.viewmodel.FindViewModel
import com.app.eye.ui.mvvm.viewmodel.PushViewModel
import com.app.eye.ui.mvvm.viewmodel.RecommendViewModel

class PushVMFactory(private val serviceHelper: ServiceHelper) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PushViewModel(serviceHelper) as T
    }
}