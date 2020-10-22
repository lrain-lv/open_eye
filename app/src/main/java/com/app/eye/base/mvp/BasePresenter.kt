package com.app.eye.base.mvp

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BasePresenter<M : IModel, V : IBaseView> : IPresenter<V> {

    var view: V? = null
    lateinit var model: M

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val isViewAttached: Boolean
        get() = view != null

    override fun attach(v: V) {
        this.view = v
        model = createModel()
    }

    override fun detach() {
        view = null
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    abstract fun createModel(): M
}