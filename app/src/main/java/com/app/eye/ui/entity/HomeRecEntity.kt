package com.app.eye.ui.entity

import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.entity.MultiItemEntity

data class HomeRecEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<HomeRecItem>,
    val nextPageUrl: String?,
    val total: Int
)

data class HomeRecItem(
    val adIndex: Int,
    val `data`: HomeRecData,
    val id: Int,
    val type: String
) : MultiItemEntity {

    companion object {
        const val HOME_TEXT_CARD = 0
        const val HOME_FOLLOWCARD_VIDEO = 1
        const val HOME_INFORMATION = 2
        const val HOME_VIDEO_SMALL_CARD = 3
        const val HOME_UGC_SELECT_CARD = 4
        const val HOME_TAG_BRIEF_CARD = 5
        const val HOME_TOPIC_BRIEF_CARD = 6
        const val NONE = -1
    }

    override val itemType: Int
        get() =
            when (type) {
                "textCard" -> {
                    if (StringUtils.equalsIgnoreCase("TextCardWithTagId",
                            data.dataType)
                    ) NONE else HOME_TEXT_CARD
                }
                "followCard" -> HOME_FOLLOWCARD_VIDEO
                "informationCard" -> HOME_INFORMATION
                "videoSmallCard" -> HOME_VIDEO_SMALL_CARD
                "ugcSelectedCardCollection" -> HOME_UGC_SELECT_CARD
                "briefCard" -> {
                    if (StringUtils.equals("TagBriefCard",
                            data.dataType)
                    ) HOME_TAG_BRIEF_CARD else HOME_TOPIC_BRIEF_CARD
                }
                else -> NONE
            }

}

data class HomeRecData(
    val actionUrl: String?,
    val ad: Boolean,
    val author: HomeRecAuthor,
    val backgroundImage: String,
    val bannerList: MutableList<HomeRecBanner>,
    val brandWebsiteInfo: Any,
    val campaign: Any,
    val category: String,
    val collected: Boolean,
    val consumption: HomeRecConsumption,
    val content: HomeRecContent,
    val count: Int,
    val cover: HomeRecCoverX,
    val dataType: String,
    val date: Long,
    val description: String?,
    val descriptionEditor: String,
    val descriptionPgc: String,
    val duration: Int,
    val expert: Boolean,
    val follow: Any,
    val footer: Any,
    val haveReward: Boolean,
    val header: HomeRecHeader?,
    val headerType: String,
    val icon: String,
    val iconType: String,
    val id: Int,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val ifNewest: Boolean,
    val ifPgc: Boolean,
    val ifShowNotificationIcon: Boolean,
    val itemList: MutableList<HomeRecItemX>,
    val library: String,
    val medalIcon: Boolean,
    val newestEndTime: Any,
    val playInfo: MutableList<HomeRecPlayInfoX>,
    val playUrl: String,
    val played: Boolean,
    val provider: HomeRecProviderX,
    val reallyCollected: Boolean,
    val recallSource: String,
    val recall_source: String,
    val refreshUrl: String,
    val releaseTime: Long,
    val remark: String,
    val resourceType: String,
    val rightText: String?,
    val searchWeight: Int,
    val showHotSign: Boolean,
    val showImmediately: Boolean,
    val src: Int,
    val startTime: Long,
    val switchStatus: Boolean,
    val tags: MutableList<HomeRecTagX>,
    val text: String,
    val thumbPlayUrl: Any,
    val title: String,
    val titleList: MutableList<String>,
    val titlePgc: String,
    val topicTitle: String,
    val type: String,
    val uid: Int,
    val videoPosterBean: Any,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: HomeRecWebUrlX
)

data class HomeRecAuthor(
    val adTrack: Any,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: HomeRecFollow,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: HomeRecShield,
    val videoNum: Int
)

data class HomeRecBanner(
    val background_image: String,
    val link: String,
    val poster_image: String,
    val tag_name: String,
    val title: String
)

data class HomeRecConsumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class HomeRecContent(
    val adIndex: Int,
    val `data`: HomeRecDataX,
    val id: Int,
    val type: String
)

data class HomeRecCoverX(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: Any,
    val sharing: Any
)

data class HomeRecHeader(
    val actionUrl: String?,
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

data class HomeRecItemX(
    val adIndex: Int,
    val `data`: HomeRecDataXX,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
)

data class HomeRecPlayInfoX(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: MutableList<HomeRecUrlX>,
    val width: Int
)

data class HomeRecProviderX(
    val alias: String,
    val icon: String,
    val name: String
)

data class HomeRecTagX(
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

data class HomeRecWebUrlX(
    val forWeibo: String,
    val raw: String
)

data class HomeRecFollow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class HomeRecShield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class HomeRecDataX(
    val ad: Boolean,
    val author: HomeRecAuthorX,
    val brandWebsiteInfo: Any,
    val campaign: Any,
    val category: String,
    val collected: Boolean,
    val consumption: HomeRecConsumptionX,
    val cover: HomeRecCover,
    val dataType: String,
    val date: Long,
    val description: String,
    val descriptionEditor: String,
    val duration: Int,
    val id: Int,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val label: Any,
    val labelList: MutableList<HomeRecLabel>,
    val lastViewTime: Any,
    val library: String,
    val playInfo: MutableList<HomeRecPlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val promotion: Any,
    val provider: HomeRecProvider,
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
    val tags: MutableList<HomeRecTag>,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: Any,
    val type: String,
    val webUrl: HomeRecWebUrl
)

data class HomeRecAuthorX(
    val adTrack: Any,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: HomeRecFollowX,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: HomeRecShieldX,
    val videoNum: Int
)

data class HomeRecConsumptionX(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class HomeRecCover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: String,
    val sharing: Any
)

data class HomeRecLabel(
    val actionUrl: Any,
    val text: String
)

data class HomeRecPlayInfo(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: MutableList<HomeRecUrl>,
    val width: Int
)

data class HomeRecProvider(
    val alias: String,
    val icon: String,
    val name: String
)

data class HomeRecTag(
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

data class HomeRecWebUrl(
    val forWeibo: String,
    val raw: String
)

data class HomeRecFollowX(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class HomeRecShieldX(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class HomeRecUrl(
    val name: String,
    val size: Int,
    val url: String
)

data class HomeRecDataXX(
    val cover: HomeRecCoverXX,
    val dailyResource: Boolean,
    val dataType: String,
    val id: Int,
    val nickname: String,
    val resourceType: String,
    val url: String,
    val urls: MutableList<String>,
    val userCover: String
)

data class HomeRecCoverXX(
    val blurred: Any,
    val detail: String,
    val feed: String,
    val homepage: Any,
    val sharing: Any
)

data class HomeRecUrlX(
    val name: String,
    val size: Int,
    val url: String
)