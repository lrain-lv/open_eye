package com.app.eye.ui.entity

import java.io.Serializable

data class ComRecEntity(
    val adExist: Boolean,
    val count: Int,
    var itemList: MutableList<ComItem>,
    var nextPageUrl: String?,
    val total: Int
) : Serializable {
    override fun toString(): String {
        return "ComRecEntity(adExist=$adExist, count=$count, itemList=$itemList, nextPageUrl='$nextPageUrl', total=$total)"
    }
}

data class ComItem(
    val adIndex: Int,
    val `data`: ComData,
    val id: Int,
    val type: String
): Serializable{
//    override fun toString(): String {
//        return "ComItem(adIndex=$adIndex, `data`=$`data`, id=$id, tag=$tag, trackingData=$trackingData, type='$type')"
//    }
}

data class ComData(
    val content: ComContent,
    val count: Int,
    val dataType: String,
    val header: RecHeader,
    val itemList: MutableList<ItemX>
): Serializable

data class RecHeader(
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
): Serializable

data class ComContent(
    val adIndex: Int,
    val `data`: ComDataX,
    val id: Int,
    val type: String
): Serializable{

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
    val description: String?,
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
    val urls: MutableList<String>?,
    val urlsWithWatermark: MutableList<String>,
    val validateResult: String,
    val validateStatus: String,
    val width: Int
): Serializable

data class ComConsumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
): Serializable

data class ComCover(
    val detail: String,
    val feed: String
):Serializable

data class ComOwner(
    val actionUrl: String,
    val avatar: String,
    val cover: String,
    val description: String,
    val expert: Boolean,
    val followed: Boolean,
    val gender: String,
    val ifPgc: Boolean,
    val library: String,
    val limitVideoOpen: Boolean,
    val nickname: String,
    val registDate: Long,
    val releaseDate: Long,
    val uid: Int,
    val userType: String
): Serializable

data class ComRecentOnceReply(
    val actionUrl: String,
    val dataType: String,
    val message: String,
    val nickname: String
): Serializable

data class ComTag(
    val actionUrl: String,
    val bgPicture: String,
    val communityIndex: Int,
    val desc: String,
    val haveReward: Boolean,
    val headerImage: String,
    val id: Int,
    val ifNewest: Boolean,
    val name: String,
    val tagRecType: String
): Serializable

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
): Serializable

data class ComAdTrack(
    val clickUrl: String,
    val id: Int,
    val monitorPositions: String,
    val needExtraParams: MutableList<String>,
    val organization: String,
    val playUrl: String,
    val viewUrl: String
): Serializable

data class ComHeader(
    val id: Int,
    val textAlign: String
): Serializable

data class ComLabel(
    val card: String,
    val text: String
): Serializable

data class ComLabelX(
    val actionUrl: String?,
    val text: String
): Serializable