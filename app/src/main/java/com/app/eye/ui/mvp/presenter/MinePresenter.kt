package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.MineContract
import com.app.eye.ui.mvp.model.MineModel


class MinePresenter : BasePresenter<MineContract.Model, MineContract.View>(),
    MineContract.Presenter {
    override fun createModel(): MineContract.Model {
        return MineModel()
    }

    override fun getTagMedals() {
        val subscribe = model.getTagMedals()
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setMedalsData(it)
                view?.hideLoading()
            }, {
                view?.setMedalsData(null)
                view?.hideLoading()
            })
        addDisposable(subscribe)
    }
}