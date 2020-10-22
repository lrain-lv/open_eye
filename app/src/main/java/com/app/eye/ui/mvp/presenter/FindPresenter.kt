package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.ui.mvp.contract.FindContract
import com.app.eye.ui.mvp.model.FindModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class FindPresenter : BasePresenter<FindContract.Model, FindContract.View>(),
    FindContract.Presenter {
    override fun createModel(): FindContract.Model = FindModel()
    override fun getDiscoveryData() {
        val subscribe = model.getDiscoveryData()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { view?.showLoading() }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.hideLoading()
                view?.getResponse(it)
            }, { view?.hideLoading() })
        addDisposable(subscribe)
    }


}