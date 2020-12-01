package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.entity.ComRecEntity

class GalleryViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var entityLiveData = MutableLiveData<EyeResult<ComRecEntity>>()

    fun onLoadMore(map: Map<String, String>) {

        launchOnUi {
            val result = serviceHelper.getCommunityRec(map)
            entityLiveData.value = result

        }
    }


}