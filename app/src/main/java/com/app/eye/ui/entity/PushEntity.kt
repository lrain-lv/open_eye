package com.app.eye.ui.entity

data class PushEntity(
    val messageList: MutableList<Message>,
    val nextPageUrl: String?,
    val updateTime: Long
)

data class Message(
    val actionUrl: String,
    val content: String,
    val date: Long,
    val icon: String,
    val id: Int,
    val ifPush: Boolean,
    val pushStatus: Int,
    val title: String,
    val uid: Any,
    val viewed: Boolean
)