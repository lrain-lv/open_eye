package com.app.eye.base.mvp

interface IPresenter< V : IBaseView> {
    fun attach(view: V)

    fun detach()
}