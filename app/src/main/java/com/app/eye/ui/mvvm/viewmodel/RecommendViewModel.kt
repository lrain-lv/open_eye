package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.entity.HomeRecEntity

class RecommendViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {

    var entityLiveData = MutableLiveData<EyeResult<HomeRecEntity>>()

    var isRefresh = MutableLiveData<Boolean>()

    fun refresh(isFirst: Boolean = false, map: Map<String, String>) {
        if (!isFirst) isRefresh.value = true
        launchOnUi {
            val homeRec = serviceHelper.getHomeRec(map)
            entityLiveData.value = homeRec
        }
        isRefresh.value = false
    }

    fun loadMore(map: Map<String, String>) {
        launchOnUi {
            val homeRec = serviceHelper.getHomeRec(map)
            entityLiveData.value = homeRec
        }
    }

}