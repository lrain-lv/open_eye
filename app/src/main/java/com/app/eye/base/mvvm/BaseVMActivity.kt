package com.app.eye.base.mvvm

import android.os.Bundle
import com.app.eye.base.BaseActivity

abstract class BaseVMActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserver()
    }
    abstract fun startObserver()
}