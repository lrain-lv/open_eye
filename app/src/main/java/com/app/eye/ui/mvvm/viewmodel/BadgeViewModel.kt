package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.mvp.model.entity.BadgeEntity
import com.app.eye.ui.mvp.model.entity.MessageEntity
import com.app.eye.ui.mvp.model.entity.PushEntity
import com.app.eye.widgets.*

class BadgeViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var entityLiveData = MutableLiveData<EyeResult<BadgeEntity>>()
    var refreshLiveData = MutableLiveData<Boolean>(false)
    var statusLiveData = MutableLiveData<Int>()

    fun onRefresh(isFirst: Boolean = false) {
        if (isFirst) {
            statusLiveData.value = STATUS_LOADING
        } else {
            statusLiveData.value = STATUS_CONTENT
            refreshLiveData.value = true
        }
        launchOnUi {
            val result = serviceHelper.getTagMedals()
            if (result is EyeResult.Error) {
                statusLiveData.value = STATUS_ERROR
            } else {
                statusLiveData.value = STATUS_CONTENT
            }
            entityLiveData.value = result
            refreshLiveData.value = false
        }
    }

}