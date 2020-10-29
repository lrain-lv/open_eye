package com.app.eye.ui.mvp.model.entity

import android.text.TextUtils
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.entity.MultiItemEntity

data class ComAttentionEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<AttItem>,
    val nextPageUrl: String,
    val total: Int
)

data class AttItem(
    val adIndex: Int,
    val `data`: AttData,
    val id: Int,
    val tag: Any,
    val type: String
) : MultiItemEntity {
    companion object {
        const val TYPE_NONE = 0
        const val TYPE_VIDEO = 1
        const val TYPE_PIC2 = 2
        const val TYPE_PIC3 = 3
        const val TYPE_PIC4 = 4
        const val TYPE_USER = 5
    }

    override val itemType: Int
        get() = when {
            TextUtils.equals("video", data.content.type) -> TYPE_VIDEO
            StringUtils.equalsIgnoreCase("userListCard", type) -> TYPE_USER
            else -> when (data.content.data.urls.size) {
                1 -> TYPE_VIDEO
                2 -> TYPE_PIC2
                3 -> TYPE_PIC3
                4 -> TYPE_PIC4
                else -> TYPE_NONE
            }
        }

}

data class AttData(
    val actionUrl: String,
    val content: AttContent,
    val count: Int,
    val dataType: String,
    val header: AttHeader,
    val id: Int,
    val userList: MutableList<AttUser>
)

data class AttContent(
    val adIndex: Int,
    val `data`: AttDataX,
    val id: Int,
    val type: String
)

data class AttHeader(
    val actionUrl: String,
    val followType: String,
    val icon: String,
    val iconType: String,
    val id: Int,
    val issuerName: String,
    val showHateVideo: Boolean,
    val tagId: Int,
    val time: Long,
    val topShow: Boolean
)

data class AttUser(
    val actionUrl: String,
    val avatar: String,
    val expert: Boolean,
    val id: Int,
    val ifLight: Boolean,
    val ifPgc: Boolean,
    val nickname: String,
    val releaseDate: Long
)

data class AttDataX(
    val addWatermark: Boolean,
    val area: String?,
    val checkStatus: String,
    val city: String,
    val collected: Boolean,
    val consumption: AttConsumption,
    val cover: AttCover,
    val createTime: Long,
    val dataType: String,
    val description: String?,
    val duration: Int,
    val height: Int,
    val id: Int,
    val ifMock: Boolean,
    val latitude: Double,
    val library: String,
    val longitude: Double,
    val owner: AttOwner,
    val playUrl: String,
    val playUrlWatermark: String,
    val privateMessageActionUrl: String,
    val reallyCollected: Boolean,
    val recentOnceReply: Any,
    val releaseTime: Long,
    val resourceType: String,
    val selectedTime: Any,
    val status: Any,
    val tags: MutableList<AttTag>,
    val title: String,
    val transId: Any,
    val type: String,
    val uid: Int,
    val updateTime: Long,
    val url: String,
    val urls: MutableList<String>,
    val urlsWithWatermark: MutableList<String>,
    val validateResult: String,
    val validateStatus: String,
    val validateTaskId: String,
    val width: Int
)

data class AttConsumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class AttCover(
    val blurred: Any,
    val detail: String,
    val feed: String,
    val homepage: Any,
    val sharing: Any
)

data class AttOwner(
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
    val university: String,
    val userType: String
)

data class AttTag(
    val actionUrl: String,
    val adTrack: Any,
    val bgPicture: String,
    val childTagIdList: Any,
    val childTagList: Any,
    val communityIndex: Int,
    val desc: String,
    val haveReward: Boolean,
    val headerImage: String,
    val id: Int,
    val ifNewest: Boolean,
    val name: String,
    val newestEndTime: Any,
    val tagRecType: String
)