package com.app.eye.base

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IPresenter

abstract class BaseMvpFragment<P : IPresenter<V>, V : IBaseView> : BaseFragment(), IBaseView {

    var mPresenter: P? = null

    override fun init() {
        super.init()
        mPresenter = createPresenter()
        mPresenter?.attach(this as V)
    }

    override fun showLoading() {
    }
    abstract fun createPresenter(): P?

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detach()
    }
}