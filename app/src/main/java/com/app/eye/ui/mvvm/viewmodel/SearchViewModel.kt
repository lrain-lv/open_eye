package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.entity.HotSearchEntity
import com.app.eye.ui.entity.SearchEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var hotLiveData = MutableLiveData<MutableList<HotSearchEntity>>()
    var searchEntityLiveData = MutableLiveData<EyeResult<SearchEntity>>()

    var preSearchLiveData = MutableLiveData<MutableList<HotSearchEntity>>()

    var refreshLiveData = MutableLiveData<Boolean>(false)
    var statusLiveData = MutableLiveData<Int>()

//    fun onRefresh(map: Map<String, String>, isFirst: Boolean = false) {
//        if (isFirst) {
//            statusLiveData.value = STATUS_LOADING
//        } else {
//            statusLiveData.value = STATUS_CONTENT
//            refreshLiveData.value = true
//        }
//        launchOnUi {
//            val result = serviceHelper.getPushMessage(map)
//            if (result is EyeResult.Error) {
//                statusLiveData.value = STATUS_ERROR
//            } else {
//                statusLiveData.value = STATUS_CONTENT
//            }
//            entityLiveData.value = result
//            refreshLiveData.value = false
//        }
//    }
//
//    fun onLoadMore(map: Map<String, String>, ) {
//        launchOnUi {
//            val result = serviceHelper.getPushMessage(map)
//            entityLiveData.value = result
//        }
//    }

    fun getHotData() {
        val list = mutableListOf<HotSearchEntity>()
        launchOnUi {
            withContext(Dispatchers.IO) {
                val hot = serviceHelper.getHot()
                if (hot is EyeResult.Success) {
                    list.add(HotSearchEntity("搜索历史", isKeyWord = false, isHistory = true))
                    list.add(HotSearchEntity("艺术", isKeyWord = true, isHistory = true))
                    list.add(HotSearchEntity("街头", isKeyWord = true, isHistory = true))
                    list.add(HotSearchEntity("摄影日常", isKeyWord = true, isHistory = true))
                    list.add(HotSearchEntity("热搜关键词", false))
                    hot.data.forEach { it ->
                        list.add(HotSearchEntity(it, true))
                    }
                }
            }
            hotLiveData.value = list
        }
    }

    fun doPreSearchData(query: String) {
        val list = mutableListOf<HotSearchEntity>()
        launchOnUi {
            withContext(Dispatchers.IO) {
                val result = serviceHelper.doPreSearch(query)
                if (result is EyeResult.Success) {
                    result.data.forEach { it ->
                        list.add(
                            HotSearchEntity(
                                keyword = query,
                                searchResult = it,
                                isSearch = true
                            )
                        )
                    }
                }
            }
            preSearchLiveData.value = list
        }
    }

    fun doSearch(query: String) {
        launchOnUi {
            val result = serviceHelper.doSearch(query)
            searchEntityLiveData.value = result
        }
    }
}