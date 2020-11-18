package com.app.eye.ui.mvp.model.entity

data class BrandWallEntity(
    val code: String,
    val message: BrandMessage,
    val result: BrandResult
)

data class BrandMessage(
    val action: String,
    val content: String
)

data class BrandResult(
    val card_list: MutableList<BrandCard>,
    val item_count: Int,
    val item_list: MutableList<BrandMetro>,
    val last_item_id: String?,
    val page_info: BrandPageInfo
)

data class BrandCard(
    val card_data: BrandCardData,
    val card_id: Int,
    val interaction: BrandInteraction,
    val style: BrandStyleXXXX,
    val tracking_data: BrandTrackingDataXXX,
    val type: String
)


data class BrandPageInfo(
    val title: String
)

data class BrandCardData(
    val body: BrandBody,
    val footer: BrandFooter,
    val header: BrandHeader
)

data class BrandInteraction(
    val scroll: String
)

data class BrandStyleXXXX(
    val border: String,
    val margin: BrandMargin,
    val mode: String,
    val padding: BrandPaddingXX
)

class BrandTrackingDataXXX(
)

data class BrandBody(
    val api_request: BrandApiRequest,
    val metro_list: MutableList<BrandMetro>
)

data class BrandFooter(
    val center: MutableList<Any>,
    val left: MutableList<Any>,
    val right: MutableList<Any>
)

data class BrandHeader(
    val center: MutableList<Any>,
    val left: MutableList<BrandLeft>,
    val right: MutableList<BrandRight>,
    val style: BrandStyleXXX
)

data class BrandApiRequest(
    val params: BrandParams,
    val url: String
)

data class BrandMetro(
    val link: String,
    val metro_data: BrandMetroData,
    val metro_id: Int,
    val show_duration: Int,
    val style: BrandStyle,
    val tracking_data: BrandTrackingData,
    val type: String
)

data class BrandItem(
    val link: String,
    val metro_data: BrandMetroDataXXX,
    val metro_id: Int,
    val style: BrandStyleXXXXX,
    val tracking_data: BrandTrackingDataXXXX,
    val type: String
)


data class BrandParams(
    val card: String,
    val card_index: Int,
    val last_item_id: String,
    val material_index: Int,
    val material_relative_index: Int,
    val num: Int,
    val start_last_item_id: String,
    val type: String
)

data class BrandMetroData(
    val author: BrandAuthor,
    val batch_custom_tracking_data: MutableList<BrandBatchCustomTrackingData>,
    val cover: BrandCover,
    val custom_tracking_data: BrandCustomTrackingData,
    val duration: BrandDuration,
    val hot_value: Int,
    val more: BrandMore,
    val play_ctrl: BrandPlayCtrl,
    val play_url: String,
    val recommend_level: String,
    val resource_id: Int,
    val resource_type: String,
    val tags: MutableList<BrandTag>,
    val title: String,
    val url: String,
    val video_id: Int
)

data class BrandStyle(
    val metro_to_screen_ratio: Double,
    val padding: BrandPadding,
    val size: BrandSize,
    val tpl_label: String
)

data class BrandTrackingData(
    val click: MutableList<BrandClick>,
    val show: MutableList<BrandShow>
)

data class BrandAuthor(
    val avatar: BrandAvatar,
    val description: String,
    val followed: Boolean,
    val link: String,
    val nick: String,
    val type: String,
    val uid: Int
)

data class BrandBatchCustomTrackingData(
    val data_source: String,
    val item_id: Int,
    val item_type: String,
    val recall_source: String
)

data class BrandCover(
    val img_info: BrandImgInfoX,
    val url: String
)

data class BrandCustomTrackingData(
    val data_source: String,
    val recall_source: String
)

data class BrandDuration(
    val text: String,
    val value: Int
)

data class BrandMore(
    val enable_cache: Boolean,
    val enable_follow: Boolean,
    val share_platform: MutableList<String>
)

data class BrandPlayCtrl(
    val autoplay: Boolean,
    val autoplay_times: Int,
    val need_wifi: Boolean
)

data class BrandTag(
    val id: Int,
    val link: String,
    val title: String
)

data class BrandAvatar(
    val img_info: BrandImgInfo,
    val url: String
)

data class BrandImgInfo(
    val height: Int,
    val scale: Int,
    val width: Int
)

data class BrandImgInfoX(
    val height: Int,
    val scale: Double,
    val width: Int
)

data class BrandPadding(
    val bottom: Int,
    val left: Int,
    val right: Int,
    val top: Int
)

data class BrandSize(
    val height: Int,
    val scale: Double,
    val width: Int
)

data class BrandClick(
    val `data`: BrandData,
    val sdk: String
)

data class BrandShow(
    val `data`: DataX,
    val sdk: String
)

data class BrandData(
    val card_id: Int,
    val card_index: Int,
    val card_title: String,
    val card_type: String,
    val clickUrl: String,
    val click_action: String,
    val click_action_url: String,
    val dev_recommend_recall_type: String,
    val element_id: Int,
    val element_index: Int,
    val element_label: String,
    val element_title: String,
    val element_type: String,
    val monitorPositions: String,
    val needExtraParams: MutableList<Any>,
    val organization: String,
    val page_type: String,
    val playUrl: String,
    val relative_index: Int,
    val viewUrl: String
)

data class BrandDataX(
    val card_id: Int,
    val card_index: Int,
    val card_title: String,
    val card_type: String,
    val clickUrl: String,
    val click_action_url: String,
    val dev_recommend_recall_type: String,
    val element_id: Int,
    val element_index: Int,
    val element_label: String,
    val element_title: String,
    val element_type: String,
    val monitorPositions: String,
    val needExtraParams: MutableList<Any>,
    val organization: String,
    val page_type: String,
    val playUrl: String,
    val relative_index: Int,
    val viewUrl: String
)

data class BrandLeft(
    val metro_data: BrandMetroDataX,
    val metro_id: Int,
    val style: BrandStyleX,
    val tracking_data: BrandTrackingDataX,
    val type: String
)

data class BrandRight(
    val metro_data: BrandMetroDataXX,
    val metro_id: Int,
    val style: BrandStyleXX,
    val tracking_data: BrandTrackingDataXX,
    val type: String
)

data class BrandStyleXXX(
    val padding: BrandPaddingX
)

data class BrandMetroDataX(
    val text: String
)

data class BrandStyleX(
    val tpl_label: String
)

class BrandTrackingDataX(
)

data class BrandMetroDataXX(
    val link: String,
    val text: String
)

data class BrandStyleXX(
    val tpl_label: String
)

data class BrandTrackingDataXX(
    val click: MutableList<BrandClickX>,
    val show: MutableList<BrandShowX>
)

data class BrandClickX(
    val `data`: BrandDataXX,
    val sdk: String
)

data class BrandShowX(
    val `data`: BrandDataXXX,
    val sdk: String
)

data class BrandDataXX(
    val card_id: Int,
    val card_index: Int,
    val card_title: String,
    val card_type: String,
    val click_action: String,
    val click_action_url: String,
    val click_name: String,
    val page_type: String
)

data class BrandDataXXX(
    val card_id: Int,
    val card_index: Int,
    val card_title: String,
    val card_type: String,
    val click_action_url: String,
    val page_type: String
)

data class BrandPaddingX(
    val bottom: Int,
    val left: Int,
    val right: Int,
    val top: Int
)

data class BrandMargin(
    val bottom: Int,
    val left: Int,
    val right: Int,
    val top: Int
)

data class BrandPaddingXX(
    val bottom: Int,
    val left: Int,
    val right: Int,
    val top: Int
)

data class BrandMetroDataXXX(
    val author: BrandAuthorX,
    val batch_custom_tracking_data: MutableList<BrandBatchCustomTrackingDataX>,
    val cover: BrandCoverX,
    val custom_tracking_data: BrandCustomTrackingDataX,
    val duration: BrandDurationX,
    val hot_value: Int,
    val more: BrandMoreX,
    val play_ctrl: BrandPlayCtrlX,
    val play_url: String,
    val recommend_level: String,
    val resource_id: Int,
    val resource_type: String,
    val tags: MutableList<Any>,
    val title: String,
    val video_id: Int
)

data class BrandStyleXXXXX(
    val tpl_label: String
)

data class BrandTrackingDataXXXX(
    val click: MutableList<BrandClickXX>,
    val show: MutableList<BrandShowXX>
)

data class BrandAuthorX(
    val avatar: BrandAvatarX,
    val description: String,
    val followed: Boolean,
    val link: String,
    val nick: String,
    val type: String,
    val uid: Int
)

data class BrandBatchCustomTrackingDataX(
    val data_source: String,
    val item_id: Int,
    val item_type: String,
    val recall_source: String
)

data class BrandCoverX(
    val img_info: BrandImgInfoXXX,
    val url: String
)

data class BrandCustomTrackingDataX(
    val data_source: String,
    val recall_source: String
)

data class BrandDurationX(
    val text: String,
    val value: Int
)

data class BrandMoreX(
    val enable_cache: Boolean,
    val enable_follow: Boolean,
    val share_platform: MutableList<String>
)

data class BrandPlayCtrlX(
    val autoplay: Boolean,
    val autoplay_times: Int,
    val need_wifi: Boolean
)

data class BrandAvatarX(
    val img_info: BrandImgInfoXX,
    val url: String
)

data class BrandImgInfoXX(
    val height: Int,
    val scale: Int,
    val width: Int
)

data class BrandImgInfoXXX(
    val height: Int,
    val scale: Double,
    val width: Int
)

data class BrandClickXX(
    val `data`: BrandDataXXXX,
    val sdk: String
)

data class BrandShowXX(
    val `data`: BrandDataXXXXX,
    val sdk: String
)

data class BrandDataXXXX(
    val card_id: Int,
    val card_index: Int,
    val card_title: String,
    val card_type: String,
    val clickUrl: String,
    val click_action: String,
    val click_action_url: String,
    val dev_recommend_recall_type: String,
    val element_id: Int,
    val element_index: Int,
    val element_label: String,
    val element_title: String,
    val element_type: String,
    val monitorPositions: String,
    val needExtraParams: MutableList<Any>,
    val organization: String,
    val page_type: String,
    val playUrl: String,
    val relative_index: Int,
    val viewUrl: String
)

data class BrandDataXXXXX(
    val card_id: Int,
    val card_index: Int,
    val card_title: String,
    val card_type: String,
    val clickUrl: String,
    val click_action_url: String,
    val dev_recommend_recall_type: String,
    val element_id: Int,
    val element_index: Int,
    val element_label: String,
    val element_title: String,
    val element_type: String,
    val monitorPositions: String,
    val needExtraParams: MutableList<Any>,
    val organization: String,
    val page_type: String,
    val playUrl: String,
    val relative_index: Int,
    val viewUrl: String
)