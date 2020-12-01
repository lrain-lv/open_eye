package com.app.eye.http

import com.app.eye.ui.entity.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ApiService {
    @GET(Constant.CONFIGS)
    fun getConfigs(): Observable<ConfigEntity>

    @GET(Constant.HOME_REC)
    fun getHomeRec(@QueryMap map: Map<String, String>): Observable<HomeRecEntity>

    @GET(Constant.DISCOVERY)
    fun getDiscovery(): Observable<DiscoverEntity>

    @GET(Constant.DAILY)
    fun getDaily(@QueryMap map: Map<String, String>): Observable<DailyEntity>

    @GET(Constant.HOT)
    fun getHot(): Observable<MutableList<String>>

    @GET(Constant.PRE_SEARCH)
    fun doPreSearch(@Query("query") query: String): Observable<MutableList<String>>

    @GET(Constant.SEARCH)
    fun doSearch(@Query("query") query: String): Observable<SearchEntity>

    @Headers("hostName: account")
    @POST(Constant.LOGIN)
    @FormUrlEncoded
    fun doLogin(@Field("username") username: String, @Field("password") password: String)
            : Observable<LoginEntity>

    @GET(Constant.MESSAGE)
    fun getPushMessage(@QueryMap map: Map<String, String>): Observable<PushEntity>

    @GET(Constant.PRIVATE_MESSAGE)
    fun getPrivateMsg(@QueryMap map: Map<String, String>): Observable<MessageEntity>

    @GET(Constant.REC_FRIEND)
    fun getRecFriend(@QueryMap map: Map<String, String>): Observable<RecFriendEntity>

    @GET(Constant.COM_REC)
    fun getCommunityRec(@QueryMap map: Map<String, String>): Observable<ComRecEntity>

    @GET(Constant.TAG_TAB)
    fun getTabTagList(): Observable<TagTabEntity>

    @GET("${Constant.TAG_CHILDTAB}/{id}")
    fun getTabChild(
        @Path("id") id: Int,
        @QueryMap map: Map<String, String>,
    ): Observable<TabChildEntity>

    @GET(Constant.TOPIC_LIST)
    fun getTopicList(@QueryMap map: Map<String, String>): Observable<TopicListEntity>

    @GET(Constant.TAG_INDEX)
    fun getTagIndex(@Query("id") id: String): Observable<TagIndexEntity>

    @GET(Constant.TAG_VIDEO)
    fun getTagVideo(@QueryMap map: Map<String, String>): Observable<TagVideoEntity>

    @GET(Constant.TAG_DYNAMIC)
    fun getTagDynamic(@QueryMap map: Map<String, String>): Observable<TagVideoEntity>

    @GET(Constant.CATEGORY)
    fun getCategories(): Observable<CategoryEntity>

    @GET(Constant.SPECIAL_TOPIC)
    fun getSpecialTopic(@QueryMap map: Map<String, String>): Observable<SpecialTopicEntity>

    @GET(Constant.RANK_LIST)
    fun getRankList(
        @Query("strategy") strategy: String,
        @QueryMap map: Map<String, String>,
    ): Observable<RankEntity>

    @GET(Constant.INFORMATION_LIST)
    fun getInformationList(@QueryMap map: Map<String, String>): Observable<InformationEntity>

    @GET(Constant.COM_ATTENTION)
    fun getComFollow(@QueryMap map: Map<String, String>): Observable<ComAttentionEntity>

    @GET("${Constant.LIGHT_TOPIC_INTERNAL}/{id}?${Constant.PARAMS}")
    fun getLightTopicInternal(@Path("id") id: Int): Observable<LightTopicInternalEntity>

    @GET(Constant.TOPIC_DETAIL)
    fun getTopicDetailRequest(@Query("id") id: String): Observable<TopicDetailEntity>

    @GET(Constant.REPLY_VIDEO)
    fun getReplyVideoRequest(
        @QueryMap map: Map<String, String>,
    ): Observable<ReplyVideoEntity>

    @GET(Constant.REPLY_CONVERSATION)
    fun getReplyConversation(@QueryMap map: Map<String, String>): Observable<ReplyVideoEntity>

    @GET(Constant.REPLY_HOT)
    fun getReplyHot(@QueryMap map: Map<String, String>): Observable<ReplyVideoEntity>

    @GET(Constant.TAG_VIDEO)
    fun getShareCount(@QueryMap map: Map<String, String>): Observable<RecentPopularEntity>

    @GET(Constant.VIDEO_RELATED)
    fun getVideoRelated(@Query("id") id: String): Observable<VideoRelatedEntity>

    @GET("${Constant.VIDEO_INDEX}/{id}?${Constant.PARAMS}")
    fun getVideoIndex(@Path("id") id: String): Observable<VideoIndexEntity>

    @POST(Constant.GET_PAGE)
    @FormUrlEncoded
    @Headers("hostName: recommend")
    fun getPage(@FieldMap map: Map<String, String>): Observable<BrandWallEntity>

    @POST(Constant.CALL_METRO)
    @FormUrlEncoded
    @Headers("hostName: recommend")
    fun getCallMetroList(@FieldMap map: Map<String, String>): Observable<BrandWallEntity>

    @GET(Constant.TAG_MEDALS)
    fun getTagMedals(): Observable<BadgeEntity>

    @Headers("hostName: recommend")
    @POST(Constant.BRAND_LIST)
    fun getBrandList(): Observable<BrandListEntity>

}