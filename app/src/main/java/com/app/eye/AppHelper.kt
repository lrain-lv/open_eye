package com.app.eye

import android.app.Application
import android.content.Context

object AppHelper {
    lateinit var mContext: Context

    fun init(context: Context) {
        this.mContext = context
    }
}