package com.app.eye.ui.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

data class SearchEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<SearchItem>,
    val nextPageUrl: String,
    val total: Int
)

data class SearchItem(
    val adIndex: Int,
    val `data`: SearchData,
    val id: Int,
    val tag: Any,
    val type: String
) : MultiItemEntity {

    companion object {
        const val SEARCH_TEXT = 0
        const val SEARCH_VIDEO = 1
        const val SEARCH_BRIEF_CARD = 2
        const val NONE = -1

    }

    override val itemType: Int
        get() =
            when (type) {
                "textCard" -> SEARCH_TEXT
                "briefCard" -> SEARCH_BRIEF_CARD
                "videoSmallCard" -> SEARCH_VIDEO
                else -> NONE
            }
}

data class SearchData(
    val actionUrl: String?,
    val ad: Boolean,
    val adTrack: Any,
    val author: SearchAuthor?,
    val brandWebsiteInfo: Any,
    val campaign: Any,
    val category: String,
    val collected: Boolean,
    val consumption: SearchConsumption,
    val cover: SearchCover,
    val dataType: String,
    val date: Long,
    val description: String,
    val descriptionEditor: String,
    val descriptionPgc: String,
    val duration: Int,
    val expert: Boolean,
    val favoriteAdTrack: Any,
    val follow: SearchFollow?,
    val haveReward: Boolean,
    val icon: String,
    val iconType: String,
    val id: Int,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val ifNewest: Boolean,
    val ifPgc: Boolean,
    val ifShowNotificationIcon: Boolean,
    val label: Any,
    val labelList: MutableList<SearchLabel>,
    val lastViewTime: Any,
    val library: String,
    val medalIcon: Boolean,
    val newestEndTime: Any,
    val playInfo: MutableList<SearchPlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val promotion: Any,
    val provider: SearchProvider,
    val reallyCollected: Boolean,
    val recallSource: Any,
    val recall_source: Any,
    val releaseTime: Long,
    val remark: Any,
    val resourceType: String,
    val searchWeight: Int,
    val shareAdTrack: Any,
    val showHotSign: Boolean,
    val slogan: Any,
    val src: Any,
    val subTitle: Any,
    val subtitles: MutableList<SearchSubtitle>,
    val switchStatus: Boolean,
    val tags: MutableList<SearchTag>,
    val text: String,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: String,
    val topicTitle: String,
    val type: String,
    val uid: Int,
    val videoPosterBean: Any,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: SearchWebUrl
)

data class SearchAuthor(
    val adTrack: Any,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: SearchFollow,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: SearchShield,
    val videoNum: Int
)

data class SearchConsumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class SearchCover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: Any,
    val sharing: Any
)

data class SearchLabel(
    val actionUrl: Any,
    val text: String
)

data class SearchPlayInfo(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: MutableList<SearchUrl>,
    val width: Int
)

data class SearchProvider(
    val alias: String,
    val icon: String,
    val name: String
)

data class SearchSubtitle(
    val type: String,
    val url: String
)

data class SearchTag(
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

data class SearchWebUrl(
    val forWeibo: String,
    val raw: String
)

data class SearchFollow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class SearchShield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class SearchUrl(
    val name: String,
    val size: Int,
    val url: String
)