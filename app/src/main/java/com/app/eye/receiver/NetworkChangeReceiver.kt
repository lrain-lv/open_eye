package com.app.eye.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.eye.event.NetworkEvent
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SPUtils
import org.greenrobot.eventbus.EventBus

class NetworkChangeReceiver : BroadcastReceiver() {

    private val isLastConnect: Boolean by lazy {
        SPUtils.getInstance("eye").getBoolean("isLastConnect")
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (NetworkUtils.isConnected()) {
            if (!isLastConnect) {
                EventBus.getDefault().post(NetworkEvent(true))
            }
        } else {
            EventBus.getDefault().post(NetworkEvent(false))
        }
    }
}