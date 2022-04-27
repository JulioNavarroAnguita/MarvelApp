package com.example.data_layer.di

import com.example.data_layer.BuildConfig
import com.example.data_layer.DataLayerContract
import com.example.data_layer.DataLayerContract.Companion.CHARACTER_DATA_SOURCE_TAG
import com.example.data_layer.DataLayerContract.Companion.RETROFIT_TAG
import com.example.data_layer.datasource.CharacterDataSource
import com.example.data_layer.repository.CharacterRepositoryImpl
import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.DomainLayerContract.DataLayer.Companion.CHARACTER_REPOSITORY_TAG
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
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

val dataLayerModule = module {
    //repository
    single {
        CharacterRepositoryImpl(
            characterDataSource = get(named(name = CHARACTER_DATA_SOURCE_TAG))
        )
    }
    single<DomainLayerContract.DataLayer.CharacterRepository>(
        named(name = CHARACTER_REPOSITORY_TAG)
    ) { get<CharacterRepositoryImpl>() }
    //dataSource
    factory<DataLayerContract.CharacterDataSource>(named(name = CHARACTER_DATA_SOURCE_TAG)) {
        CharacterDataSource(retrofit = get(named(name = RETROFIT_TAG)))
    }
    // retrofit
    single(named(name = RETROFIT_TAG)) {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient())
            .baseUrl(MARVEL_BASE_URL)
            .build()
    }

}

private fun httpClient(): OkHttpClient {
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
            val url = originalHttpUrl.newBuilder().addQueryParameter("apikey", API_PUBLIC_KEY).build()
            request.url(url)
            chain.proceed(request.build())
        })
        .addInterceptor(interceptor)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()
}
