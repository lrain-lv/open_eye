package com.app.eye.rx.scheduler

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.schedulers.Schedulers
import org.reactivestreams.Publisher

open class BaseScheduler<T> constructor(var subscribeOn: Scheduler, var observeOn: Scheduler) :
    ObservableTransformer<T, T>, SingleTransformer<T, T>, FlowableTransformer<T, T> {
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(subscribeOn)
            .observeOn(observeOn)
    }

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.subscribeOn(subscribeOn)
            .observeOn(observeOn)
    }

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.subscribeOn(subscribeOn)
            .observeOn(observeOn)
    }

}