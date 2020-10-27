package com.app.eye

import com.blankj.utilcode.util.EncodeUtils


fun main(args: Array<String>) {
    val s =
        "udid=7a5bd452383b40a1804972422eef008361cf87b8&vc=6030101&vn=6.3.10&deviceModel=ALP-AL00&first_channel=huawei&size=1080X1920&system_version_code=29&token=c75802a844a5c786&card_index=13&material_index=72&last_item_id=20&num=-1&type=guess_like&material_relative_index=1&start_last_item_id=0&card=%257B%2522card_id%2522%253A36%252C%2522type%2522%253A%2522call_metro_list%2522%252C%2522style%2522%253A%257B%2522padding%2522%253A%257B%2522top%2522%253A10%252C%2522right%2522%253A15%252C%2522bottom%2522%253A10%252C%2522left%2522%253A15%257D%257D%252C%2522interaction%2522%253A%257B%2522scroll%2522%253A%2522v-scroll%2522%257D%252C%2522card_data%2522%253A%257B%2522header%2522%253A%257B%2522left%2522%253A%255B%257B%2522metro_id%2522%253A827%252C%2522type%2522%253A%2522text%2522%252C%2522style%2522%253A%257B%2522tpl_label%2522%253A%2522card_title%2522%257D%252C%2522metro_data%2522%253A%257B%2522text%2522%253A%2522%25E6%259B%25B4%25E5%25A4%259A%25E6%258E%25A8%25E8%258D%2590%2522%257D%252C%2522tracking_data%2522%253A%257B%257D%257D%255D%252C%2522center%2522%253A%255B%255D%252C%2522right%2522%253A%255B%255D%257D%252C%2522body%2522%253A%257B%2522metro_list%2522%253A%255B%255D%257D%252C%2522footer%2522%253A%257B%2522left%2522%253A%255B%255D%252C%2522center%2522%253A%255B%255D%252C%2522right%2522%253A%255B%255D%257D%257D%252C%2522tracking_data%2522%253A%255B%255D%257D"
    val urlDecode = EncodeUtils.urlDecode("22%3A%5B%5D7D")
    println(urlDecode)
}
