package com.dragon.common_network.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.core.content.getSystemService
import com.dragon.common_network.state.NetworkState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject

class ConnectivityManagerNetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkMonitor {

    override val isOnline: Flow<NetworkState> = callbackFlow {

        val connectivityManager = context.getSystemService<ConnectivityManager>()
        //注册网络变化监听
        val callback = object : NetworkCallback() {

            override fun onAvailable(network: Network) {
                channel.trySend(connectivityManager.isCurrentlyConnected())
            }

            override fun onLost(network: Network) {
                channel.trySend(NetworkState.NONE)
            }
        }
        connectivityManager?.registerNetworkCallback(
            NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build(),
            callback
        )

        channel.trySend(connectivityManager.isCurrentlyConnected())

        awaitClose {
            connectivityManager?.unregisterNetworkCallback(callback)
        }

    }.conflate()


    @Suppress("DEPRECATION")
    private fun ConnectivityManager?.isCurrentlyConnected() = when (this) {
        null -> NetworkState.NONE
        else -> when {
            //23
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                if (activeNetwork == null) NetworkState.NONE
                else {
                    if (getNetworkCapabilities(activeNetwork) == null) NetworkState.NONE
                    else {
                        val networkCapabilities = getNetworkCapabilities(activeNetwork)!!
                        if (!networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) NetworkState.NONE
                        else
                            when {
                                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkState.WIFI
                                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkState.CELLULAR
                                else -> NetworkState.NONE
                            }
                    }

                }
            }
            //其他为旧方法
            else -> {
                if (activeNetworkInfo == null) NetworkState.NONE
                else {
                    if (!activeNetworkInfo!!.isConnected) NetworkState.NONE
                    else {
                        when (activeNetworkInfo!!.type) {
                            ConnectivityManager.TYPE_MOBILE -> NetworkState.CELLULAR
                            ConnectivityManager.TYPE_WIFI -> NetworkState.WIFI
                            else -> NetworkState.NONE
                        }
                    }
                }

            }
        }
    }
}