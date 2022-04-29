package com.example.data_layer.di

import android.app.Application
import androidx.room.Room
import com.example.data_layer.BuildConfig
import com.example.data_layer.DataLayerContract
import com.example.data_layer.database.CharacterDatabase
import com.example.data_layer.database.dao.CharacterDao
import com.example.data_layer.datasource.CharacterDatabaseDataSource
import com.example.data_layer.datasource.CharacterRemoteDataSource
import com.example.data_layer.datasource.ConnectivityDataSource
import com.example.data_layer.repository.CharacterRepositoryImpl
import com.example.domain_layer.DomainLayerContract
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * This variable represents the 'data-layer' dependencies module to be used by Koin. It basically
 * includes repository and data-source definitions.
 *
 * @author Julio Navarro
 * @since 1.0
 */

const val MARVEL_BASE_URL = "http://gateway.marvel.com/v1/public/"
const val API_PUBLIC_KEY = "2be68cc0bc260f758fd401bb934bc4ca"
private const val HASH = "751d116d30cb878c764d95954fc8eef4"

val repositoryModule = module {
    //repository
    factory<DomainLayerContract.DataLayer.CharacterRepository> {
        CharacterRepositoryImpl(
            characterRemoteDataSource = get(),
            characterDatabaseDataSource = get(),
            connectivityDataSource = get()
        )
    }
}

val dataSourceModule = module {
    //dataSource
    factory<DataLayerContract.CharacterDataSource.Remote> {
        CharacterRemoteDataSource(retrofit = get())
    }
    factory<DataLayerContract.CharacterDataSource.Database> {
        CharacterDatabaseDataSource(characterDao = get())
    }
    factory<DataLayerContract.AndroidDataSource> {
        ConnectivityDataSource(
            context = get()
        )
    }
}

val networkModule = module {
    fun httpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                val originalHttpUrl = chain.request().url
                val url =
                    originalHttpUrl.newBuilder().addQueryParameter("apikey", API_PUBLIC_KEY).build()
                request.url(url)
                chain.proceed(request.build())
            })
            .addInterceptor(interceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }
    // retrofit
    single {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient())
            .baseUrl(MARVEL_BASE_URL)
            .build()
    }

}

val roomModule = module {
    fun provideDataBase(application: Application): CharacterDatabase {
        return Room.databaseBuilder(application, CharacterDatabase::class.java, "characterdb")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(dataBase: CharacterDatabase): CharacterDao {
        return dataBase.getCharacterDao()
    }
    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }
}

val dataLayerModule = listOf(networkModule, dataSourceModule, repositoryModule, roomModule)
