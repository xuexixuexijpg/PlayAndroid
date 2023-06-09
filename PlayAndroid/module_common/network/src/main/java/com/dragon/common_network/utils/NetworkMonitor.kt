package com.dragon.common_network.utils

import com.dragon.common_network.state.NetworkState
import kotlinx.coroutines.flow.Flow

/**
 * 网络连接状态
 */
interface NetworkMonitor {
    //是否连接 以及所处网络状态
    val isOnline: Flow<NetworkState>
}