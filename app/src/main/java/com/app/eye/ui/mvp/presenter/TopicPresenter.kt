package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.TopicContact
import com.app.eye.ui.mvp.model.TopicModel
import com.blankj.utilcode.util.ToastUtils

class TopicPresenter : BasePresenter<TopicContact.Model, TopicContact.View>(),
    TopicContact.Presenter {
    override fun createModel(): TopicContact.Model = TopicModel()

    override fun getTabRequest() {
        val subscribe = model.getTabRequest()
            .compose(SchedulerUtils.ioToMain())
            .subscribe({ view?.setTabResponse(it) }, {
                view?.setTabResponse(null)
                ToastUtils.showShort(it.message)
            })
        addDisposable(subscribe)
    }

    override fun getTabChildRequest(id: Int, map: Map<String, String>) {
        val subscribe = model.getTabChildRequest(id, map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setTabChildResponse(it)
                view?.hideLoading()
            }, {
                view?.setTabChildResponse(null)
                view?.hideLoading()
            })
        addDisposable(subscribe)
    }

    override fun getTopicListRequest(map: Map<String, String>) {
        val subscribe = model.getTopicListRequest(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setTopicListResponse(it)
                view?.hideLoading()
            }, {
                view?.setTopicListResponse(null)
                view?.hideLoading()
            })
        addDisposable(subscribe)
    }

}