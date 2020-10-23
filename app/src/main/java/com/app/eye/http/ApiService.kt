package com.app.eye.http

import com.app.eye.http.Constant
import com.app.eye.ui.mvp.model.entity.DailyEntity
import com.app.eye.ui.mvp.model.entity.DiscoverEntity
import com.app.eye.ui.mvp.model.entity.HotSearchEntity
import io.reactivex.rxjava3.core.Observable
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.*
import java.util.*

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
    fun getHot(): Observable<List<String>>
}