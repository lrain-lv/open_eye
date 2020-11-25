package com.app.eye.base.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BasePresenter<M : IModel, V : IBaseView> : IPresenter<V> {

    var view: V? = null
    lateinit var model: M

    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    private val isViewAttached: Boolean
        get() = view != null

    override fun attach(view: V) {
        this.view = view
        model = createModel()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun detach() {
        if (!compositeDisposable?.isDisposed!!) {
            compositeDisposable?.clear()
            compositeDisposable = null
        }
        view = null
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable?.add(disposable)
    }

    abstract fun createModel(): M
}