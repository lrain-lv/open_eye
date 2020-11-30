package com.app.eye.ui.mvvm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.mvvm.viewmodel.CategoryViewModel
import com.app.eye.ui.mvvm.viewmodel.DailyViewModel
import com.app.eye.ui.mvvm.viewmodel.FindViewModel
import com.app.eye.ui.mvvm.viewmodel.RecommendViewModel

class CategoryVMFactory(private val serviceHelper: ServiceHelper) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryViewModel(serviceHelper) as T
    }
}