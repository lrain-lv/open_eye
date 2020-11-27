package com.app.eye.ui.mvvm.factory

import com.app.eye.http.mvvm.ServiceHelper

object InjectorUtil {
    fun getFindVMFactory() = FindVMFactory(ServiceHelper.getInstance())

    fun getRecommendVMFactory() = RecommendVMFactory(ServiceHelper.getInstance())
    fun getDailyVMFactory() = DailyVMFactory(ServiceHelper.getInstance())
}