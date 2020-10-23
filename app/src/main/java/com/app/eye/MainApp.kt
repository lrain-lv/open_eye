package com.app.eye

import android.app.Application
import com.app.eye.widgets.EyeLoadMoreView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class MainApp : Application() {

    companion object {
        @JvmStatic
        lateinit var mContext: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        LoadMoreModuleConfig.defLoadMoreView = EyeLoadMoreView()
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory()
        }
        Glide.get(this).onTrimMemory(level)
    }


}