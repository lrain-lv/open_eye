package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.entity.MessageEntity
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING

class MessageViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var entityLiveData = MutableLiveData<EyeResult<MessageEntity>>()
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
            val result = serviceHelper.getPrivateMsg(map)
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
            val result = serviceHelper.getPrivateMsg(map)
            entityLiveData.value = result
        }
    }
}