package com.app.eye.ui

import android.content.Context
import androidx.startup.Initializer
import coil.Coil
import coil.ImageLoader
import com.app.eye.AppHelper
import com.app.eye.http.RetrofitManager
import com.app.eye.widgets.EyeLoadMoreView
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class AppInitializer() : Initializer<Unit> {
    override fun create(context: Context) {
        AppHelper.init(context = context)
        LoadMoreModuleConfig.defLoadMoreView = EyeLoadMoreView()
        Logger.addLogAdapter(AndroidLogAdapter())
        val builder = ImageLoader.Builder(context)
            .crossfade(true)
            .allowRgb565(true)
            .okHttpClient(RetrofitManager.sInstance.client)
            .build()
        Coil.setImageLoader(builder)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}