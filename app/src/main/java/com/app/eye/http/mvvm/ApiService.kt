package com.app.eye.http.mvvm

import com.app.eye.http.Constant
import com.app.eye.ui.mvp.model.entity.*
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {
    @GET(Constant.CONFIGS)
    suspend fun getConfigs(): ConfigEntity

    @GET(Constant.HOME_REC)
    suspend fun getHomeRec(@QueryMap map: Map<String, String>): HomeRecEntity

    @GET(Constant.DISCOVERY)
    suspend fun getDiscovery(): DiscoverEntity

    @GET(Constant.DAILY)
    suspend fun getDaily(@QueryMap map: Map<String, String>): DailyEntity

    @GET(Constant.HOT)
    suspend fun getHot(): MutableList<String>

    @GET(Constant.PRE_SEARCH)
    suspend fun doPreSearch(@Query("query") query: String): MutableList<String>

    @GET(Constant.SEARCH)
    suspend fun doSearch(@Query("query") query: String): SearchEntity

    @Headers("hostName: account")
    @POST(Constant.LOGIN)
    @FormUrlEncoded
    suspend fun doLogin(@Field("username") username: String, @Field("password") password: String)
            : LoginEntity

    @GET(Constant.MESSAGE)
    suspend fun getPushMessage(@QueryMap map: Map<String, String>): PushEntity

    @GET(Constant.PRIVATE_MESSAGE)
    suspend fun getPrivateMsg(@QueryMap map: Map<String, String>): MessageEntity

    @GET(Constant.REC_FRIEND)
    suspend fun getRecFriend(@QueryMap map: Map<String, String>): RecFriendEntity

    @GET(Constant.COM_REC)
    suspend fun getCommunityRec(@QueryMap map: Map<String, String>): ComRecEntity

    @GET(Constant.TAG_TAB)
    suspend fun getTabTagList(): TagTabEntity

    @GET("${Constant.TAG_CHILDTAB}/{id}")
    suspend fun getTabChild(
        @Path("id") id: Int,
        @QueryMap map: Map<String, String>,
    ): TabChildEntity

    @GET(Constant.TOPIC_LIST)
    suspend fun getTopicList(@QueryMap map: Map<String, String>): TopicListEntity

    @GET(Constant.TAG_INDEX)
    suspend fun getTagIndex(@Query("id") id: String): TagIndexEntity

    @GET(Constant.TAG_VIDEO)
    suspend fun getTagVideo(@QueryMap map: Map<String, String>): TagVideoEntity

    @GET(Constant.TAG_DYNAMIC)
    suspend fun getTagDynamic(@QueryMap map: Map<String, String>): TagVideoEntity

    @GET(Constant.CATEGORY)
    suspend fun getCategories(): CategoryEntity

    @GET(Constant.SPECIAL_TOPIC)
    suspend fun getSpecialTopic(@QueryMap map: Map<String, String>): SpecialTopicEntity

    @GET(Constant.RANK_LIST)
    suspend fun getRankList(
        @Query("strategy") strategy: String,
        @QueryMap map: Map<String, String>,
    ): RankEntity

    @GET(Constant.INFORMATION_LIST)
    suspend fun getInformationList(@QueryMap map: Map<String, String>): InformationEntity

    @GET(Constant.COM_ATTENTION)
    suspend fun getComFollow(@QueryMap map: Map<String, String>): ComAttentionEntity

    @GET("${Constant.LIGHT_TOPIC_INTERNAL}/{id}?${Constant.PARAMS}")
    suspend fun getLightTopicInternal(@Path("id") id: Int): LightTopicInternalEntity

    @GET(Constant.TOPIC_DETAIL)
    suspend fun getTopicDetailRequest(@Query("id") id: String): TopicDetailEntity

    @GET(Constant.REPLY_VIDEO)
    suspend fun getReplyVideoRequest(
        @QueryMap map: Map<String, String>,
    ): ReplyVideoEntity

    @GET(Constant.REPLY_CONVERSATION)
    suspend fun getReplyConversation(@QueryMap map: Map<String, String>): ReplyVideoEntity

    @GET(Constant.REPLY_HOT)
    suspend fun getReplyHot(@QueryMap map: Map<String, String>): ReplyVideoEntity

    @GET(Constant.TAG_VIDEO)
    suspend fun getShareCount(@QueryMap map: Map<String, String>): RecentPopularEntity

    @GET(Constant.VIDEO_RELATED)
    suspend fun getVideoRelated(@Query("id") id: String): VideoRelatedEntity

    @GET("${Constant.VIDEO_INDEX}/{id}?${Constant.PARAMS}")
    suspend fun getVideoIndex(@Path("id") id: String): VideoIndexEntity

    @POST(Constant.GET_PAGE)
    @FormUrlEncoded
    @Headers("hostName: recommend")
    suspend fun getPage(@FieldMap map: Map<String, String>): BrandWallEntity

    @POST(Constant.CALL_METRO)
    @FormUrlEncoded
    @Headers("hostName: recommend")
    suspend fun getCallMetroList(@FieldMap map: Map<String, String>): BrandWallEntity

    @GET(Constant.TAG_MEDALS)
    suspend fun getTagMedals(): BadgeEntity

    @Headers("hostName: recommend")
    @POST(Constant.BRAND_LIST)
    suspend fun getBrandList(): BrandListEntity

}