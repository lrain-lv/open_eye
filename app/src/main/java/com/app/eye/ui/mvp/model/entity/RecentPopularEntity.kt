package com.app.eye.ui.mvp.model.entity

data class RecentPopularEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<PopItem>,
    val nextPageUrl: String,
    val total: Int
)

data class PopItem(
    val adIndex: Int,
    val `data`: PopData,
    val id: Int,
    val tag: Any,
    val type: String
)

data class PopData(
    val content: PopContent,
    val dataType: String,
    val header: PopHeader
)

data class PopContent(
    val adIndex: Int,
    val `data`: PopDataX,
    val id: Int,
    val type: String
)

data class PopHeader(
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

data class PopDataX(
    val ad: Boolean,
    val author: PopAuthor,
    val candidateId: Int,
    val category: String,
    val collected: Boolean,
    val consumption: PopConsumption,
    val cover: PopCover,
    val createTime: Long,
    val dataType: String,
    val date: Long,
    val description: String?,
    val descriptionEditor: String,
    val duration: Int,
    val id: Int,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val infoStatus: String,
    val label: Any,
    val lastViewTime: Any,
    val library: String,
    val playInfo: MutableList<PopPlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val premiereDate: Any,
    val promotion: Any,
    val provider: PopProvider,
    val reallyCollected: Boolean,
    val recallSource: Any,
    val recall_source: Any,
    val releaseTime: Long,
    val remark: Any,
    val resourceType: String,
    val searchWeight: Int,
    val shareAdTrack: Any,
    val showLabel: Boolean,
    val slogan: Any,
    val sourceUrl: String,
    val src: Any,
    val status: String,
    val subtitleStatus: String,
    val subtitles: MutableList<Any>,
    val tags: MutableList<PopTag>?,
    val tailTimePoint: Int,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: Any,
    val translateStatus: String,
    val type: String,
    val updateTime: Long,
    val videoPosterBean: Any,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: PopWebUrl
)

data class PopAuthor(
    val adTrack: Any,
    val amplifiedLevelId: Int,
    val approvedNotReadyVideoCount: Int,
    val authorStatus: String,
    val authorType: String,
    val cover: String,
    val description: String,
    val expert: Boolean,
    val follow: PopFollow,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val library: String,
    val link: String,
    val name: String,
    val pendingVideoCount: Int,
    val recSort: Int,
    val shield: PopShield,
    val videoNum: Int
)

data class PopConsumption(
    val collectionCount: Int,
    val playCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class PopCover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: String,
    val sharing: Any
)

data class PopPlayInfo(
    val bitRate: Int,
    val dimension: String,
    val duration: Int,
    val height: Int,
    val id: Int,
    val name: String,
    val size: Int,
    val type: String,
    val url: String,
    val urlList: MutableList<PopUrl>,
    val videoId: Int,
    val width: Int
)

data class PopProvider(
    val alias: String,
    val icon: String,
    val id: Int,
    val name: String,
    val status: String
)

data class PopTag(
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

data class PopWebUrl(
    val forWeibo: String,
    val raw: String
)

data class PopFollow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class PopShield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class PopUrl(
    val name: String,
    val size: Int,
    val url: String
)