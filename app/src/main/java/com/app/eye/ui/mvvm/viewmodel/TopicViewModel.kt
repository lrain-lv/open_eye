package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.entity.*
import com.app.eye.widgets.*

class TopicViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var refreshLiveData = MutableLiveData<Boolean>(false)
    var statusLiveData = MutableLiveData<Int>()

    var topicListLiveData = MutableLiveData<EyeResult<TopicListEntity>>()

    var tabChildLiveData = MutableLiveData<EyeResult<TabChildEntity>>()

    var tabLiveData = MutableLiveData<EyeResult<TagTabEntity>>()

    fun getTopicList(
        map: Map<String, String>,
        isFirst: Boolean = false,
        isLoadMore: Boolean = false
    ) {
        if (!isLoadMore) {
            if (isFirst) {
                statusLiveData.value = STATUS_LOADING
            } else {
                statusLiveData.value = STATUS_CONTENT
                refreshLiveData.value = true
            }
            launchOnUi {
                val result = serviceHelper.getTopicList(map)
                if (result is EyeResult.Error) {
                    statusLiveData.value = STATUS_ERROR
                } else {
                    statusLiveData.value = STATUS_CONTENT
                }
                topicListLiveData.value = result
                refreshLiveData.value = false
            }
        } else {
            launchOnUi {
                val result = serviceHelper.getTopicList(map)
                topicListLiveData.value = result
            }
        }
    }

    fun getTabChild(
        id: Int, map: Map<String, String>,
        isFirst: Boolean = false,
        isLoadMore: Boolean = false
    ) {
        if (!isLoadMore) {
            if (isFirst) {
                statusLiveData.value = STATUS_LOADING
            } else {
                statusLiveData.value = STATUS_CONTENT
                refreshLiveData.value = true
            }
            launchOnUi {
                val result = serviceHelper.getTabChild(id, map)
                if (result is EyeResult.Error) {
                    statusLiveData.value = STATUS_ERROR
                } else {
                    statusLiveData.value = STATUS_CONTENT
                }
                tabChildLiveData.value = result
                refreshLiveData.value = false
            }
        } else {
            launchOnUi {
                val result = serviceHelper.getTabChild(id, map)
                tabChildLiveData.value = result
            }
        }
    }

    fun getTab() {
        launchOnUi {
            val result = serviceHelper.getTabTagList()
            tabLiveData.value = result
        }
    }

}