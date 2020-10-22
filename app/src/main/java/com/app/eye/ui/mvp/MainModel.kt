package com.app.eye.ui.mvp

import com.app.eye.http.RetrofitManager
import com.app.eye.http.ApiService
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.core.Observable
import okhttp3.ResponseBody

class MainModel : MainContract.Model {

    override fun getData(): Observable<ResponseBody> {
        Logger.e("ddd")
        var map = mapOf<String, String>("udid" to "7a5bd452383b40a1804972422eef008361cf87b8")
        return RetrofitManager.sInstance.createService(ApiService::class.java).getPage(map)
    }
}