package com.app.eye.ui.mvp.model.entity

data class RankEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<RankItem>,
    val nextPageUrl: String,
    val total: Int
)

data class RankItem(
    val adIndex: Int,
    val `data`: RankData,
    val id: Int,
    val type: String
)

data class RankData(
    val content: RankContent,
    val dataType: String,
    val header: RankHeader
)

data class RankContent(
    val adIndex: Int,
    val `data`: RankDataX,
    val id: Int,
    val type: String
)

data class RankHeader(
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

data class RankDataX(
    val ad: Boolean,
    val author: RankAuthor,
    val campaign: Any,
    val category: String,
    val collected: Boolean,
    val consumption: RankConsumption,
    val cover: RankCover,
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
    val lastViewTime: Any,
    val library: String,
    val playInfo: MutableList<RankPlayInfo>,
    val playUrl: String,
    val played: Boolean,
    val playlists: Any,
    val promotion: Any,
    val provider: RankProvider,
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
    val tags: MutableList<RankTag>,
    val thumbPlayUrl: Any,
    val title: String,
    val titlePgc: Any,
    val type: String,
    val videoPosterBean: Any,
    val waterMarks: Any,
    val webAdTrack: Any,
    val webUrl: RankWebUrl
)

data class RankAuthor(
    val adTrack: Any,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: RankFollow,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: RankShield,
    val videoNum: Int
)

data class RankConsumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class RankCover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: String,
    val sharing: Any
)

data class RankPlayInfo(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: MutableList<RankUrl>,
    val width: Int
)

data class RankProvider(
    val alias: String,
    val icon: String,
    val name: String
)

data class RankTag(
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

data class RankWebUrl(
    val forWeibo: String,
    val raw: String
)

data class RankFollow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)

data class RankShield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)

data class RankUrl(
    val name: String,
    val size: Int,
    val url: String
)