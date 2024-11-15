package com.example.template.core.network.di

import com.example.template.core.network.utils.ConnectivityManagerNetworkMonitor
import com.example.template.core.network.utils.NetworkMonitor
import com.example.template.core.network.utils.TimeZoneBroadcastMonitor
import com.example.template.core.network.utils.TimeZoneMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkUtilModule {
    @Binds
    internal abstract fun bindsNetworkMonitor(networkMonitor: ConnectivityManagerNetworkMonitor): NetworkMonitor

    @Binds
    internal abstract fun binds(impl: TimeZoneBroadcastMonitor): TimeZoneMonitor
}