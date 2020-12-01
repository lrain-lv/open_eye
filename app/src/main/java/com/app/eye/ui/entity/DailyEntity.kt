package com.app.eye.ui.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

data class DailyEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<ItemDaily>,
    val nextPageUrl: String,
    val total: Int

) {
    override fun toString(): String {
        return "DailyEntity(adExist=$adExist, count=$count, itemList=$itemList, nextPageUrl='$nextPageUrl', total=$total)"
    }
}

data class ItemDaily(
    val adIndex: Int,
    val `data`: DataDaily,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
) : MultiItemEntity {
    override val itemType: Int
        get() = when (type) {
            "textCard" -> DAILY_TEXT
            "followCard" -> DAILY_VIDEO
            "informationCard" -> DAILY_INFORMATION
            else -> NONE
        }

    companion object {
        const val DAILY_TEXT = 0
        const val DAILY_VIDEO = 1
        const val DAILY_INFORMATION = 2
        const val NONE = -1

    }

}

data class DataDaily(
    val actionUrl: String?,
    val adTrack: Any,
    val backgroundImage: String,
    val bannerList: MutableList<Banner>,
    val content: Content,
    val dataType: String,
    val follow: Any,
    val header: HeaderDaily,
    val headerType: String,
    val id: Int,
    val rightText: String?,
    val startTime: Long,
    val subTitle: Any,
    val text: String,
    val titleList: MutableList<String>,
    val type: String
)

data class Banner(
    val background_image: String,
    val link: String,
    val poster_image: String,
    val tag_name: String,
    val title: String
)

data class Content(
    val adIndex: Int,
    val `data`: DataXDaily,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
)

data class HeaderDaily(
    val actionUrl: String,
    val cover: Any,
    val description: String,
    val font: Any,
    val icon: String,
    val iconType: String,
    val id: Int,
    val label: Any,
    val labelList: Any,
    val rightText: Any,
    val showHateVideo: Boolean,
    val subTitle: Any,
    val subTitleFont: Any,
    val textAlign: String,
    val time: Long,
    val title: String
)

data class DataXDaily(
    val ad: Boolean,
    val adTrack: MutableList<Any>,
    val author: AuthorDaily,
    val brandWebsiteInfo: Any,
    val campaign: Any,
    val category: String,
    val collected: Boolean,
    val consumption: ConsumptionDaily,
    val cover: CoverDaily,
    val dataType: String,
    val date: Long,
    val description: String,
    val descriptionEditor: String,
    val descriptionPgc: Any,
    val duration: Int,
    val favoriteAdTrack: Any,
    val id: Int,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val label: Any,
    val labelList: MutableList<Any>,
    val lastViewTime: Any,
    val library: String,
    val playInfo: MutableList<PlayInfoDaily>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val promotion: Any,
    val provider: ProviderDaily,
    val reallyCollected: Boolean,
    val recallSource: Any,
    val recall_source: Any,
    val releaseTime: Long,
    val remark: Any,
    val resourceType: String,
    val searchWeight: Int,
    val shareAdTrack: Any,
    val slogan: Any,
    val src: Any,
    val subtitles: MutableList<Any>,
    val tags: MutableList<TagDaily>,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: Any,
    val type: String,
    val videoPosterBean: Any,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: WebUrlDaily
)

data class AuthorDaily(
    val adTrack: Any,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: FollowDaily,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: ShieldDaily,
    val videoNum: Int
)

data class ConsumptionDaily(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class CoverDaily(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: String,
    val sharing: Any
)

data class PlayInfoDaily(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: MutableList<UrlDaily>,
    val width: Int
)

data class ProviderDaily(
    val alias: String,
    val icon: String,
    val name: String
)

data class TagDaily(
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

data class WebUrlDaily(
    val forWeibo: String,
    val raw: String
)

data class FollowDaily(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class ShieldDaily(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class UrlDaily(
    val name: String,
    val size: Int,
    val url: String
)