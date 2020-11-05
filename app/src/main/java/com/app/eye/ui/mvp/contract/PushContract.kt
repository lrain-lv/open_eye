package com.app.eye.ui.mvp.contract

import com.app.eye.base.mvp.IBaseView
import com.app.eye.base.mvp.IModel
import com.app.eye.base.mvp.IPresenter
import com.app.eye.ui.mvp.model.entity.MessageEntity
import com.app.eye.ui.mvp.model.entity.PushEntity
import com.app.eye.ui.mvp.model.entity.RecFriendEntity
import io.reactivex.rxjava3.core.Observable

interface PushContract {
    interface View : IBaseView {

        fun setPushResponse(pushEntity: PushEntity?)

        fun setPrivateMsgResponse(entity: MessageEntity?)
        fun setRecFriendResponse(entity: RecFriendEntity?)
    }

    interface Model : IModel {
        fun getPushRequest(map: Map<String, String>): Observable<PushEntity>
        fun getPrivateMsgRequest(map: Map<String, String>): Observable<MessageEntity>
        fun getRecFriendRequest(map: Map<String, String>): Observable<RecFriendEntity>
    }

    interface Presenter : IPresenter<View> {
        fun getPushRequest(map: Map<String, String>)
        fun getPrivateMsgRequest(map: Map<String, String>)
        fun getRecFriendRequest(map: Map<String, String>)
    }


}