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
            EyeResult.Success(homeRecEntity)
        } catch (e: Exception) {
            EyeResult.Error(IOException(e.message))
        }

    suspend fun getDiscovery() =
        try {
            val entity = service.getDiscovery()
            EyeResult.Success(entity)
        } catch (e: Exception) {
            EyeResult.Error(IOException(e.message))
        }


    suspend fun getConfigs(): EyeResult<ConfigEntity> =
        try {
            val configs = service.getConfigs()
            EyeResult.Success(configs)
        } catch (e: Exception) {
            EyeResult.Error(IOException(e.message))
        }


    suspend fun getDaily(map: Map<String, String>) = try {
        val entity = service.getDaily(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getHot() = try {
        val entity = service.getHot()
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getTabTagList() = try {
        val entity = service.getTabTagList()
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getCategories() = try {
        val entity = service.getCategories()
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun doPreSearch(query: String) = try {
        val entity = service.doPreSearch(query)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun doSearch(query: String) = try {
        val entity = service.doSearch(query)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun doLogin(username: String, password: String) = try {
        val entity = service.doLogin(username, password)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getPushMessage(map: Map<String, String>) = try {
        val entity = service.getPushMessage(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getPrivateMsg(map: Map<String, String>) = try {
        val entity = service.getPrivateMsg(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getRecFriend(map: Map<String, String>) = try {
        val entity = service.getRecFriend(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getCommunityRec(map: Map<String, String>) = try {
        val entity = service.getCommunityRec(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getTabChild(id: Int, map: Map<String, String>) = try {
        val entity = service.getTabChild(id, map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getTopicList(map: Map<String, String>) = try {
        val entity = service.getTopicList(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getTagVideo(map: Map<String, String>) = try {
        val entity = service.getTagVideo(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getTagDynamic(map: Map<String, String>) = try {
        val entity = service.getTagDynamic(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getSpecialTopic(map: Map<String, String>) = try {
        val entity = service.getSpecialTopic(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getInformationList(map: Map<String, String>) = try {
        val entity = service.getInformationList(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getComFollow(map: Map<String, String>) = try {
        val entity = service.getComFollow(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getReplyVideoRequest(map: Map<String, String>) = try {
        val entity = service.getReplyVideoRequest(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getReplyConversation(map: Map<String, String>) = try {
        val entity = service.getReplyConversation(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getReplyHot(map: Map<String, String>) = try {
        val entity = service.getReplyHot(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getShareCount(map: Map<String, String>) = try {
        val entity = service.getShareCount(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getPage(map: Map<String, String>) = try {
        val entity = service.getPage(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getCallMetroList(map: Map<String, String>) = try {
        val entity = service.getCallMetroList(map)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getRankList(strategy: String, map: Map<String, String>) =
        try {
            val entity = service.getRankList(strategy, map)
            EyeResult.Success(entity)
        } catch (e: Exception) {
            EyeResult.Error(IOException(e.message))
        }

    suspend fun getTagIndex(id: String) = try {
        val entity = service.getTagIndex(id)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getTagMedals() = try {
        val entity = service.getTagMedals()
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getBrandList() = try {
        val entity = service.getBrandList()
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getVideoRelated(id: String) = try {
        val entity = service.getVideoRelated(id)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getVideoIndex(id: String) = try {
        val entity = service.getVideoIndex(id)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getLightTopicInternal(id: Int) = try {
        val entity = service.getLightTopicInternal(id)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }

    suspend fun getTopicDetailRequest(id: String) = try {
        val entity = service.getTopicDetailRequest(id)
        EyeResult.Success(entity)
    } catch (e: Exception) {
        EyeResult.Error(IOException(e.message))
    }


}