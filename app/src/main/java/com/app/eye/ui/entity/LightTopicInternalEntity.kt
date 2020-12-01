package com.app.eye.ui.entity

data class LightTopicInternalEntity(
    val brief: String,
    val count: Int,
    val headerImage: String,
    val id: Int,
    val itemList: MutableList<LightItem>,
    val shareLink: String,
    val text: String
)

data class LightItem(
    val adIndex: Int,
    val `data`: LightData,
    val id: Int,
    val type: String
)

data class LightData(
    val content: LightContent,
    val dataType: String,
    val header: LightHeader
)

data class LightContent(
    val adIndex: Int,
    val `data`: LightDataX,
    val id: Int,
    val type: String
)

data class LightHeader(
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

data class LightDataX(
    val ad: Boolean,
    val author: LightAuthor,
    val brandWebsiteInfo: Any,
    val campaign: Any,
    val category: String,
    val collected: Boolean,
    val consumption: LightConsumption,
    val cover: LightCover,
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
    val lastViewTime: Any,
    val library: String,
    val playInfo: MutableList<LightPlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val provider: LightProvider,
    val reallyCollected: Boolean,
    val recallSource: Any,
    val recall_source: Any,
    val releaseTime: Long,
    val remark: String,
    val resourceType: String,
    val searchWeight: Int,
    val shareAdTrack: Any,
    val slogan: String,
    val src: Any,
    val tags: MutableList<LightTag>?,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: String,
    val type: String,
    val videoPosterBean: Any,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: LightWebUrl
)

data class LightAuthor(
    val adTrack: Any,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: LightFollow,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: LightShield,
    val videoNum: Int
)

data class LightConsumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class LightCover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: String,
    val sharing: Any
)

data class LightPlayInfo(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: MutableList<LightUrl>,
    val width: Int
)

data class LightProvider(
    val alias: String,
    val icon: String,
    val name: String
)

data class LightTag(
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

data class LightWebUrl(
    val forWeibo: String,
    val raw: String
)

data class LightFollow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class LightShield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class LightUrl(
    val name: String,
    val size: Int,
    val url: String
)