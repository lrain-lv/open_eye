package com.app.eye

import com.app.eye.ui.entity.BrandApiRequest
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.GsonUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow


fun main(args: Array<String>) {
    runBlocking {
        GlobalScope.launch { // 在后台启动一个新的协程并继续
            delay(1000L)
            println("World!")
        }
        println("Hello,")

        delay(5000L)
    }
}

suspend fun simple(): List<Int> {
    delay(3000) // 假装我们在这里做了一些异步的事情
    return listOf(1, 2, 3)
}

