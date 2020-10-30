package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.LightTopicContract
import com.app.eye.ui.mvp.model.LightTopicModel

class LightTopicPresenter : BasePresenter<LightTopicContract.Model, LightTopicContract.View>(),
    LightTopicContract.Presenter {
    override fun createModel(): LightTopicContract.Model = LightTopicModel()

    override fun getLightTopicRequest(id: Int) {
        val subscribe = model.getLightTopicRequest(id)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setLightTopicResponse(it)
                view?.hideLoading()
            }, {
                view?.setLightTopicResponse(null)
                view?.hideLoading()
            })
        addDisposable(subscribe)
    }
}