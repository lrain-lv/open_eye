package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.Result
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.mvp.model.entity.DailyEntity
import com.app.eye.ui.mvp.model.entity.DiscoverEntity

class DailyViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var paramData = MutableLiveData<Result<DailyEntity>>()

    var isRefresh = MutableLiveData<Boolean>()

    fun refresh(map: Map<String, String>, isFirst: Boolean = false) {
        if (!isFirst) isRefresh.value = true
        launchOnUi {
            val dailyEntity = serviceHelper.getDaily(map)
            paramData.value = dailyEntity
        }
        if (!isFirst) isRefresh.value = false
    }

    fun loadMore(map: Map<String, String>) {
        launchOnUi {
            val dailyEntity = serviceHelper.getDaily(map)
            paramData.value = dailyEntity
        }
    }
}