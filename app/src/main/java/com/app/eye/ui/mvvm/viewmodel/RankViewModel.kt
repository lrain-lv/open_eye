package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.entity.RankEntity
import com.app.eye.widgets.*

class RankViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var entityLiveData = MutableLiveData<EyeResult<RankEntity>>()
    var refreshLiveData = MutableLiveData<Boolean>(false)
    var statusLiveData = MutableLiveData<Int>()

    fun onRefresh(type: Int, map: Map<String, String>, isFirst: Boolean = false) {
        if (isFirst) {
            statusLiveData.value = STATUS_LOADING
        } else {
            statusLiveData.value = STATUS_CONTENT
            refreshLiveData.value = true
        }
        launchOnUi {
            val result =
                serviceHelper.getRankList(
                    strategy = if (type == 0) "weekly" else if (type == 1) "monthly" else "historical",
                    map = map
                )
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
            val result = serviceHelper.getRankList(
                if (type == 0) "weekly" else if (type == 1) "monthly" else "historical",
                map
            )
            entityLiveData.value = result
        }
    }
}