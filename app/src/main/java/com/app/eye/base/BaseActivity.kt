package com.app.eye.base

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.event.NetworkEvent
import com.app.eye.receiver.NetworkChangeReceiver
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.gyf.immersionbar.ImmersionBar
import me.yokeyword.fragmentation.SupportActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity : SupportActivity() {

    val mContext: Context by lazy { this }

    val immersionBar: ImmersionBar by lazy { ImmersionBar.with(this) }

    private val receiver = NetworkChangeReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityUtils.addActivityLifecycleCallbacks(this, Utils.ActivityLifecycleCallbacks())
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

    fun initToolBar(bar: Toolbar) {
        bar.setNavigationOnClickListener {
            onBackPressedSupport()
        }
    }

    open fun initSwipeRefreshLayout(refreshLayout: SwipeRefreshLayout) {
        val intArray = resources.getIntArray(R.array.google_colors)
        refreshLayout.setColorSchemeColors(*intArray)
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
        ActivityUtils.removeActivityLifecycleCallbacks(this)
    }

    open fun init() {}
    abstract fun getLayoutRes(): Int
    abstract fun initView()
    abstract fun initData()
    open fun reConnect() {}
    open fun isUseEventBus(): Boolean = false


}