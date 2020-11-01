package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.TopicDetailContract
import com.app.eye.ui.mvp.model.TopicDetailModel
import com.orhanobut.logger.Logger

class TopicDetailPresenter : BasePresenter<TopicDetailContract.Model, TopicDetailContract.View>(),
    TopicDetailContract.Presenter {
    override fun createModel(): TopicDetailContract.Model = TopicDetailModel()

    override fun getTopicDetailRequest(id: String) {
        val subscribe = model.getTopicDetailRequest(id)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setTopicDetailResponse(it)
                view?.hideLoading()
            }, {
                view?.setTopicDetailResponse(null)
            })
        addDisposable(subscribe)
    }

    override fun getReplyVideoRequest(map: Map<String, String>) {
        val subscribe = model.getReplyVideoRequest(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setReplyVideoResponse(it)
                view?.hideLoading()
            }, {
                view?.hideLoading()
                view?.setReplyVideoResponse(null)
            })
        addDisposable(subscribe)
    }

    override fun getReplyConversationRequest(map: Map<String, String>) {
        val subscribe = model.getReplyConversationRequest(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setReplyConversationResponse(it)
                view?.hideLoading()
            }, {
                view?.hideLoading()
                view?.setReplyConversationResponse(null)
            })
        addDisposable(subscribe)
    }

    override fun getReplyHotRequest(map: Map<String, String>) {
        val subscribe = model.getReplyHotRequest(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setReplyHotResponse(it)
                view?.hideLoading()
            }, {
                view?.hideLoading()
                view?.setReplyHotResponse(null)
            })
        addDisposable(subscribe)
    }
}