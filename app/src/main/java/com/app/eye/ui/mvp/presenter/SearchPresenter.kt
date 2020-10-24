package com.app.eye.ui.mvp.presenter

import com.app.eye.base.mvp.BasePresenter
import com.app.eye.rx.SchedulerUtils
import com.app.eye.ui.mvp.contract.SearchContract
import com.app.eye.ui.mvp.model.HotSearchModel
import com.app.eye.ui.mvp.model.entity.HotSearchEntity
import io.reactivex.rxjava3.functions.Function

class SearchPresenter : BasePresenter<SearchContract.Model, SearchContract.View>(),
    SearchContract.Presenter {
    override fun createModel(): SearchContract.Model = HotSearchModel()

    override fun getHotData() {
        val subscribe = model.getHotData()
            .map { t ->
                var list = mutableListOf<HotSearchEntity>()
                list.add(HotSearchEntity("搜索历史", isKeyWord = false, isHistory = true))
                list.add(HotSearchEntity("艺术", isKeyWord = true, isHistory = true))
                list.add(HotSearchEntity("街头", isKeyWord = true, isHistory = true))
                list.add(HotSearchEntity("摄影日常", isKeyWord = true, isHistory = true))
                list.add(HotSearchEntity("热搜关键词", false))
                t?.forEach { it ->
                    list.add(HotSearchEntity(it, true))
                }
                list
            }
            .compose(SchedulerUtils.ioToMain())
            .subscribe {
                view?.setHotSearchData(it)
            }
        addDisposable(subscribe)
    }

    override fun doPreSearchData(query: String) {
        val subscribe = model.doPreSearchData(query)
            .map { t ->
                var list = mutableListOf<HotSearchEntity>()
                t?.forEach { it ->
                    list.add(HotSearchEntity(keyword = query, searchResult = it, isSearch = true))
                }
                list
            }
            .compose(SchedulerUtils.ioToMain())
            .subscribe {
                view?.setPreSearchResult(it)
            }
        addDisposable(subscribe)
    }

    override fun doSearch(query: String) {
        var subscribe = model.doSearch(query)
            .compose(SchedulerUtils.ioToMain())
            .subscribe { view?.setSearchResult(it) }
        addDisposable(subscribe)
    }
}