package com.app.eye

import com.blankj.utilcode.util.EncodeUtils


fun main(args: Array<String>) {
    val link ="eyepetizer://lightTopic/detail/121?title=%E6%B3%B0%E5%9B%BD%E5%B9%BF%E5%91%8A%E7%B2%BE%E9%80%89"
    val index = link.indexOf("?")
    val lastindex = link.lastIndexOf("/")
    val id = link.substring(lastindex+1,index)
    print(id)
}
