package com.app.eye.http

object Constant {

    const val COMMON_BASE_URL = "http://baobab.kaiyanapp.com/api/"
    const val RECOMMEND_BASE_URL = "http://api.eyepetizer.net/"

    const val CONFIGS =
        "v2/configs?model=Android&deviceTypeOs=29&deviceImsi=0&deviceBrand=HUAWEI&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val RECOMMEND = "http://api.eyepetizer.net/v1/card/page/get_page"
    const val DISCOVERY =
        "http://baobab.kaiyanapp.com/api/v7/index/tab/discovery?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"
    const val DAILY =
        "http://baobab.kaiyanapp.com/api/v5/index/tab/feed?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val GET_PAGE = "v1/card/page/get_page"
}