package com.example.marvel_app

import android.app.Application
import com.example.data_layer.di.dataLayerModule
import com.example.domain_layer.di.domainLayerModule
import com.example.presentation_layer.di.presentationLayerModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

@ExperimentalCoroutinesApi
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
            startKoin {
                androidLogger()
                androidContext(this@Application)
                modules(listOf(presentationLayerModule, domainLayerModule, dataLayerModule).flatten())
            }
        }
}
