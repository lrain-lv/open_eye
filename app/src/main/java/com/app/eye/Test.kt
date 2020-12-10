package com.app.eye

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
 runBlocking {
     test()
 }


}

suspend fun test() {
    flowOf("haha")
        .onCompletion { print(1) }
        .collect { println(it) }
}





