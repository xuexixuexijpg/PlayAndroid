package com.dragon.common_network.state

/**
 * Author:Knight
 * Time:2022/4/22 14:35
 * Description:NetworkState
 */
enum class NetworkState {
    // Wi-Fi网络
    WIFI,
    // 移动蜂窝网络
    CELLULAR,
    // 以太网
    ETHERNET,
    //能连接 但无法判断
    OTHER_STATE,
    // 没有网络
    NONE
}