package com.app.eye.ui.mvp.model.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

data class ReplyVideoEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<ReplyItem>,
    val nextPageUrl: String,
    val total: Int
)

data class ReplyItem(
    val adIndex: Int,
    val `data`: ReplyData,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
) : MultiItemEntity {
    companion object {
        const val TYPE_NONE = 0
        const val TYPE_REPLY = 1
        const val TYPE_TEXT = 2
    }

    override val itemType: Int
        get() = when (type) {
            "textCard" -> TYPE_TEXT
            "reply" -> TYPE_REPLY
            else -> TYPE_NONE
        }
}

data class ReplyData(
    val actionUrl: String?,
    val adTrack: Any,
    val cover: Any,
    val createTime: Long,
    val dataType: String,
    val follow: Any,
    val hot: Boolean,
    val id: Long,
    val imageUrl: String?,
    val likeCount: Int,
    val liked: Boolean,
    val message: String,
    val parentReply: ParentReply?,
    val parentReplyId: Long,
    val replyStatus: String,
    val rootReplyId: Long,
    val sequence: Int,
    val showConversationButton: Boolean,
    val showParentReply: Boolean,
    val sid: String,
    val subTitle: Any,
    val text: String,
    val type: String,
    val ugcVideoId: Any,
    val ugcVideoUrl: Any,
    val user: ReplyUser,
    val userBlocked: Boolean,
    val userType: Any,
    val videoId: Int,
    val videoTitle: Any
)

data class ParentReply(
    val id: Long,
    val imageUrl: String?,
    val message: String?,
    val replyStatus: String,
    val user: User
)

data class User(
    val actionUrl: String,
    val area: Any,
    val avatar: String,
    val birthday: Long,
    val city: Any,
    val country: Any,
    val cover: Any,
    val description: Any,
    val expert: Boolean,
    val followed: Boolean,
    val gender: Any,
    val ifPgc: Boolean,
    val job: Any,
    val library: String,
    val limitVideoOpen: Boolean,
    val nickname: String,
    val registDate: Long,
    val releaseDate: Any,
    val uid: Int,
    val university: Any,
    val userType: String
)


data class ReplyUser(
    val actionUrl: String,
    val area: Any,
    val avatar: String,
    val birthday: Long,
    val city: Any,
    val country: Any,
    val cover: String?,
    val description: String,
    val expert: Boolean,
    val followed: Boolean,
    val gender: String,
    val ifPgc: Boolean,
    val job: Any,
    val library: String,
    val limitVideoOpen: Boolean,
    val nickname: String,
    val registDate: Long,
    val releaseDate: Long,
    val uid: Int,
    val university: Any,
    val userType: String
)