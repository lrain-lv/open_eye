package com.app.eye

import android.app.Application
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
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}