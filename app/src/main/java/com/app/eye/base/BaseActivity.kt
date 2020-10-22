package com.app.eye.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.eye.base.mvp.BasePresenter
import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IPresenter
import com.gyf.immersionbar.ImmersionBar
import com.orhanobut.logger.Logger
import me.yokeyword.fragmentation.SupportActivity
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity : SupportActivity() {

    val mContext: Context by lazy { this }

    val immersionBar: ImmersionBar by lazy { ImmersionBar.with(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        if (isUseEventBus()) {
            EventBus.getDefault().register(this)
        }
        init()
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isUseEventBus()) EventBus.getDefault().unregister(this)
    }

    open fun init() {}
    abstract fun getLayoutRes(): Int
    abstract fun initView()
    abstract fun initData()
    open fun isUseEventBus(): Boolean = false
}