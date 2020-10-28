package com.app.eye.ui.mvp.model.entity

import android.text.TextUtils
import com.chad.library.adapter.base.entity.MultiItemEntity

data class TagVideoEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<TagVideoItem>,
    val nextPageUrl: String,
    val total: Int
)

data class TagVideoItem(
    val adIndex: Int,
    val `data`: TagVideoData,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
) : MultiItemEntity {
    companion object {
        const val TYPE_NONE = 0
        const val TYPE_VIDEO = 1
        const val TYPE_PIC2 = 2
        const val TYPE_PIC3 = 3
        const val TYPE_PIC4 = 4
    }

    override val itemType: Int
        get() {
            return if (TextUtils.equals("video", data.content.type))
                TYPE_VIDEO
            else
                when (data.content.data.urls.size) {
                    1 -> TYPE_VIDEO
                    2 -> TYPE_PIC2
                    3 -> TYPE_PIC3
                    4 -> TYPE_PIC4
                    else -> TYPE_NONE
                }
        }
}

data class TagVideoData(
    val adTrack: Any,
    val content: TagVideoContent,
    val dataType: String,
    val header: TagVideoHeader
)

data class TagVideoContent(
    val adIndex: Int,
    val `data`: TagVideoDataX,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
)

data class TagVideoHeader(
    val actionUrl: String,
    val followType: String,
    val icon: String,
    val iconType: String,
    val id: Int,
    val issuerName: String,
    val labelList: Any,
    val showHateVideo: Boolean,
    val tagId: Int,
    val tagName: Any,
    val time: Long,
    val topShow: Boolean
)

data class TagVideoDataX(
    val addWatermark: Boolean,
    val area: String?,
    val checkStatus: String,
    val city: String,
    val collected: Boolean,
    val consumption: TagVideoConsumption,
    val cover: TagVideoCover,
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
    val owner: TagVideoOwner,
    val playUrl: String,
    val playUrlWatermark: String,
    val privateMessageActionUrl: String,
    val reallyCollected: Boolean,
    val recentOnceReply: Any,
    val releaseTime: Long,
    val resourceType: String,
    val selectedTime: Any,
    val status: Any,
    val tags: MutableList<TagVideoTag>,
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

data class TagVideoConsumption(
    val collectionCount: Int,
    val playCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class TagVideoCover(
    val blurred: Any,
    val detail: String,
    val feed: String,
    val homepage: Any,
    val sharing: Any
)

data class TagVideoOwner(
    val actionUrl: String,
    val area: Any,
    val avatar: String,
    val bannedDate: Any,
    val bannedDays: Any,
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
    val releaseDate: Long,
    val tagIds: Any,
    val uid: Int,
    val university: Any,
    val uploadStatus: String,
    val userMedalBeanList: Any,
    val userType: String,
    val username: String
)

data class TagVideoTag(
    val actionUrl: String,
    val adTrack: Any,
    val alias: Any,
    val bgPicture: String,
    val childTagIdList: Any,
    val childTagList: Any,
    val communityIndex: Int,
    val desc: String,
    val haveReward: Boolean,
    val headerImage: String,
    val id: Int,
    val ifNewest: Boolean,
    val ifShow: Boolean,
    val level: Int,
    val name: String,
    val newestEndTime: Any,
    val parentId: Int,
    val recWeight: Double,
    val relationType: Int,
    val tagRecType: String,
    val tagStatus: String,
    val top: Int,
    val type: String
)