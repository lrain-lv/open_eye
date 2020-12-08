package com.app.eye.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.datastore.createDataStore
import com.app.eye.AppHelper
import com.app.eye.event.NetworkEvent
import com.app.eye.utils.DataStoreUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SPUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus

class NetworkChangeReceiver : BroadcastReceiver() {

    private var isLastConnect: Boolean = false

    private val mainScope = MainScope()

    override fun onReceive(context: Context?, intent: Intent?) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                isLastConnect = DataStoreUtils.getInstance().readBooleanData("isLastConnect")
            }
            if (NetworkUtils.isConnected()) {
                if (!isLastConnect) {
                    EventBus.getDefault().post(NetworkEvent(true))
                }
            } else {
                EventBus.getDefault().post(NetworkEvent(false))
            }
        }

    }
}