package com.app.eye.base.mvp

import androidx.lifecycle.LifecycleObserver

interface IPresenter<V : IBaseView> : LifecycleObserver {
    fun attach(view: V)

    fun detach()
}