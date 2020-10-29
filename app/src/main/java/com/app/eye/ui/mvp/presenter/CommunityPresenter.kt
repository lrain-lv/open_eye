package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.CommunityContract
import com.app.eye.ui.mvp.model.CommunityModel
import com.app.eye.ui.mvp.model.entity.ComItem
import com.blankj.utilcode.util.ToastUtils
import com.orhanobut.logger.Logger

class CommunityPresenter : BasePresenter<CommunityContract.Model, CommunityContract.View>(),
    CommunityContract.Presenter {
    override fun createModel(): CommunityContract.Model = CommunityModel()
    override fun getRecRequest(isRefresh: Boolean, map: Map<String, String>) {
        val subscribe = model.getRecRequest(map)
            .map {
                if (!isRefresh) {
                    val mutableList =
                        it.itemList.filter { item -> item.type == "communityColumnsCard" } as MutableList<ComItem>
                    it.itemList = mutableList
                }
                it
            }
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.hideLoading()
                view?.setComRecResponse(it)
            }, {
                println(it.stackTrace)
                Logger.e(it.message!!)
                view?.hideLoading()
                view?.setComRecResponse(null)
                ToastUtils.showShort(it.message ?: "请求失败")
            })
        addDisposable(subscribe)
    }

    override fun getComAttentionRequest(map: Map<String, String>) {
        val subscribe = model.getComAttentionRequest(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.hideLoading()
                view?.setComAttentionResponse(it)
            }, {
                view?.hideLoading()
                view?.setComAttentionResponse(null)
                ToastUtils.showShort(it.message ?: "请求失败")
            })
        addDisposable(subscribe)
    }
}