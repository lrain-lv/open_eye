package com.app.eye.ui.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

data class InteractEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: List<InteractItem>,
    val nextPageUrl: String?,
    val total: Int
) {
    override fun toString(): String {
        return "InteractEntity(adExist=$adExist, count=$count, itemList=$itemList, nextPageUrl=$nextPageUrl, total=$total)"
    }
}

data class InteractItem(
    val adIndex: Int,
    val `data`: InteractData,
    val id: Int,
    val type: String
) {
    override fun toString(): String {
        return "InteractItem(adIndex=$adIndex, `data`=$`data`, id=$id, type='$type')"
    }
}

data class InteractData(
    val actionUrl: String,
    val briefCard: InteractBriefCard,
    val cover: String?,
    val createDate: Long,
    val createTime: Long,
    val dataType: String,
    val dynamicType: String,
    val hot: Boolean,
    val icon: String,
    val id: Int,
    val imageUrl: String,
    val likeCount: Int,
    val liked: Boolean,
    val merge: Boolean,
    val mergeNickName: String?,
    val mergeSubTitle: String?,
    val message: String,
    val parentReply: InteractParentReply,
    val parentReplyId: Long,
    val replyActionType: String,
    val replyId: Long,
    val replyStatus: String,
    val rootReplyId: Long,
    val sequence: Int,
    val showConversationButton: Boolean,
    val showParentReply: Boolean,
    val sid: Any,
    val simpleVideo: InteractSimpleVideo,
    val subTitle: String,
    val text: String,
    val title: String,
    val type: String,
    val ugcVideoId: Any,
    val ugcVideoUrl: Any,
    val user: InteractUserX,
    val userBlocked: Boolean,
    val userType: Any,
    val videoId: Int,
    val videoTitle: Any,
    val viewed: Boolean
)

data class InteractBriefCard(
    val actionUrl: String,
    val adTrack: Any,
    val dataType: String,
    val description: String,
    val expert: Boolean,
    val follow: InteractFollow,
    val icon: String,
    val iconType: String,
    val id: Int,
    val ifPgc: Boolean,
    val ifShowNotificationIcon: Boolean,
    val subTitle: Any,
    val title: String,
    val uid: Int
)

data class InteractParentReply(
    val id: Long,
    val imageUrl: String?,
    val message: String,
    val replyStatus: String,
    val user: InteractUser
)

data class InteractSimpleVideo(
    val actionUrl: String,
    val category: Any,
    val collected: Boolean,
    val consumption: InteractConsumption,
    val count: Int,
    val cover: InteractCover,
    val description: String,
    val duration: Int,
    val id: Int,
    val onlineStatus: String,
    val playUrl: String,
    val releaseTime: Long,
    val resourceType: String,
    val title: String,
    val uid: Int
)

data class InteractUserX(
    val actionUrl: String,
    val area: Any,
    val avatar: String,
    val birthday: Any,
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

data class InteractFollow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class InteractUser(
    val actionUrl: String,
    val area: Any,
    val avatar: String,
    val birthday: Int,
    val city: Any,
    val country: Any,
    val cover: Any,
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

data class InteractConsumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class InteractCover(
    val blurred: Any,
    val detail: String,
    val feed: String,
    val homepage: Any,
    val sharing: Any
)