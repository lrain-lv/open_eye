package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.PushContract
import com.app.eye.ui.mvp.model.PushModel
import com.blankj.utilcode.util.ToastUtils

class PushPresenter : BasePresenter<PushContract.Model, PushContract.View>(),
    PushContract.Presenter {
    override fun createModel(): PushContract.Model = PushModel()

    override fun getPushRequest(map: Map<String, String>) {
        val subscribe = model.getPushRequest(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.hideLoading()
                view?.setPushResponse(it)
            }, {
                view?.hideLoading()
                ToastUtils.showShort(it.message ?: "请求失败")
                view?.setPushResponse(null)
            })
        addDisposable(subscribe)
    }

    override fun getPrivateMsgRequest(map: Map<String, String>) {
        val subscribe = model.getPrivateMsgRequest(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.hideLoading()
                view?.setPrivateMsgResponse(it)
            }, {
                ToastUtils.showShort(it.message ?: "请求失败")
                view?.hideLoading()
                view?.setPrivateMsgResponse(null)
            })
        addDisposable(subscribe)
    }

    override fun getRecFriendRequest(map: Map<String, String>) {
        val subscribe = model.getRecFriendRequest(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.hideLoading()
                view?.setRecFriendResponse(it)
            }, {
                ToastUtils.showShort(it.message ?: "请求失败")
                view?.hideLoading()
                view?.setRecFriendResponse(null)
            })
        addDisposable(subscribe)
    }
}