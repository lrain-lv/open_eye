package com.app.eye

import com.app.eye.rx.formToMap
import com.blankj.utilcode.util.EncodeUtils


fun main(args: Array<String>) {
    val s =
        "title=%e6%af%8f%e6%97%a5%e6%96%b0%e9%b2%9c%e8%b5%84%e8%ae%af&url=https%3a%2f%2fwww.kaiyanapp.com%2fnew_article.html%3fnid%3d2252%26shareable%3dtrue%26rid%3d623"
    val formToMap = s.formToMap()
    println(formToMap.toString())

    val urlDecode = EncodeUtils.urlDecode(s)
    println(urlDecode)
}
