package com.app.eye.http.mvvm

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.model.entity.ConfigEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class ServiceHelper {
    val service: ApiService by lazy { RetrofitManager.sInstance.createService(ApiService::class.java) }

    companion object {
        var serviceHelper: ServiceHelper? = null
        fun getInstance(): ServiceHelper {
            if (serviceHelper == null) {
                synchronized(ServiceHelper::class.java) {
                    if (serviceHelper == null) {
                        serviceHelper = ServiceHelper()
                    }
                }
            }
            return serviceHelper!!
        }
    }

    suspend fun getHomeRec(map: Map<String, String>) =
        try {
            val homeRecEntity = service.getHomeRec(map)
            Result.Success(homeRecEntity)
        } catch (e: Exception) {
            Result.Error(IOException(e.message))
        }

    suspend fun getDiscovery() =
        try {
            val entity = service.getDiscovery()
            Result.Success(entity)
        } catch (e: Exception) {
            Result.Error(IOException(e.message))
        }


    suspend fun getConfigs(): Result<ConfigEntity> =
        try {
            val configs = service.getConfigs()
            Result.Success(configs)
        } catch (e: Exception) {
            Result.Error(IOException(e.message))
        }


    suspend fun getDaily(map: Map<String, String>) = try {
        val entity = service.getDaily(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getHot() = try {
        val entity = service.getHot()
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getTabTagList() = try {
        val entity = service.getTabTagList()
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getCategories() = try {
        val entity = service.getCategories()
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun doPreSearch(query: String) = try {
        val entity = service.doPreSearch(query)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun doSearch(query: String) = try {
        val entity = service.doSearch(query)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun doLogin(username: String, password: String) = try {
        val entity = service.doLogin(username, password)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getPushMessage(map: Map<String, String>) = try {
        val entity = service.getPushMessage(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getPrivateMsg(map: Map<String, String>) = try {
        val entity = service.getPrivateMsg(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getRecFriend(map: Map<String, String>) = try {
        val entity = service.getRecFriend(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getCommunityRec(map: Map<String, String>) = try {
        val entity = service.getCommunityRec(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getTabChild(id: Int, map: Map<String, String>) = try {
        val entity = service.getTabChild(id, map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getTopicList(map: Map<String, String>) = try {
        val entity = service.getTopicList(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getTagVideo(map: Map<String, String>) = try {
        val entity = service.getTagVideo(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getTagDynamic(map: Map<String, String>) = try {
        val entity = service.getTagDynamic(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getSpecialTopic(map: Map<String, String>) = try {
        val entity = service.getSpecialTopic(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getInformationList(map: Map<String, String>) = try {
        val entity = service.getInformationList(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getComFollow(map: Map<String, String>) = try {
        val entity = service.getComFollow(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getReplyVideoRequest(map: Map<String, String>) = try {
        val entity = service.getReplyVideoRequest(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getReplyConversation(map: Map<String, String>) = try {
        val entity = service.getReplyConversation(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getReplyHot(map: Map<String, String>) = try {
        val entity = service.getReplyHot(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getShareCount(map: Map<String, String>) = try {
        val entity = service.getShareCount(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getPage(map: Map<String, String>) = try {
        val entity = service.getPage(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getCallMetroList(map: Map<String, String>) = try {
        val entity = service.getCallMetroList(map)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getRankList(strategy: String, map: Map<String, String>) =
        try {
            val entity = service.getRankList(strategy, map)
            Result.Success(entity)
        } catch (e: Exception) {
            Result.Error(IOException(e.message))
        }

    suspend fun getTagIndex(id: String) = try {
        val entity = service.getTagIndex(id)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getTagMedals() = try {
        val entity = service.getTagMedals()
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getBrandList() = try {
        val entity = service.getBrandList()
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getVideoRelated(id: String) = try {
        val entity = service.getVideoRelated(id)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getVideoIndex(id: String) = try {
        val entity = service.getVideoIndex(id)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getLightTopicInternal(id: Int) = try {
        val entity = service.getLightTopicInternal(id)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }

    suspend fun getTopicDetailRequest(id: String) = try {
        val entity = service.getTopicDetailRequest(id)
        Result.Success(entity)
    } catch (e: Exception) {
        Result.Error(IOException(e.message))
    }


}