package com.app.eye


fun main(args: Array<String>) {
    val stringToMp =
        stringToMp("http://baobab.kaiyanapp.com/api/v5/index/tab/feed?date=1603242000000&num=2")
    print(stringToMp)
}


private fun stringToMp(s: String): Map<String, String> {
    var map = hashMapOf<String, String>()
    val index = s.indexOf("?")
    val result = s.substring(index + 1)
    val split = result.split("&")
    split.forEach {
        map[it.split("=")[0]] = it.split("=")[1]
    }
    return map
}
