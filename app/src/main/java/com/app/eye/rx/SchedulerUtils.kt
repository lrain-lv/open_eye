package com.app.eye.rx

import com.app.eye.rx.scheduler.IoMainScheduler

object SchedulerUtils {
    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
}