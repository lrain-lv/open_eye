package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.DailyContract
import com.app.eye.ui.mvp.model.DailyModel
import com.app.eye.ui.mvp.model.entity.DailyEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class DailyPresenter : BasePresenter<DailyContract.Model, DailyContract.View>(),
    DailyContract.Presenter {
    override fun createModel(): DailyContract.Model = DailyModel()

    override fun getDailyData(isRefresh: Boolean, map: Map<String, String>) {
        val subscribe = model.getDailyData(isRefresh, map)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { view?.showLoading() }
            .subscribeOn(AndroidSchedulers.mainThread())
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.hideLoading()
                view?.setDailyEntity(it)
            }, {
                view?.hideLoading()
                view?.setDailyEntity(null)
            })
        addDisposable(subscribe)
    }
}