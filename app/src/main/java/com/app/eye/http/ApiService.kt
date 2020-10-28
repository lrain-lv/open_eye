package com.app.eye.http

import com.app.eye.ui.mvp.model.entity.*
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {
    @GET(Constant.CONFIGS)
    fun getConfigs(@Query("size") size: String): Observable<ResponseBody>

    @Headers("hostName: recommend")
    @POST(Constant.GET_PAGE)
    @FormUrlEncoded
    fun getPage(@FieldMap map: Map<String, String>): Observable<ResponseBody>

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

    @GET(Constant.COM_REC)
    fun getCommunityRec(@QueryMap map: Map<String, String>): Observable<ComRecEntity>

    @GET(Constant.TAG_TAB)
    fun getTabTagList(): Observable<TagTabEntity>

    @GET("${Constant.TAG_CHILDTAB}/{id}")
    fun getTabChild(
        @Path("id") id: Int,
        @QueryMap map: Map<String, String>
    ): Observable<TabChildEntity>

    @GET(Constant.TOPIC_LIST)
    fun getTopicList(@QueryMap map: Map<String, String>): Observable<TopicListEntity>

    @GET(Constant.TAG_INDEX)
    fun getTagIndex(@Query("id") id: String): Observable<TagIndexEntity>

    @GET(Constant.TAG_VIDEO)
    fun getTagVideo(@QueryMap map: Map<String, String>): Observable<TagVideoEntity>
}