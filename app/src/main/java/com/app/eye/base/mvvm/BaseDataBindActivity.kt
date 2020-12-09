package com.app.eye.base.mvvm

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.Utils
import com.gyf.immersionbar.ImmersionBar
import me.yokeyword.fragmentation.SupportActivity
import org.greenrobot.eventbus.EventBus

abstract class BaseDataBindActivity : SupportActivity() {


    protected inline fun <reified T : ViewDataBinding> binding(
        @LayoutRes resId: Int
    ): Lazy<T> = lazy {
        DataBindingUtil.setContentView<T>(this, resId).apply {
            lifecycleOwner = this@BaseDataBindActivity
        }
    }

    val mContext: Context by lazy { this }

    val immersionBar: ImmersionBar by lazy { ImmersionBar.with(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityUtils.addActivityLifecycleCallbacks(this, Utils.ActivityLifecycleCallbacks())
        if (isUseEventBus()) {
            EventBus.getDefault().register(this)
        }
        initView()
        initData()
        startObserver()
    }

    open fun initSwipeRefreshLayout(refreshLayout: SwipeRefreshLayout) {
        val intArray = resources.getIntArray(R.array.google_colors)
        refreshLayout.setColorSchemeColors(*intArray)
    }

    override fun onDestroy() {
        KeyboardUtils.fixAndroidBug5497(this)
        KeyboardUtils.fixSoftInputLeaks(this)
        super.onDestroy()
        if (isUseEventBus()) EventBus.getDefault().unregister(this)
        ActivityUtils.removeActivityLifecycleCallbacks(this)
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun startObserver()
    open fun isUseEventBus(): Boolean = false
}