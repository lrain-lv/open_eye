package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.HomeRecContract
import com.app.eye.ui.mvp.model.HomeRecModel

class HomeRecPresenter : BasePresenter<HomeRecContract.Model, HomeRecContract.View>(),
    HomeRecContract.Presenter {
    override fun createModel(): HomeRecContract.Model = HomeRecModel()
    override fun getRecData(map: Map<String, String>) {
        val subscribe = model.getRecData(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.hideLoading()
                view?.setResponse(it)
            }, { view?.hideLoading() })
        addDisposable(subscribe)
    }


}