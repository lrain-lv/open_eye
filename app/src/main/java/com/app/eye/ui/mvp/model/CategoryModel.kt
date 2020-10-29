package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.CategoryContract
import com.app.eye.ui.mvp.model.entity.CategoryEntity
import com.app.eye.ui.mvp.model.entity.InformationEntity
import com.app.eye.ui.mvp.model.entity.SpecialTopicEntity
import io.reactivex.rxjava3.core.Observable

class CategoryModel : CategoryContract.Model {
    override fun getCategoryRequest(): Observable<CategoryEntity> =
        RetrofitManager.service.getCategories()

    override fun getSpecialTopicRequest(map: Map<String, String>): Observable<SpecialTopicEntity> {
        return RetrofitManager.service.getSpecialTopic(map)
    }

    override fun getInformationRequest(map: Map<String, String>): Observable<InformationEntity> =
        RetrofitManager.service.getInformationList(map)
}