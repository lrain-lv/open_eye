package com.app.eye.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IPresenter
import me.yokeyword.fragmentation.SupportFragment

abstract class BaseMvpFragment<P : IPresenter<V>, V : IBaseView> : BaseFragment(), IBaseView {

    var mPresenter: P? = null

    override fun init() {
        super.init()
        mPresenter = createPresenter()
        mPresenter?.attach(this as V)
    }

    abstract fun createPresenter(): P?

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detach()
    }
}