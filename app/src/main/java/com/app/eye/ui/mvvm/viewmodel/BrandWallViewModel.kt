package com.app.eye.ui.mvvm.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.entity.BrandListItemX
import com.app.eye.ui.entity.BrandWallEntity
import com.app.eye.widgets.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BrandWallViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {

    private val _brandListLiveData = MutableLiveData<MutableList<BrandListItemX>>()
    val brandListLiveData = _brandListLiveData

    private val _indexListLiveData = MutableLiveData<MutableList<String>>()
    val indexListLiveData = _indexListLiveData

    private val _brandWallLiveData = MutableLiveData<EyeResult<BrandWallEntity>>()
    var brandWallLiveData = _brandWallLiveData

    val refreshLiveData = MutableLiveData(false)
    val enableLoadMoreLiveData = MutableLiveData(false)
    val enableRefreshLiveData = MutableLiveData(true)
    val statusLiveData = MutableLiveData<Int>()

    private val brandWallMap = mapOf(
        "page_label" to "brand_wall",
        "title" to "品牌墙",
        "page_type" to "card"
    )

    private val brandDetailMap = hashMapOf(
        "page_label" to "brand_wall_detail",
        "page_type" to "card"
    )

    var isRefresh = true

    private var paramsMap = hashMapOf<String, String>()

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
            _brandListLiveData.value = list
            _indexListLiveData.value = indexList
            statusLiveData.value = STATUS_CONTENT
        }
    }


    fun onRefresh(id: String = "", isDetail: Boolean = false, isFirst: Boolean = false) {
        launchOnUi {
            isRefresh = true
            enableLoadMoreLiveData.value = false
            if (isFirst) {
                statusLiveData.value = STATUS_LOADING
            } else {
                statusLiveData.value = STATUS_CONTENT
                refreshLiveData.value = true
            }
            if (isDetail) {
                brandDetailMap["resource_id"] = id
            }
            val result = serviceHelper.getPage(if (isDetail) brandDetailMap else brandWallMap)
            if (result is EyeResult.Success) {
                statusLiveData.value = STATUS_CONTENT
                val cardList = result.data.result.card_list
                val brandCard = cardList.filter { TextUtils.equals("call_metro_list", it.type) }[0]
                val apiRequest = brandCard.card_data.body.api_request
                paramsMap["type"] = apiRequest.params.type
                paramsMap["last_item_id"] = apiRequest.params.last_item_id
                paramsMap["num"] = apiRequest.params.num.toString()
                paramsMap["card"] = apiRequest.params.card
                paramsMap["card_index"] = apiRequest.params.card_index.toString()
                paramsMap["material_index"] =
                    apiRequest.params.material_index.toString()
                paramsMap["material_relative_index"] =
                    apiRequest.params.material_relative_index.toString()
                paramsMap["start_last_item_id"] =
                    apiRequest.params.start_last_item_id
                enableLoadMoreLiveData.value = true
                _brandWallLiveData.value = result
            } else {
                statusLiveData.value = STATUS_ERROR
            }

            refreshLiveData.value = false
        }
    }

    fun onLoadMore() {
        launchOnUi {
            isRefresh = false
            enableRefreshLiveData.value = false
            val result = serviceHelper.getCallMetroList(paramsMap)
            if (result is EyeResult.Success) {
                val lastItemId = result.data.result.last_item_id
                paramsMap["last_item_id"] = lastItemId ?: ""
            }
            _brandWallLiveData.value = result
            enableRefreshLiveData.value = true
        }
    }
}