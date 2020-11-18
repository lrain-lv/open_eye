package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.BrandWallContract
import com.app.eye.ui.mvp.model.BrandWallModel
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.core.Observable

class BrandWallPresenter : BasePresenter<BrandWallContract.Model, BrandWallContract.View>(),
    BrandWallContract.Presenter {
    override fun createModel(): BrandWallContract.Model = BrandWallModel()

    override fun getRequest(map: Map<String, String>, isRefresh: Boolean) {
        var subscribe = model.getRequest(map, isRefresh)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setBrandWallData(it)
                view?.hideLoading()
            }, {
                Logger.e(it.message!!)
                view?.setBrandWallData(null)
                view?.hideLoading()
            })
        addDisposable(subscribe)
    }
}