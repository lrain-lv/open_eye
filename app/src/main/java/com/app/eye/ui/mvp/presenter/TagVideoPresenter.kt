package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.TagVideoContract
import com.app.eye.ui.mvp.model.TagVideoModel

class TagVideoPresenter : BasePresenter<TagVideoContract.Model, TagVideoContract.View>(),
    TagVideoContract.Presenter {
    override fun createModel(): TagVideoContract.Model = TagVideoModel()

    override fun getTagIndexRequest(id: String) {
        val subscribe = model.getTagIndexRequest(id)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setTagIndexResponse(it)
                view?.hideLoading()
            }, {
                view?.hideLoading()
                view?.setTagIndexResponse(null)
            })
        addDisposable(subscribe)
    }

    override fun getTagVideoRequest(map: Map<String, String>) {
        val subscribe = model.getTagVideoRequest(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setTagVideoResponse(it)
                view?.hideLoading()
            }, {
                view?.hideLoading()
                view?.setTagVideoResponse(null)
            })
        addDisposable(subscribe)
    }

    override fun getTagDynamicRequest(map: Map<String, String>) {
        val subscribe = model.getTagDynamicRequest(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setTagDynamicResponse(it)
                view?.hideLoading()
            }, {
                view?.hideLoading()
                view?.setTagDynamicResponse(null)
            })
        addDisposable(subscribe)
    }
}