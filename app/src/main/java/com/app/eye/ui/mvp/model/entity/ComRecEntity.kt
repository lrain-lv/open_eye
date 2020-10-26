package com.app.eye.ui.mvp.model.entity

data class ComRecEntity(
    val adExist: Boolean,
    val count: Int,
    var itemList: MutableList<ComItem>,
    val nextPageUrl: String,
    val total: Int
){
    override fun toString(): String {
        return "ComRecEntity(adExist=$adExist, count=$count, itemList=$itemList, nextPageUrl='$nextPageUrl', total=$total)"
    }
}

data class ComItem(
    val adIndex: Int,
    val `data`: ComData,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
){
//    override fun toString(): String {
//        return "ComItem(adIndex=$adIndex, `data`=$`data`, id=$id, tag=$tag, trackingData=$trackingData, type='$type')"
//    }
}

data class ComData(
    val adTrack: Any,
    val content: ComContent,
    val count: Int,
    val dataType: String,
    val footer: Any,
    val header: RecHeader,
    val itemList: MutableList<ItemX>
){
}

data class RecHeader(
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

data class ComContent(
    val adIndex: Int,
    val `data`: ComDataX,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
){
    override fun toString(): String {
        return "ComContent(adIndex=$adIndex, `data`=$`data`, id=$id, tag=$tag, trackingData=$trackingData, type='$type')"
    }
}

//data class ItemX(
//    val adIndex: Int,
//    val `data`: DataXX,
//    val id: Int,
//    val tag: Any,
//    val trackingData: Any,
//    val type: String
//)

data class ComDataX(
    val addWatermark: Boolean,
    val area: String,
    val checkStatus: String,
    val city: String,
    val collected: Boolean,
    val consumption: ComConsumption,
    val cover: ComCover,
    val createTime: Long,
    val dataType: String,
    val description: String,
    val height: Int,
    val id: Int,
    val ifMock: Boolean,
    val latitude: Double,
    val library: String,
    val longitude: Double,
    val owner: ComOwner,
    val privateMessageActionUrl: String,
    val reallyCollected: Boolean,
    val recentOnceReply: ComRecentOnceReply,
    val releaseTime: Long,
    val resourceType: String,
    val selectedTime: Long,
//    val status: Int,
    val tags: MutableList<ComTag>,
    val title: String,
    val uid: Int,
    val updateTime: Long,
    val url: String,
    val urls: MutableList<String>,
    val urlsWithWatermark: MutableList<String>,
    val validateResult: String,
    val validateStatus: String,
    val width: Int
)

data class ComConsumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class ComCover(
    val blurred: Any,
    val detail: String,
    val feed: String,
    val homepage: Any,
    val sharing: Any
)

data class ComOwner(
    val actionUrl: String,
    val area: Any,
    val avatar: String,
    val birthday: Any,
    val city: Any,
    val country: Any,
    val cover: String,
    val description: String,
    val expert: Boolean,
    val followed: Boolean,
    val gender: String,
    val ifPgc: Boolean,
    val job: Any,
    val library: String,
    val limitVideoOpen: Boolean,
    val nickname: String,
    val registDate: Long,
    val releaseDate: Long,
    val uid: Int,
    val university: Any,
    val userType: String
)

data class ComRecentOnceReply(
    val actionUrl: String,
    val contentType: Any,
    val dataType: String,
    val message: String,
    val nickname: String
)

data class ComTag(
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

data class ComDataXX(
    val actionUrl: String,
    val adTrack: MutableList<ComAdTrack>,
    val autoPlay: Boolean,
    val bgPicture: String,
    val dataType: String,
    val description: String,
    val header: ComHeader,
    val id: Int,
    val image: String,
    val label: ComLabel,
    val labelList: MutableList<ComLabelX>,
    val shade: Boolean,
    val subTitle: String,
    val title: String
)

data class ComAdTrack(
    val clickUrl: String,
    val id: Int,
    val monitorPositions: String,
    val needExtraParams: MutableList<String>,
    val organization: String,
    val playUrl: String,
    val viewUrl: String
)

data class ComHeader(
    val actionUrl: Any,
    val cover: Any,
    val description: Any,
    val font: Any,
    val icon: Any,
    val id: Int,
    val label: Any,
    val labelList: Any,
    val rightText: Any,
    val subTitle: Any,
    val subTitleFont: Any,
    val textAlign: String,
    val title: Any
)

data class ComLabel(
    val card: String,
    val detail: Any,
    val text: String
)

data class ComLabelX(
    val actionUrl: Any,
    val text: String
)