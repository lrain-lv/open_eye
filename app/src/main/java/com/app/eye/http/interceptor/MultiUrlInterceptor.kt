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
            builder.addHeader("X-THEFAIR-APPID", "ahpagrcrf2p7m6rg")
                .addHeader(
                    "X-THEFAIR-AUTH",
                    "GNDG32JRfMGTxw/W6tBWtk+K577rigUWd73muNoT6alLkiu7XEoGfqeyJFndxKFGO5wIHJLUawMGxg6uWYR6dC7auVfSZsM86u94o4ETAmQZ3WTWo28+6KXhL/Uj5FP3ZiZmNskoqRz2jwHjQncP9nM2XB2jbZUbL9YTZ6Se3FpSxJlkC824c3bYUSQ07jTZ6QXpGdc0g+p948/trkFAmCQuSuCZP5y5Z3QpG1qeGdPPHuusGea7BsOf4/aO9r+FL5Y05RtfE+DAYNsajfjBFQ=="
                )
                .addHeader("X-THEFAIR-CID", "c440c5ef4cc919d27fdb74e0c382d955")
                .addHeader(
                    "X-THEFAIR-UA",
                    "EYEPETIZER/6030101 (ALP-AL00;android;10;zh_CN_#Hans;android;6.3.10;cn-bj;huawei;c440c5ef4cc919d27fdb74e0c382d955;WIFI;1080*1920) native/1.0"
                )
                .addHeader("x-api-key", "0530ee4341324ce2b26c23fcece80ea2")
                .addHeader(
                    "User-Agent",
                    "EYEPETIZER/6030101 (ALP-AL00;android;10;zh_CN_#Hans;android;6.3.10;cn-bj;huawei;c440c5ef4cc919d27fdb74e0c382d955;WIFI;1080*1920) native/1.0"
                )
                .addHeader(
                    "Cookie",
                    "ky_auth=_V1MTU3NTEzMjAxOTU6MTYxMTc0MjEyODkxMTo0ZmE0ZmEwZGFmOGQ5MDZiNmUwMGYzOTIyNDE2Yjg1OA; expires=Wed, 27-Jan-2021 06:08:48 GMT; path=/account-api; domain=account.kaiyanapp.com; HttpOnly"
                )

            val newRequest = builder.url(newUrl).build()
            return chain.proceed(newRequest)
        } else {
            return chain.proceed(originalRequest)
        }
    }
}