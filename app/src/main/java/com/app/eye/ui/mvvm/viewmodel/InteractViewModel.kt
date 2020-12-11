package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.entity.InteractEntity
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING

class InteractViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {

    private val _entityLiveData = MutableLiveData<EyeResult<InteractEntity>>()
    val entityLiveData = _entityLiveData

    val refreshLiveData = MutableLiveData<Boolean>(false)
    val enableRefreshLiveData = MutableLiveData<Boolean>(true)
    val enableLoadMoreLiveData = MutableLiveData<Boolean>(false)
    val statusViewLiveData = MutableLiveData<Int>(STATUS_LOADING)

    var nextPageUrl: String? = ""

    var isRefresh = true

    fun onRefresh(isFirst: Boolean = false) {
        launchOnUi {
            isRefresh = true
            enableLoadMoreLiveData.value = false
            if (isFirst) {
                statusViewLiveData.value = STATUS_LOADING
            } else {
                statusViewLiveData.value = STATUS_CONTENT
                refreshLiveData.value = true
            }
            val result = serviceHelper.getDiscoverMyMsg()
            enableLoadMoreLiveData.value = true
            refreshLiveData.value = false
            if (result is EyeResult.Success) {
                statusViewLiveData.value = STATUS_CONTENT
                nextPageUrl = result.data.nextPageUrl
                _entityLiveData.value = result
            } else {
                statusViewLiveData.value = STATUS_ERROR
            }
        }

    }

    fun onLoadMore() {
        launchOnUi {
            isRefresh = false
            enableRefreshLiveData.value = false

            val result = serviceHelper.getDiscoverMyMsg()

            enableRefreshLiveData.value = true
            if (result is EyeResult.Success) {
                nextPageUrl = result.data.nextPageUrl
                _entityLiveData.value = result
            }
        }

    }
}