package com.app.eye

import com.blankj.utilcode.util.EncodeUtils


fun main(args: Array<String>) {
    val s =
        "title=%E5%85%A8%E9%83%A8%E8%B5%84%E8%AE%AF&url=http%3A%2F%2Fbaobab.kaiyanapp.com%2Fapi%2Fv7%2Finformation%2Flist"
    val urlDecode = EncodeUtils.urlDecode(s)
    println(urlDecode)
}
