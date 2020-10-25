package com.app.eye.base

import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.eye.base.mvp.BasePresenter
import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IPresenter
import com.app.eye.event.NetworkEvent
import com.app.eye.receiver.NetworkChangeReceiver
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import com.orhanobut.logger.Logger
import me.yokeyword.fragmentation.SupportActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.prefs.Preferences

abstract class BaseActivity : SupportActivity() {

    val mContext: Context by lazy { this }

    val immersionBar: ImmersionBar by lazy { ImmersionBar.with(this) }

    val receiver = NetworkChangeReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(receiver, filter)
        if (isUseEventBus()) {
            EventBus.getDefault().register(this)
        }
        init()
        initView()
        initData()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onNetworkChangeEvent(event: NetworkEvent) {
        SPUtils.getInstance("eye").put("isLastConnect", event.isConnect)
        if (event.isConnect) {
            reConnect()
        } else {
            ToastUtils.showShort("当前无网络！")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        if (isUseEventBus()) EventBus.getDefault().unregister(this)
    }

    open fun init() {}
    abstract fun getLayoutRes(): Int
    abstract fun initView()
    abstract fun initData()
    abstract fun reConnect()
    open fun isUseEventBus(): Boolean = false
}