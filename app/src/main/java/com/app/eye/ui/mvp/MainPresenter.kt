package com.app.eye.ui.mvp

import android.util.Log
import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(),
    MainContract.Presenter {
    override fun createModel(): MainContract.Model = MainModel()

    override fun getData() {
        val subscribe = model.getData()
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ view?.getResponse(it.string()) }, { Logger.e(it.message!!) })
        addDisposable(subscribe)
    }

}