package com.app.eye.base

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IPresenter

abstract class BaseMvpActivity<P : IPresenter<V>, V : IBaseView> : BaseActivity(), IBaseView {

    var mPresenter: P? = null

    override fun init() {
        mPresenter = createPresenter()
        mPresenter?.attach(this as V)
    }

    abstract fun createPresenter(): P?

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detach()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}