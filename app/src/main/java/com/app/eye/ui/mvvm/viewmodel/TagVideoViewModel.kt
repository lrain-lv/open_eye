package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.entity.*
import com.app.eye.widgets.*

class TagVideoViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var entityLiveData = MutableLiveData<EyeResult<TagVideoEntity>>()
    var indexEntityLiveData = MutableLiveData<EyeResult<TagIndexEntity>>()
    var refreshLiveData = MutableLiveData<Boolean>(false)
    var statusLiveData = MutableLiveData<Int>()

    fun getTagIndex(id: String) {
        launchOnUi {
            val result = serviceHelper.getTagIndex(id)
            indexEntityLiveData.value = result
        }
    }

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
                        serviceHelper.getTagVideo(map = map)
                    }
                    else -> {
                        serviceHelper.getTagDynamic(map = map)
                    }
                }
            if (result is EyeResult.Error) {
                statusLiveData.value = STATUS_ERROR
            } else {
                statusLiveData.value = STATUS_CONTENT
            }
            entityLiveData.value = result
            refreshLiveData.value = false
        }
    }

    fun onLoadMore(type: Int, map: Map<String, String>, ) {
        launchOnUi {
            val result = when (type) {
                0 -> {
                    serviceHelper.getTagVideo(map = map)
                }
                else -> {
                    serviceHelper.getTagDynamic(map = map)
                }
            }
            entityLiveData.value = result
        }
    }
}