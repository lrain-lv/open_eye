package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.PopularContract
import com.app.eye.ui.mvp.model.PopularModel

class PopularPresenter : BasePresenter<PopularContract.Model, PopularContract.View>(),
    PopularContract.Presenter {
    override fun createModel(): PopularContract.Model = PopularModel()

    override fun getRecentShareCount(map: Map<String, String>) {
        val subscribe = model.getRecentShareCount(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setRecentShareCountResponse(it)
                view?.hideLoading()
            }, {
                view?.setRecentShareCountResponse(null)
                view?.hideLoading()
            })
        addDisposable(subscribe)
    }

}