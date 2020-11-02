package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.VideoDetailContract
import com.app.eye.ui.mvp.model.VideoDetailModel
import com.orhanobut.logger.Logger

class VideoDetailPresenter : BasePresenter<VideoDetailContract.Model, VideoDetailContract.View>(),
    VideoDetailContract.Presenter {
    override fun createModel(): VideoDetailContract.Model = VideoDetailModel()

    override fun getVideoDetailRequest(id: String) {
        val subscribe = model.getVideoDetailRequest(id)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setVideoDetailResponse(it)
                view?.hideLoading()
            }, {
                Logger.e("-----------${it.message.toString()}")
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