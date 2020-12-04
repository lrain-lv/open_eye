package com.app.eye.http

import com.app.eye.AppHelper
import com.app.eye.http.cookie.PersistentCookieJar
import com.app.eye.http.cookie.SharedPrefsCookiePersistor
import com.app.eye.http.cookie.cache.SetCookieCache
import com.app.eye.http.interceptor.LoggingInterceptor
import com.app.eye.http.interceptor.MultiUrlInterceptor
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class RetrofitManager {

    private object RetrofitManagerHolder {
        val sInstance = RetrofitManager()
    }

    init {
        initOkHttpClient()
        initRetrofit()
    }

    lateinit var retrofit: Retrofit
    lateinit var client: OkHttpClient

    companion object {
        @JvmStatic
        val sInstance: RetrofitManager by lazy {
            RetrofitManagerHolder.sInstance
        }

//        val service: ApiService by lazy {
//            sInstance.createService(ApiService::class.java)
//        }

    }

    fun <T> createService(clz: Class<T>): T {
        return retrofit.create(clz)
    }

    private fun initRetrofit() {
        retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(Constant.COMMON_BASE_URL)
            .client(client)
            .build()
    }

    private fun initOkHttpClient() {

        val cookie =
            PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(AppHelper.mContext))
        val cacheFile = File(AppHelper.mContext.cacheDir, "eyeCache")
        val cache = Cache(cacheFile, 1024 * 1024 * 500)
        client = OkHttpClient.Builder()
            .cache(cache)
            .cookieJar(cookie)
            .readTimeout(20000, TimeUnit.MILLISECONDS)
            .writeTimeout(20000, TimeUnit.MILLISECONDS)
            .connectTimeout(15000, TimeUnit.MILLISECONDS)
            .addInterceptor(LoggingInterceptor())
            .addNetworkInterceptor(LoggingInterceptor())
            .addInterceptor(MultiUrlInterceptor())
            .build()
    }
}