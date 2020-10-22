package com.app.eye.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eye.R
import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IPresenter
import com.gyf.immersionbar.ImmersionBar
import me.yokeyword.fragmentation.SupportFragment

abstract class BaseFragment : SupportFragment() {

    lateinit var mContext: Context

    lateinit var mRootView: View

    val immersionBar: ImmersionBar by lazy { ImmersionBar.with(this) }

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
        mRootView = inflater.inflate(getLayoutRes(), container, false)
        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!useLazyLoad()) {
            initView()
            initData()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if (useLazyLoad()) {
            initView()
            initData()
        }
    }

    open fun initSwipeRefreshLayout(refreshLayout: SwipeRefreshLayout) {
        val intArray = resources.getIntArray(R.array.google_colors)
        refreshLayout.setColorSchemeColors(*intArray)
    }


    abstract fun getLayoutRes(): Int

    open fun useLazyLoad(): Boolean = false
    open fun init() {}

    abstract fun initView()
    abstract fun initData()
}