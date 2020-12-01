package com.app.eye.ui.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.app.eye.base.mvvm.BaseViewModel
import com.app.eye.http.mvvm.EyeResult
import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.entity.CategoryEntity
import com.app.eye.ui.entity.InformationEntity
import com.app.eye.ui.entity.RecFriendEntity
import com.app.eye.ui.entity.SpecialTopicEntity
import com.app.eye.widgets.STATUS_CONTENT
import com.app.eye.widgets.STATUS_ERROR
import com.app.eye.widgets.STATUS_LOADING

class CategoryViewModel(private val serviceHelper: ServiceHelper) : BaseViewModel() {
    var refreshLiveData = MutableLiveData<Boolean>(false)
    var statusLiveData = MutableLiveData<Int>()

    var categoryLiveData = MutableLiveData<EyeResult<CategoryEntity>>()
    var specialTopicLiveData = MutableLiveData<EyeResult<SpecialTopicEntity>>()
    var informationLiveData = MutableLiveData<EyeResult<InformationEntity>>()
    var recFriendLiveData = MutableLiveData<EyeResult<RecFriendEntity>>()

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
                        val categories = serviceHelper.getCategories()
                        categoryLiveData.value = categories
                        categories
                    }
                    1 -> {
                        val specialTopic = serviceHelper.getSpecialTopic(map)
                        specialTopicLiveData.value = specialTopic
                        specialTopic
                    }
                    2 -> {
                        val informationList = serviceHelper.getInformationList(map)
                        informationLiveData.value = informationList
                        informationList
                    }
                    else -> {
                        val recFriend = serviceHelper.getRecFriend(map)
                        recFriendLiveData.value = recFriend
                        recFriend

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
              1 -> {
                  val specialTopic = serviceHelper.getSpecialTopic(map)
                  specialTopicLiveData.value = specialTopic
              }
              2 -> {
                  val informationList = serviceHelper.getInformationList(map)
                  informationLiveData.value = informationList
              }
              else -> {
                  val recFriend = serviceHelper.getRecFriend(map)
                  recFriendLiveData.value = recFriend
              }
          }
      }
    }
}