package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.entity.ComAttentionEntity
import com.app.eye.ui.entity.ComRecEntity
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING

class CommunityViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var refreshLiveData = MutableLiveData<Boolean>(false)
    var statusLiveData = MutableLiveData<Int>()

    var comRecLiveData = MutableLiveData<EyeResult<ComRecEntity>>()
    var comAttLiveData = MutableLiveData<EyeResult<ComAttentionEntity>>()

    fun onRefresh(type: Int, map: Map<String, String>, isFirst: Boolean = false) {
        if (isFirst) {
            statusLiveData.value = STATUS_LOADING
        } else {
            statusLiveData.value = STATUS_CONTENT
            refreshLiveData.value = true
        }
        launchOnUi {

            val result =
                when (type) {
                    0 -> {
                        val comRecEntity = serviceHelper.getCommunityRec(map)
                        comRecLiveData.value = comRecEntity
                        comRecEntity
                    }
                    else -> {
                        val entity = serviceHelper.getComFollow(map)
                        comAttLiveData.value = entity
                        entity
                    }

                }
            if (result is EyeResult.Error) {
                statusLiveData.value = STATUS_ERROR
            } else {
                statusLiveData.value = STATUS_CONTENT
            }
            refreshLiveData.value = false
        }
    }

    fun onLoadMore(type: Int, map: Map<String, String>) {
        launchOnUi {
            when (type) {
                0 -> {
                    val comRecEntity = serviceHelper.getCommunityRec(map)
                    comRecLiveData.value = comRecEntity
                }
                else -> {
                    val entity = serviceHelper.getComFollow(map)
                    comAttLiveData.value = entity
                }
            }
        }
    }
}