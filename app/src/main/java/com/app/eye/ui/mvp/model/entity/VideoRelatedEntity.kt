package com.app.eye.ui.mvp.model.entity

data class VideoRelatedEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<VrItem>,
    val nextPageUrl: Any,
    val total: Int
)

data class VrItem(
    val adIndex: Int,
    val `data`: VrData,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
)

data class VrData(
    val ad: Boolean,
    val adTrack: Any,
    val author: VrAuthor,
    val brandWebsiteInfo: Any,
    val campaign: Any,
    val category: String,
    val collected: Boolean,
    val consumption: VrConsumption,
    val count: Int,
    val cover: VrCover,
    val dataType: String,
    val date: Long,
    val description: String,
    val descriptionEditor: String,
    val descriptionPgc: Any,
    val duration: Int,
    val favoriteAdTrack: Any,
    val footer: Any,
    val header: Any,
    val id: Int,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val itemList: MutableList<VrItemX>,
    val label: Any,
    val labelList: MutableList<Any>,
    val lastViewTime: Any,
    val library: String,
    val playInfo: MutableList<VrPlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val promotion: Any,
    val provider: VrProvider,
    val reallyCollected: Boolean,
    val recallSource: String,
    val recall_source: String,
    val releaseTime: Long,
    val remark: Any,
    val resourceType: String,
    val searchWeight: Int,
    val shareAdTrack: Any,
    val slogan: Any,
    val src: Int,
    val subtitles: MutableList<Any>,
    val tags: MutableList<VrTag>,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: Any,
    val type: String,
    val videoPosterBean: Any,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: VrWebUrl
)

data class VrAuthor(
    val adTrack: Any,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: VrFollow,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: VrShield,
    val videoNum: Int
)

data class VrConsumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class VrCover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: Any,
    val sharing: Any
)

data class VrItemX(
    val adIndex: Int,
    val `data`: VrDataX,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
)

data class VrPlayInfo(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: MutableList<VrUrl>,
    val width: Int
)

data class VrProvider(
    val alias: String,
    val icon: String,
    val name: String
)

data class VrTag(
    val actionUrl: String,
    val adTrack: Any,
    val bgPicture: String,
    val childTagIdList: Any,
    val childTagList: Any,
    val communityIndex: Int,
    val desc: Any,
    val haveReward: Boolean,
    val headerImage: String,
    val id: Int,
    val ifNewest: Boolean,
    val name: String,
    val newestEndTime: Any,
    val tagRecType: String
)

data class VrWebUrl(
    val forWeibo: String,
    val raw: String
)

data class VrFollow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class VrShield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class VrDataX(
    val actionUrl: Any,
    val contentType: String,
    val dataType: String,
    val message: String,
    val nickname: String
)

data class VrUrl(
    val name: String,
    val size: Int,
    val url: String
)