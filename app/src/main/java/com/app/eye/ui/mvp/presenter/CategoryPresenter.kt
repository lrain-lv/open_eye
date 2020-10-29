package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.CategoryContract
import com.app.eye.ui.mvp.model.CategoryModel

class CategoryPresenter : BasePresenter<CategoryContract.Model, CategoryContract.View>(),
    CategoryContract.Presenter {
    override fun createModel(): CategoryContract.Model = CategoryModel()

    override fun getCategoryRequest() {
        val subscribe = model.getCategoryRequest()
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setCategoryResponse(it)
                view?.hideLoading()
            }, {
                view?.hideLoading()
                view?.setCategoryResponse(null)
            })
        addDisposable(subscribe)
    }

    override fun getSpecialTopicRequest(map: Map<String, String>) {
        val subscribe = model.getSpecialTopicRequest(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setSpecialTopicResponse(it)
                view?.hideLoading()
            }, {
                view?.hideLoading()
                view?.setSpecialTopicResponse(null)
            })
        addDisposable(subscribe)
    }

    override fun getInformationRequest(map: Map<String, String>) {
        val subscribe = model.getInformationRequest(map)
            .compose(SchedulerUtils.ioToMain())
            .subscribe({
                view?.setInformationResponse(it)
                view?.hideLoading()
            }, {
                view?.hideLoading()
                view?.setInformationResponse(null)
            })
        addDisposable(subscribe)
    }
}