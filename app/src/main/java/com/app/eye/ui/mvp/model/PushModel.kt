package com.app.eye.ui.mvp.model

import com.app.eye.http.RetrofitManager
import com.app.eye.ui.mvp.contract.PushContract
import com.app.eye.ui.mvp.model.entity.MessageEntity
import com.app.eye.ui.mvp.model.entity.PushEntity
import com.app.eye.ui.mvp.model.entity.RecFriendEntity
import io.reactivex.rxjava3.core.Observable

class PushModel : PushContract.Model {
    override fun getPushRequest(map: Map<String, String>): Observable<PushEntity> {
        return RetrofitManager.service.getPushMessage(map)
    }

    override fun getPrivateMsgRequest(map: Map<String, String>): Observable<MessageEntity> =
        RetrofitManager.service.getPrivateMsg(map)

    override fun getRecFriendRequest(map: Map<String, String>): Observable<RecFriendEntity> =
        RetrofitManager.service.getRecFriend(map)

}