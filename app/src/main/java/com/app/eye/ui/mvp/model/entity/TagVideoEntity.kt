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
        const val TYPE_TEXT_CARD = 5
        const val TYPE_FOLLOW_CARD = 6
        const val TYPE_VIDEO_SMALL_CARD = 7
    }

    override val itemType: Int
        get() {
            return when {
                TextUtils.equals("textCard", type) -> TYPE_TEXT_CARD
                TextUtils.equals("followCard", type) -> TYPE_FOLLOW_CARD
                TextUtils.equals("videoSmallCard", type) -> TYPE_VIDEO_SMALL_CARD
                TextUtils.equals("video", data.content?.type) -> TYPE_VIDEO
                else -> when (data.content?.data?.urls?.size) {
                    1 -> TYPE_VIDEO
                    2 -> TYPE_PIC2
                    3 -> TYPE_PIC3
                    4 -> TYPE_PIC4
                    else -> TYPE_NONE
                }
            }
        }
}

data class TagVideoData(
    val actionUrl: String?,
    val ad: Boolean,
    val author: TagVideoAuthor,
    val candidateId: Int,
    val category: String,
    val collected: Boolean,
    val consumption: TagVideoConsumption,
    val content: TagVideoContent?,
    val cover: TagVideoCoverX,
    val createTime: Long,
    val dataType: String,
    val date: Long,
    val description: String,
    val descriptionEditor: String,
    val descriptionPgc: String,
    val duration: Int,
    val follow: Any,
    val header: TagVideoHeader,
    val id: Int,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val infoStatus: String,
    val label: Any,
    val library: String,
    val playInfo: MutableList<TagVideoPlayInfoX>,
    val playUrl: String,
    val played: Boolean,
    val provider: TagVideoProviderX,
    val reallyCollected: Boolean,
    val recallSource: String,
    val recall_source: String,
    val releaseTime: Long,
    val resourceType: String,
    val searchWeight: Int,
    val showLabel: Boolean,
    val slogan: Any,
    val sourceUrl: String,
    val src: Int,
    val status: String,
    val subTitle: Any,
    val subtitleStatus: String,
    val tags: MutableList<TagVideoTagX>,
    val tailTimePoint: Int,
    val text: String,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: String,
    val translateStatus: String,
    val type: String,
    val updateTime: Long,
    val videoPosterBean: Any,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: TagVideoWebUrlX
)

data class TagVideoAuthor(
    val adTrack: Any,
    val amplifiedLevelId: Any,
    val approvedNotReadyVideoCount: Int,
    val authorStatus: String,
    val authorType: String,
    val cover: String,
    val description: String,
    val expert: Boolean,
    val follow: TagVideoFollow,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val library: String,
    val link: String,
    val name: String,
    val pendingVideoCount: Int,
    val recSort: Int,
    val shield: TagVideoShield,
    val videoNum: Int
)

data class TagVideoConsumption(
    val collectionCount: Int,
    val playCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class TagVideoContent(
    val adIndex: Int,
    val `data`: TagVideoDataX,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
)

data class TagVideoCoverX(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: Any,
    val sharing: Any
)

data class TagVideoHeader(
    val actionUrl: String,
    val cover: Any,
    val description: String,
    val followType: String,
    val font: Any,
    val icon: String,
    val iconType: String,
    val id: Int,
    val issuerName: String,
    val label: Any,
    val labelList: Any,
    val rightText: Any,
    val showHateVideo: Boolean,
    val subTitle: Any,
    val subTitleFont: Any,
    val tagId: Int,
    val tagName: Any,
    val textAlign: String,
    val time: Long,
    val title: String,
    val topShow: Boolean
)

data class TagVideoPlayInfoX(
    val bitRate: Int,
    val dimension: String,
    val duration: Int,
    val height: Int,
    val id: Int,
    val name: String,
    val size: Int,
    val type: String,
    val url: String,
    val urlList: MutableList<TagVideoUrlX>,
    val videoId: Int,
    val width: Int
)

data class TagVideoProviderX(
    val alias: String,
    val icon: String,
    val id: Int,
    val name: String,
    val status: String
)

data class TagVideoTagX(
    val actionUrl: String,
    val adTrack: Any,
    val alias: Any,
    val bgPicture: String,
    val childTagIdList: Any,
    val childTagList: Any,
    val communityIndex: Int,
    val desc: Any,
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

data class TagVideoWebUrlX(
    val forWeibo: String,
    val raw: String
)

data class TagVideoFollow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class TagVideoShield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class TagVideoDataX(
    val ad: Boolean,
    val author: TagVideoAuthorX,
    val area: String?,
    val city: String?,
    val brandWebsiteInfo: Any,
    val campaign: Any,
    val candidateId: Int,
    val category: String,
    val collected: Boolean,
    val consumption: TagVideoConsumptionX,
    val cover: TagVideoCover,
    val createTime: Long,
    val dataType: String,
    val date: Long,
    val description: String?,
    val descriptionEditor: String,
    val descriptionPgc: String,
    val duration: Int,
    val favoriteAdTrack: Any,
    val id: Int,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val infoStatus: String,
    val label: Any,
    val lastViewTime: Any,
    val library: String,
    val playInfo: MutableList<TagVideoPlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val premiereDate: Any,
    val promotion: Any,
    val provider: TagVideoProvider,
    val reallyCollected: Boolean,
    val recallSource: String,
    val recall_source: String,
    val releaseTime: Long,
    val remark: Any,
    val resourceType: String,
    val searchWeight: Int,
    val shareAdTrack: Any,
    val showLabel: Boolean,
    val slogan: Any,
    val sourceUrl: String,
    val src: Int,
    val status: String,
    val subtitleStatus: String,
    val tags: MutableList<TagVideoTag>,
    val tailTimePoint: Int,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: String,
    val translateStatus: String,
    val type: String,
    val updateTime: Any,
    val urls: MutableList<String>?,
    val videoPosterBean: Any,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: TagVideoWebUrl
)

data class TagVideoAuthorX(
    val adTrack: Any,
    val amplifiedLevelId: Any,
    val approvedNotReadyVideoCount: Int,
    val authorStatus: String,
    val authorType: String,
    val cover: String,
    val description: String,
    val expert: Boolean,
    val follow: TagVideoFollowX,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val library: String,
    val link: String,
    val name: String,
    val pendingVideoCount: Int,
    val recSort: Int,
    val shield: TagVideoShieldX,
    val videoNum: Int
)

data class TagVideoConsumptionX(
    val collectionCount: Int,
    val playCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class TagVideoCover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: Any,
    val sharing: Any
)

data class TagVideoPlayInfo(
    val bitRate: Int,
    val dimension: String,
    val duration: Int,
    val height: Int,
    val id: Int,
    val name: String,
    val size: Int,
    val type: String,
    val url: String,
    val urlList: MutableList<TagVideoUrl>,
    val videoId: Int,
    val width: Int
)

data class TagVideoProvider(
    val alias: String,
    val icon: String,
    val id: Int,
    val name: String,
    val status: String
)

data class TagVideoTag(
    val actionUrl: String,
    val adTrack: Any,
    val alias: Any,
    val bgPicture: String,
    val childTagIdList: Any,
    val childTagList: Any,
    val communityIndex: Int,
    val desc: Any,
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

data class TagVideoWebUrl(
    val forWeibo: String,
    val raw: String
)

data class TagVideoFollowX(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class TagVideoShieldX(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class TagVideoUrl(
    val name: String,
    val size: Int,
    val url: String
)

data class TagVideoUrlX(
    val name: String,
    val size: Int,
    val url: String
)