package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.mvp.model.entity.BrandListItemX
import com.app.eye.ui.mvp.model.entity.BrandWallEntity
import com.app.eye.ui.mvp.model.entity.MessageEntity
import com.app.eye.ui.mvp.model.entity.PushEntity
import com.app.eye.widgets.*
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BrandWallViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var brandListLiveData = MutableLiveData<MutableList<BrandListItemX>>()
    var indexListLiveData = MutableLiveData<MutableList<String>>()

    var brandWallLiveData = MutableLiveData<EyeResult<BrandWallEntity>>()

    var refreshLiveData = MutableLiveData<Boolean>(false)
    var statusLiveData = MutableLiveData<Int>()

    fun getBrandList() {
        statusLiveData.value = STATUS_LOADING
        val list = mutableListOf<BrandListItemX>()
        val indexList = mutableListOf<String>()
        launchOnUi {
            withContext(Dispatchers.IO) {
                val result = serviceHelper.getBrandList()
                if (result is EyeResult.Success) {
                    result.data.result.item_list.forEach { it ->
                        val key = it.key
                        val item = BrandListItemX("", key)
                        list.add(item)
                        indexList.add(key)
                        list.addAll(it.items)
                    }
                }
            }
            brandListLiveData.value = list
            indexListLiveData.value = indexList
            statusLiveData.value = STATUS_CONTENT
        }
    }


    fun onRefresh(map: Map<String, String>, isFirst: Boolean = false) {
        if (isFirst) {
            statusLiveData.value = STATUS_LOADING
        } else {
            statusLiveData.value = STATUS_CONTENT
            refreshLiveData.value = true
        }
        launchOnUi {
            val result = serviceHelper.getPage(map)
            if (result is EyeResult.Error) {
                statusLiveData.value = STATUS_ERROR
            } else {
                statusLiveData.value = STATUS_CONTENT
            }
            brandWallLiveData.value = result
            refreshLiveData.value = false
        }
    }

    fun onLoadMore(map: Map<String, String>, ) {
        launchOnUi {
            val result = serviceHelper.getCallMetroList(map)
            brandWallLiveData.value = result
        }
    }
}