package com.app.eye.http.interceptor

import com.app.eye.http.Constant
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class MultiUrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val oldUrl = originalRequest.url()
        val builder = originalRequest.newBuilder()
        val headers: MutableList<String>? = originalRequest.headers("hostName")
        if (headers?.isNotEmpty()!!) {
            builder.removeHeader("hostName")
            val baseUrl = when (headers[0]) {
                "recommend" -> {
                    HttpUrl.parse(Constant.RECOMMEND_BASE_URL)!!
                }
                "account" -> {
                    HttpUrl.parse(Constant.ACCOUNT_BASE_URL)!!
                }
                else -> {
                    oldUrl
                }
            }

            val newUrl = oldUrl.newBuilder()
                .scheme(baseUrl.scheme())
                .host(baseUrl.host())
                .port(baseUrl.port())
                .build()
            builder.addHeader("x-thefair-appid", Constant.X_THEFAIR_APPID)
                .addHeader(
                    "x-thefair-auth",
                    Constant.X_THEFAIR_AUTH
                )
                .addHeader("x-thefair-cid", Constant.X_THEFAIR_CID)
                .addHeader(
                    "x-thefair-ua",
                    Constant.X_THEFAIR_UA
                )
                .addHeader("x-api-key", Constant.x_api_key)
                .addHeader(
                    "User-Agent", Constant.X_THEFAIR_UA
                )

            val newRequest = builder.url(newUrl).build()
            return chain.proceed(newRequest)
        } else {
            return chain.proceed(originalRequest)
        }
    }
}