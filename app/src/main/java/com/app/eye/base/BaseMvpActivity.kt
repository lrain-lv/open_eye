package com.app.eye.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.app.eye.base.mvp.BasePresenter
import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.orhanobut.logger.Logger

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
}