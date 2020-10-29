package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.RankContract
import com.app.eye.ui.mvp.model.RankModel

class RankPresenter : BasePresenter<RankContract.Model, RankContract.View>(),
    RankContract.Presenter {
    override fun createModel(): RankContract.Model = RankModel()

    override fun getRankRequest(type: Int,map:Map<String,String>) {
        val subscribe = model.getRankRequest(type,map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setRankResponse(type, it)
                view?.hideLoading()
            }, {
                view?.setRankResponse(type, null)
                view?.hideLoading()
            })
        addDisposable(subscribe)
    }
}