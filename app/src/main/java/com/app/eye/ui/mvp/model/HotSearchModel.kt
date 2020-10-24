package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.SearchContract
import com.app.eye.ui.mvp.model.entity.SearchEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class HotSearchModel : SearchContract.Model {
    override fun getHotData(): Observable<MutableList<String>> =
        RetrofitManager.service.getHot()

    override fun doPreSearchData(query: String): Observable<MutableList<String>> =
        Observable.just(query)
            .debounce(800, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .flatMap {
                RetrofitManager.service.doPreSearch(query)
            }

    override fun doSearch(query: String): Observable<SearchEntity> =
        RetrofitManager.service.doSearch(query)

}