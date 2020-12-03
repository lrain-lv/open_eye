package com.app.eye

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.app.eye.widgets.EyeLoadMoreView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import leakcanary.LeakCanary


class MainApp : Application() {

    companion object {
        @JvmStatic
        lateinit var mContext: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
    }

    override fun onLowMemory() {
        super.onLowMemory()
        val imageLoader = Coil.imageLoader(AppHelper.mContext)
        imageLoader.memoryCache.clear()
        imageLoader.bitmapPool.clear()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        val imageLoader = Coil.imageLoader(AppHelper.mContext)
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            imageLoader.memoryCache.clear()
            imageLoader.bitmapPool.clear()
        }
    }


}