package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.mvp.model.entity.PushEntity
import com.app.eye.ui.mvp.model.entity.RecentPopularEntity
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING

class PopularViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var entityLiveData = MutableLiveData<EyeResult<RecentPopularEntity>>()
    var refreshLiveData = MutableLiveData<Boolean>(false)
    var statusLiveData = MutableLiveData<Int>()

    fun onRefresh(map: Map<String, String>, isFirst: Boolean = false) {
        if (isFirst) {
            statusLiveData.value = STATUS_LOADING
        } else {
            statusLiveData.value = STATUS_CONTENT
            refreshLiveData.value = true
        }
        launchOnUi {
            val result = serviceHelper.getShareCount(map)
            if (result is EyeResult.Error) {
                statusLiveData.value = STATUS_ERROR
            } else {
                statusLiveData.value = STATUS_CONTENT
            }
            entityLiveData.value = result
            refreshLiveData.value = false
        }
    }

    fun onLoadMore(map: Map<String, String>, ) {
        launchOnUi {
            val result = serviceHelper.getShareCount(map)
            entityLiveData.value = result
        }
    }
}