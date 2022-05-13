
package com.dragon.common_utils.ext

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * 广播的扩展 flow
 */
fun Context.flowBroadcasts(intentFilter: IntentFilter): Flow<Intent> {
    val resultChannel = MutableStateFlow(Intent())

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            resultChannel.value = intent
        }
    }

    //注册和反注册
    return resultChannel.onStart { registerReceiver(receiver, intentFilter) }
        .onCompletion { unregisterReceiver(receiver) }
}
