package com.app.eye.base

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IPresenter

abstract class BaseMvpActivity<P : IPresenter<V>, V : IBaseView> : BaseActivity(), IBaseView {

    var mPresenter: P? = null

    override fun initPresenter() {
        mPresenter = createPresenter()
        mPresenter?.attach(this as V)
        lifecycle.addObserver(mPresenter!!)
    }

    abstract fun createPresenter(): P?

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            lifecycle.removeObserver(mPresenter!!)
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}