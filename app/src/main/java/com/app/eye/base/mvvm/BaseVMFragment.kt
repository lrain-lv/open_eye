package com.app.eye.base.mvvm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.BaseFragment
import com.app.eye.event.NetworkEvent
import com.app.eye.receiver.NetworkChangeReceiver
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.immersionbar.ImmersionBar
import me.yokeyword.fragmentation.SupportFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseVMFragment : SupportFragment() {

    lateinit var mContext: Context

    var mRootView: View? = null

    val immersionBar: ImmersionBar by lazy { ImmersionBar.with(this) }
    val receiver = NetworkChangeReceiver()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (isUseEventBus()) {
            EventBus.getDefault().register(this)
        }
        mRootView = inflater.inflate(getLayoutRes(), container, false)
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!useLazyLoad()) {
            initView()
            startObserver()
            initData()

        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if (useLazyLoad()) {
            initView()
            startObserver()
            initData()

        }
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

    open fun initSwipeRefreshLayout(refreshLayout: SwipeRefreshLayout) {
        val intArray = resources.getIntArray(R.array.google_colors)
        refreshLayout.setColorSchemeColors(*intArray)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isUseEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }


    abstract fun getLayoutRes(): Int
    abstract fun startObserver()
    abstract fun initView()
    abstract fun initData()
    open fun showLoading() {}
    open fun hideLoading() {}

    open fun useLazyLoad(): Boolean = true
    open fun isUseEventBus(): Boolean = false

    open fun init() {}
    open fun reConnect() {}


}