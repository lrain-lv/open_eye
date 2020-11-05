package com.app.eye.ui.mvp.model.entity

data class MessageEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<MsgItem>,
    val nextPageUrl: String?,
    val total: Int,
)

data class MsgItem(
    val adIndex: Int,
    val `data`: MsgData,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String,
)

data class MsgData(
    val actionUrl: String?,
    val content: String,
    val dataType: String,
    val id: Int,
    val lastTime: Long,
    val unReadCount: Int,
    val user: MsgUser,
)

data class MsgUser(
    val actionUrl: String,
    val area: Any,
    val avatar: String,
    val birthday: Long,
    val city: String,
    val country: String,
    val cover: String,
    val description: String,
    val expert: Boolean,
    val followed: Boolean,
    val gender: String,
    val ifPgc: Boolean,
    val job: String,
    val library: String,
    val limitVideoOpen: Boolean,
    val nickname: String,
    val registDate: Long,
    val releaseDate: Long,
    val uid: Int,
    val university: Any,
    val userType: String,
)