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
import com.bumptech.glide.Glide
import com.gyf.immersionbar.ImmersionBar
import me.yokeyword.fragmentation.SupportFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseDataBindFragment<T : ViewDataBinding> : SupportFragment() {

    lateinit var mContext: Context

    var mRootView: View? = null

    lateinit var binding: T


    protected fun <T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes layoutId: Int,
        container: ViewGroup?
    ): T = DataBindingUtil.inflate<T>(inflater, layoutId, container, false).apply {
        lifecycleOwner = this@BaseDataBindFragment
    }

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
        binding = binding<T>(inflater, getLayoutRes(), container)
        mRootView = binding.root
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!useLazyLoad()) {
            initView()
            initData()
            startObserver()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if (useLazyLoad()) {
            initView()
            initData()
            startObserver()
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