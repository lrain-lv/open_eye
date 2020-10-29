package com.app.eye.http

object Constant {

    const val COMMON_BASE_URL = "http://baobab.kaiyanapp.com/"
    const val RECOMMEND_BASE_URL = "http://api.eyepetizer.net/"
    const val ACCOUNT_BASE_URL = "https://account.kaiyanapp.com/"

    const val LOGIN = "v1/api/login"

    const val CONFIGS =
        "api/v2/configs?model=Android&deviceTypeOs=29&deviceImsi=0&deviceBrand=HUAWEI&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val RECOMMEND = "http://api.eyepetizer.net/v1/card/page/get_page"
    const val DISCOVERY =
        "api/v7/index/tab/discovery?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val DAILY =
        "api/v5/index/tab/feed?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"
    const val HOT =
        "api/v3/queries/hot?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val PRE_SEARCH =
        "api/v3/search/preSearch?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&size=1080X1920&first_channel=huawei&last_channel=huawei&system_version_code=29&token=c75802a844a5c786"
    const val SEARCH =
        "api/v3/search?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&size=1080X1920&first_channel=huawei&last_channel=huawei&system_version_code=29&token=c75802a844a5c786"

    const val USER_AGREEMENT = "http://www.eyepetizer.net/agreement.html"

    //http://baobab.kaiyanapp.com/api/v5/index/tab/feed?date=1603155600000&num=2&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //http://baobab.kaiyanapp.com/api/v5/index/tab/feed?date=1602982800000&num=2&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
//http://baobab.kaiyanapp.com/api/v5/index/tab/feed?date=1602810000000&num=2&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    const val GET_PAGE = "v1/card/page/get_page"

    const val MESSAGE =
        "api/v3/messages?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val TOPIC_DETAIL =
        "api/v7/topic/detail?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&size=1080X1920&first_channel=huawei&last_channel=huawei&system_version_code=29&token=c75802a844a5c786"

    const val COM_REC =
        "api/v7/community/tab/rec?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val TAG_TAB =
        "api/v7/tag/tabList?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val TAG_CHILDTAB = "api/v7/tag/childTab"
    const val TOPIC_LIST =
        "api/v7/topic/list?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val TAG_INDEX =
        "api/v6/tag/index?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val TAG_VIDEO =
        "api/v1/tag/videos?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val TAG_DYNAMIC =
        "api/v6/tag/dynamics?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val CATEGORY =
        "api/v4/categories/all?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val SPECIAL_TOPIC =
        "api/v3/specialTopics?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val RANK_LIST =
        "api/v4/rankList/videos?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val INFORMATION_LIST =
        "api/v7/information/list?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    const val COM_ATTENTION =
        "api/v6/community/tab/follow?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29"

    //全部分类
    //http://baobab.kaiyanapp.com/api/v4/categories/all?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //专题策划
    //http://baobab.kaiyanapp.com/api/v3/specialTopics?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //本周榜单
    //周排行
    //http://baobab.kaiyanapp.com/api/v4/rankList/videos?strategy=weekly&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //月排行
    //http://baobab.kaiyanapp.com/api/v4/rankList/videos?strategy=monthly&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //总排行
//http://baobab.kaiyanapp.com/api/v4/rankList/videos?strategy=historical&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29

    //排行点击
    //http://baobab.kaiyanapp.com/api/v2/replies/video?videoId=220005&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //http://baobab.kaiyanapp.com/api/v4/video/related?id=220005&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //排行头像点击
    //https://baobab.kaiyanapp.com/api/v5/userInfo/tab/index?id=2003&userType=PGC&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //https://baobab.kaiyanapp.com/api/v5/userInfo/tab/works?uid=301106124&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //https://baobab.kaiyanapp.com/api/v5/userInfo/tab?id=2003&userType=PGC&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&size=1080X1920&first_channel=huawei&last_channel=huawei&system_version_code=29&token=c75802a844a5c786

    //推荐 https://api.eyepetizer.net/v1/card/metro/call_metro_list
    //udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&first_channel=huawei&size=1080X1920&system_version_code=29&token=c75802a844a5c786&card_index=13&material_index=72&last_item_id=20&num=-1&type=guess_like&material_relative_index=1&start_last_item_id=0&card=%257B%2522card_id%2522%253A36%252C%2522type%2522%253A%2522call_metro_list%2522%252C%2522style%2522%253A%257B%2522padding%2522%253A%257B%2522top%2522%253A10%252C%2522right%2522%253A15%252C%2522bottom%2522%253A10%252C%2522left%2522%253A15%257D%257D%252C%2522interaction%2522%253A%257B%2522scroll%2522%253A%2522v-scroll%2522%257D%252C%2522card_data%2522%253A%257B%2522header%2522%253A%257B%2522left%2522%253A%255B%257B%2522metro_id%2522%253A827%252C%2522type%2522%253A%2522text%2522%252C%2522style%2522%253A%257B%2522tpl_label%2522%253A%2522card_title%2522%257D%252C%2522metro_data%2522%253A%257B%2522text%2522%253A%2522%25E6%259B%25B4%25E5%25A4%259A%25E6%258E%25A8%25E8%258D%2590%2522%257D%252C%2522tracking_data%2522%253A%257B%257D%257D%255D%252C%2522center%2522%253A%255B%255D%252C%2522right%2522%253A%255B%255D%257D%252C%2522body%2522%253A%257B%2522metro_list%2522%253A%255B%255D%257D%252C%2522footer%2522%253A%257B%2522left%2522%253A%255B%255D%252C%2522center%2522%253A%255B%255D%252C%2522right%2522%253A%255B%255D%257D%257D%252C%2522tracking_data%2522%253A%255B%255D%257D
    //udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&first_channel=huawei&size=1080X1920&system_version_code=29&token=c75802a844a5c786&card_index=13&material_index=72&last_item_id=30&num=-1&type=guess_like&material_relative_index=1&start_last_item_id=0&card=%257B%2522card_id%2522%253A36%252C%2522type%2522%253A%2522call_metro_list%2522%252C%2522style%2522%253A%257B%2522padding%2522%253A%257B%2522top%2522%253A10%252C%2522right%2522%253A15%252C%2522bottom%2522%253A10%252C%2522left%2522%253A15%257D%257D%252C%2522interaction%2522%253A%257B%2522scroll%2522%253A%2522v-scroll%2522%257D%252C%2522card_data%2522%253A%257B%2522header%2522%253A%257B%2522left%2522%253A%255B%257B%2522metro_id%2522%253A827%252C%2522type%2522%253A%2522text%2522%252C%2522style%2522%253A%257B%2522tpl_label%2522%253A%2522card_title%2522%257D%252C%2522metro_data%2522%253A%257B%2522text%2522%253A%2522%25E6%259B%25B4%25E5%25A4%259A%25E6%258E%25A8%25E8%258D%2590%2522%257D%252C%2522tracking_data%2522%253A%257B%257D%257D%255D%252C%2522center%2522%253A%255B%255D%252C%2522right%2522%253A%255B%255D%257D%252C%2522body%2522%253A%257B%2522metro_list%2522%253A%255B%255D%257D%252C%2522footer%2522%253A%257B%2522left%2522%253A%255B%255D%252C%2522center%2522%253A%255B%255D%252C%2522right%2522%253A%255B%255D%257D%257D%252C%2522tracking_data%2522%253A%255B%255D%257D
    //udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&first_channel=huawei&size=1080X1920&system_version_code=29&token=c75802a844a5c786&card_index=13&material_index=72&last_item_id=50&num=-1&type=guess_like&material_relative_index=1&start_last_item_id=0&card=%257B%2522card_id%2522%253A36%252C%2522type%2522%253A%2522call_metro_list%2522%252C%2522style%2522%253A%257B%2522padding%2522%253A%257B%2522top%2522%253A10%252C%2522right%2522%253A15%252C%2522bottom%2522%253A10%252C%2522left%2522%253A15%257D%257D%252C%2522interaction%2522%253A%257B%2522scroll%2522%253A%2522v-scroll%2522%257D%252C%2522card_data%2522%253A%257B%2522header%2522%253A%257B%2522left%2522%253A%255B%257B%2522metro_id%2522%253A827%252C%2522type%2522%253A%2522text%2522%252C%2522style%2522%253A%257B%2522tpl_label%2522%253A%2522card_title%2522%257D%252C%2522metro_data%2522%253A%257B%2522text%2522%253A%2522%25E6%259B%25B4%25E5%25A4%259A%25E6%258E%25A8%25E8%258D%2590%2522%257D%252C%2522tracking_data%2522%253A%257B%257D%257D%255D%252C%2522center%2522%253A%255B%255D%252C%2522right%2522%253A%255B%255D%257D%252C%2522body%2522%253A%257B%2522metro_list%2522%253A%255B%255D%257D%252C%2522footer%2522%253A%257B%2522left%2522%253A%255B%255D%252C%2522center%2522%253A%255B%255D%252C%2522right%2522%253A%255B%255D%257D%257D%252C%2522tracking_data%2522%253A%255B%255D%257D

    // hot http://baobab.kaiyanapp.com/api/v3/queries/hot?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //艺术 http://baobab.kaiyanapp.com/api/v3/search?query=艺术&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //社区推荐 http://baobab.kaiyanapp.com/api/v7/community/tab/rec?refreshCount=1&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //http://baobab.kaiyanapp.com/api/v7/community/tab/rec?startScore=1603371432000&pageCount=2&refreshCount=2&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29

    //社区 关注
    //http://baobab.kaiyanapp.com/api/v6/community/tab/follow?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
//http://baobab.kaiyanapp.com/api/v6/community/tab/follow?start=10&num=10&newest=false&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
//http://baobab.kaiyanapp.com/api/v6/community/tab/follow?start=20&num=10&newest=false&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29

    //推送
    //http://baobab.kaiyanapp.com/api/v3/messages?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //http://baobab.kaiyanapp.com/api/v3/messages?start=10&num=10&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //topic
    // https://baobab.kaiyanapp.com/api/v7/topic/detail?id=407&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&size=1080X1920&first_channel=huawei&last_channel=huawei&system_version_code=29&token=c75802a844a5c786

    //私信
    //https://baobab.kaiyanapp.com/api/v7/topic/detail?id=406&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&size=1080X1920&first_channel=huawei&last_channel=huawei&system_version_code=29&token=c75802a844a5c786

    //发现好友
    //http://baobab.kaiyanapp.com/api/v5/userInfo/recFriend?uid=304200827&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //http://baobab.kaiyanapp.com/api/v5/userInfo/recFriend?uid=304200827&start=10&num=10&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //http://baobab.kaiyanapp.com/api/v5/userInfo/recFriend?uid=304200827&start=20&num=10&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29

    //tab
    //https://baobab.kaiyanapp.com/api/v5/userInfo/tab?id=300713682&userType=NORMAL&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&size=1080X1920&first_channel=huawei&last_channel=huawei&system_version_code=29&token=c75802a844a5c786
//https://baobab.kaiyanapp.com/api/v5/userInfo/tab/works?uid=300713682&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29

    //http://baobab.kaiyanapp.com/api/v2/configs?model=Android&deviceTypeOs=29&deviceImsi=0&deviceBrand=HUAWEI&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29

    //主题广场
    //http://baobab.kaiyanapp.com/api/v7/tag/tabList?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //http://baobab.kaiyanapp.com/api/v7/tag/childTab/0?isRecTab=true&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //http://baobab.kaiyanapp.com/api/v7/tag/childTab/1?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //http://baobab.kaiyanapp.com/api/v7/tag/childTab/1?start=10&num=10&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //点击api 推荐
    //http://baobab.kaiyanapp.com/api/v6/tag/index?id=1465&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //http://baobab.kaiyanapp.com/api/v1/tag/videos?id=1465&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //http://baobab.kaiyanapp.com/api/v1/tag/videos?start=10&num=10&strategy=date&id=1465&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //广场
    //http://baobab.kaiyanapp.com/api/v6/tag/dynamics?id=1465&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //http://baobab.kaiyanapp.com/api/v6/tag/dynamics?start=10&num=10&id=1465&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29

    //话题大厅
    //http://baobab.kaiyanapp.com/api/v7/topic/list?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //点击
    //https://baobab.kaiyanapp.com/api/v7/topic/detail?id=406&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&size=1080X1920&first_channel=huawei&last_channel=huawei&system_version_code=29&token=c75802a844a5c786
    //回复评论
    //http://baobab.kaiyanapp.com/api/v2/replies/video?videoId=406&type=topic&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29

    //私信列表
    //http://baobab.kaiyanapp.com/api/v2/private/message/list?udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29

    //发现好友
    //http://baobab.kaiyanapp.com/api/v5/userInfo/recFriend?uid=304200827&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
    //瀑布流点击
    //https://baobab.kaiyanapp.com/api/v2/video/556911?resourceType=ugc_picture&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&size=1080X1920&first_channel=huawei&last_channel=huawei&system_version_code=29&token=c75802a844a5c786
    //https://baobab.kaiyanapp.com/api/v2/video/556911?resourceType=ugc_picture&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&size=1080X1920&first_channel=huawei&last_channel=huawei&system_version_code=29&token=c75802a844a5c786
    //评论列表
    //http://baobab.kaiyanapp.com/api/v2/replies/video?videoId=572211&type=ugc_picture&udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&size=1080X1920&deviceModel=ALP-AL00&first_channel=huawei&last_channel=huawei&system_version_code=29
}
