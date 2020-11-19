package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.BrandWallContract
import com.app.eye.ui.mvp.model.BrandWallModel
import com.app.eye.ui.mvp.model.entity.BrandListItemX
import com.blankj.utilcode.util.StringUtils
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.core.Observable

class BrandWallPresenter : BasePresenter<BrandWallContract.Model, BrandWallContract.View>(),
    BrandWallContract.Presenter {
    override fun createModel(): BrandWallContract.Model = BrandWallModel()

    override fun getRequest(map: Map<String, String>, isRefresh: Boolean) {
        val subscribe = model.getRequest(map, isRefresh)
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

    override fun getListRequest() {
        val list = mutableListOf<BrandListItemX>()
        val indexList = mutableListOf<String>()
        val subscribe = model.getListRequest()
            .map {
                if (StringUtils.equals("0", it.code)) {
                    val result = it.result
                    val itemList = result.item_list
                    itemList.forEach { it ->
                        val key = it.key
                        val item = BrandListItemX("", key)
                        list.add(item)
                        indexList.add(key)
                        list.addAll(it.items)
                    }
                }
                return@map list
            }
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setBrandListData(it, indexList)
                view?.hideLoading()
            }, {
                Logger.e(it.message!!)
                view?.setBrandListData(mutableListOf(), mutableListOf())
                view?.hideLoading()
            })
        addDisposable(subscribe)
    }
}