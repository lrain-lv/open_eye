package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.entity.DiscoverEntity

class FindViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var paramData = MutableLiveData<EyeResult<DiscoverEntity>>()


    var isRefresh = MutableLiveData<Boolean>(false)

    fun refresh(isFirst: Boolean = false) {
        if (!isFirst)
            isRefresh.value = true
        launchOnUi {
            val discovery = serviceHelper.getDiscovery()
            paramData.value = discovery
            isRefresh.value = false
        }
    }


}