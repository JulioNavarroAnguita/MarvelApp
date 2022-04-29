package com.example.data_layer.datasource

import android.content.Context
import com.example.data_layer.DataLayerContract
import com.example.data_layer.utils.isNetworkAvailable

class ConnectivityDataSource(
    private val context: Context
): DataLayerContract.AndroidDataSource {
    override suspend fun checkNetworkConnectionAvailability() = context.isNetworkAvailable()
}