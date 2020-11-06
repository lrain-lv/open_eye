package com.app.eye.ui.mvp.model.entity

data class ConfigEntity(
    val androidPlayer: AndroidPlayer,
    val autoCache: AutoCache,
    val campaignInDetail: CampaignInDetail,
    val campaignInFeed: CampaignInFeed,
    val consumption: ConfigConsumption,
    val dialog: Dialog,
    val dialogInteractiveAd: DialogInteractiveAd,
    val firstLaunch: FirstLaunch,
    val homepage: Homepage,
    val interceptorAdOutActionUrl: InterceptorAdOutActionUrl,
    val isHomepageSearchEnabled: Boolean,
    val issueCover: IssueCover,
    val launch: Launch,
    val log: Log,
    val loginOptions: List<LoginOption>,
    val preload: Preload,
    val preloadVideos: PreloadVideos,
    val privacyPolicy: PrivacyPolicy,
    val privateMessageRefresh: PrivateMessageRefresh,
    val profilePageAd: ProfilePageAd,
    val push: Push,
    val pushInfo: PushInfo,
    val reply: Reply,
    val roamingCalendar: RoamingCalendar,
    val shareTitle: ShareTitle,
    val startImageGroup: StartImageGroup,
    val startPage: StartPage,
    val startPageAd: StartPageAd,
    val startPageVideo: StartPageVideo,
    val startPageVideoConfig: StartPageVideoConfig,
    val thirdPartyAd: ThirdPartyAd,
    val version: String,
    val videoAdsConfig: VideoAdsConfig
)

data class AndroidPlayer(
    val playerList: List<String>,
    val version: String
)

data class AutoCache(
    val forceOff: Boolean,
    val offset: Int,
    val version: String,
    val videoNum: Int
)

data class CampaignInDetail(
    val actionUrl: String,
    val available: Boolean,
    val imageUrl: String,
    val showType: String,
    val version: String
)

data class CampaignInFeed(
    val actionUrl: String,
    val available: Boolean,
    val imageUrl: String,
    val version: String
)

data class ConfigConsumption(
    val display: Boolean,
    val version: String
)

data class Dialog(
    val actionUrl: String,
    val adTrack: List<Any>,
    val animateImage: String,
    val animateTimeLength: Int,
    val animateType: String,
    val buttonList: List<Button>,
    val citys: String,
    val content: String,
    val endTime: Long,
    val id: Int,
    val image: String,
    val showTimes: Int,
    val startTime: Long,
    val title: String,
    val version: String
)

data class DialogInteractiveAd(
    val InteractiveImage: String,
    val InteractiveTime: Int,
    val adTrack: List<Any>,
    val androidActionUrl: String,
    val animationType: String,
    val buttonList: List<ButtonX>,
    val displayCount: Int,
    val endTime: Long,
    val id: Int,
    val imageUrl: String,
    val interactiveAdTrack: List<Any>,
    val iosActionUrl: String,
    val loadingMode: Int,
    val openSound: Boolean,
    val playUrl: String,
    val showImage: Boolean,
    val showLabel: Boolean,
    val startTime: Long,
    val title: String,
    val version: String
)

data class FirstLaunch(
    val showFirstDetail: Boolean,
    val version: String
)

data class Homepage(
    val cover: String,
    val version: String
)

data class InterceptorAdOutActionUrl(
    val controlSwitch: Boolean,
    val version: String
)

data class IssueCover(
    val issueLogo: IssueLogo,
    val version: String
)

data class Launch(
    val adTrack: List<Any>,
    val version: String
)

data class Log(
    val playLog: Boolean,
    val version: String
)

data class LoginOption(
    val iconUrl: String,
    val order: Int,
    val type: String
)

data class Preload(
    val on: Boolean,
    val version: String
)

data class PreloadVideos(
    val list: List<Any>,
    val version: String
)

data class PrivacyPolicy(
    val actionUrl: String,
    val buttonList: List<ButtonXX>,
    val content: String,
    val endTime: Long,
    val id: Int,
    val ifEnforcement: Boolean,
    val image: String,
    val showTimes: Int,
    val startTime: Long,
    val title: String,
    val version: String
)

data class PrivateMessageRefresh(
    val controlList: Int,
    val controlMsgDetail: Int,
    val version: String
)

data class ProfilePageAd(
    val actionUrl: String,
    val adTrack: List<Any>,
    val endTime: Long,
    val imageUrl: String,
    val startTime: Long,
    val version: String
)

data class Push(
    val endTime: Int,
    val message: String,
    val startTime: Int,
    val version: String
)

data class PushInfo(
    val normal: Any,
    val version: String
)

data class Reply(
    val on: Boolean,
    val version: String
)

data class RoamingCalendar(
    val actionUrl: String,
    val imageUrl: String,
    val isShow: Boolean,
    val version: String
)

data class ShareTitle(
    val qzone: String,
    val version: String,
    val wechatMoments: String,
    val weibo: String
)

data class StartImageGroup(
    val displayCount: Int,
    val endTime: Long,
    val id: Int,
    val list: List<ConfigContent>,
    val startTime: Long,
    val version: String
)

data class StartPage(
    val actionUrl: String,
    val imageUrl: String,
    val version: String
)

data class StartPageAd(
    val actionUrl: String,
    val adTrack: List<Any>,
    val adaptiveUrls: String,
    val blurredImageUrl: String,
    val buttonPosition: Int,
    val canSkip: Boolean,
    val countdown: Boolean,
    val displayCount: Int,
    val displayTimeDuration: Int,
    val endTime: Long,
    val id: Int,
    val imageUrl: String,
    val newImageUrl: String,
    val showBottomBar: Boolean,
    val startTime: Long,
    val version: String
)

data class StartPageVideo(
    val actionUrl: String,
    val adTrack: List<Any>,
    val adaptiveUrls: String,
    val canSkip: Boolean,
    val countdown: Boolean,
    val displayCount: Int,
    val displayTimeDuration: Int,
    val endTime: Long,
    val loadingMode: Int,
    val showImage: Boolean,
    val showImageTime: Int,
    val startTime: Long,
    val timeBeforeSkip: Int,
    val url: String,
    val version: String
)

data class StartPageVideoConfig(
    val list: List<Any>,
    val version: String
)

data class ThirdPartyAd(
    val controlSwitch: Boolean,
    val version: String
)

data class VideoAdsConfig(
    val list: List<X>,
    val version: String
)

data class Button(
    val actionUrl: String,
    val highlight: Boolean,
    val ifAdTrack: Boolean,
    val text: String
)

data class ButtonX(
    val actionUrl: String,
    val highlight: Boolean,
    val ifAdTrack: Boolean,
    val text: String
)

data class IssueLogo(
    val adapter: Boolean,
    val weekendExtra: String
)

data class ButtonXX(
    val actionUrl: String,
    val highlight: Boolean,
    val ifAdTrack: Boolean,
    val text: String
)

data class ConfigContent(
    val actionUrl: String,
    val actionUrlAndroid: String,
    val actionUrlIos: String,
    val adTrack: List<ConfigAdTrack>,
    val adaptiveUrls: String,
    val blurredImageUrl: String,
    val buttonPosition: Int,
    val canSkip: Boolean,
    val countdown: Boolean,
    val displayCount: Int,
    val displayTimeDuration: Int,
    val endTime: Long,
    val id: Int,
    val imageUrl: String,
    val moreImage: Boolean,
    val newImageUrl: String,
    val showBottomBar: Boolean,
    val startTime: Long,
    val status: String,
    val title: String
)

data class ConfigAdTrack(
    val clickUrl: String,
    val id: Int,
    val monitorPositions: String,
    val needExtraParams: List<String>,
    val organization: String,
    val playUrl: String,
    val viewUrl: String
)

data class X(
    val actionUrl: String,
    val adTrack: List<Any>,
    val adaptiveImageUrls: String,
    val adaptiveUrls: String,
    val canSkip: Boolean,
    val categoryId: Int,
    val countdown: Boolean,
    val cycleCount: Int,
    val description: String,
    val displayCount: Int,
    val displayTimeDuration: Int,
    val endTime: Long,
    val icon: String,
    val id: Int,
    val ifLinkage: Boolean,
    val imageUrl: String,
    val iosActionUrl: String,
    val linkageAdId: Int,
    val loadingMode: Int,
    val openSound: Boolean,
    val position: Int,
    val showActionButton: Boolean,
    val showImage: Boolean,
    val showImageTime: Int,
    val startTime: Long,
    val status: String,
    val timeBeforeSkip: Int,
    val title: String,
    val url: String,
    val videoAdType: String,
    val videoType: String
)