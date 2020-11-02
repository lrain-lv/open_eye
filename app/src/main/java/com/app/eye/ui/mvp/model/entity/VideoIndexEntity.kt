package com.app.eye.ui.mvp.model.entity

data class VideoIndexEntity(
    val ad: Boolean,
    val author: ViAuthor,
    val category: String,
    val collected: Boolean,
    val consumption: ViConsumption,
    val cover: ViCover,
    val dataType: String,
    val date: Long,
    val description: String,
    val descriptionEditor: String,
    val duration: Int,
    val id: Int,
    val idx: Int,
    val ifLimitVideo: Boolean,
    val lastViewTime: Any,
    val library: String,
    val playInfo: MutableList<ViPlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val promotion: Any,
    val provider: ViProvider,
    val reallyCollected: Boolean,
    val recallSource: Any,
    val recall_source: Any,
    val releaseTime: Long,
    val remark: Any,
    val resourceType: String,
    val searchWeight: Int,
    val tags: MutableList<ViTag>,
    val title: String,
    val type: String,
    val webUrl: ViWebUrl
)

data class ViAuthor(
    val adTrack: Any,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: ViFollow,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: ViShield,
    val videoNum: Int
)

data class ViConsumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class ViCover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: String,
    val sharing: Any
)

data class ViPlayInfo(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: MutableList<ViUrl>,
    val width: Int
)

data class ViProvider(
    val alias: String,
    val icon: String,
    val name: String
)

data class ViTag(
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

data class ViWebUrl(
    val forWeibo: String,
    val raw: String
)

data class ViFollow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class ViShield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class ViUrl(
    val name: String,
    val size: Int,
    val url: String
)