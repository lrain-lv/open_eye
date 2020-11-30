package com.app.eye.ui.mvvm.factory

import com.app.eye.http.mvvm.ServiceHelper
import com.app.eye.ui.mvvm.viewmodel.SearchViewModel

object InjectorUtil {
    fun getFindVMFactory() = FindVMFactory(ServiceHelper.getInstance())
    fun getRecommendVMFactory() = RecommendVMFactory(ServiceHelper.getInstance())
    fun getDailyVMFactory() = DailyVMFactory(ServiceHelper.getInstance())
    fun getLoginVMFactory() = LoginVMFactory(ServiceHelper.getInstance())
    fun getPushVMFactory() = PushVMFactory(ServiceHelper.getInstance())
    fun getMessageVMFactory() = MessageVMFactory(ServiceHelper.getInstance())
    fun getCategoryVMFactory() = CategoryVMFactory(ServiceHelper.getInstance())
    fun getCommunityVMFactory() = CommunityVMFactory(ServiceHelper.getInstance())
    fun getPopularVMFactory() = PopularVMFactory(ServiceHelper.getInstance())
    fun getRankVMFactory() = RankVMFactory(ServiceHelper.getInstance())
    fun getTagDynamicVMFactory() = TagDynamicVMFactory(ServiceHelper.getInstance())
    fun getTopicVMFactory() = TopicVMFactory(ServiceHelper.getInstance())
    fun getLightTopVMFactory() = LightTopVMFactory(ServiceHelper.getInstance())
    fun getBadgeVMFactory() = BadgeVMFactory(ServiceHelper.getInstance())
    fun getBrandWallVMFactory() = BrandWallVMFactory(ServiceHelper.getInstance())
    fun getGalleryVMFactory() = GalleryVMFactory(ServiceHelper.getInstance())
    fun getSearchVMFactory() = SearchVMFactory(ServiceHelper.getInstance())
    fun getTopicDetailVMFactory() = TopicDetailVMFactory(ServiceHelper.getInstance())
}