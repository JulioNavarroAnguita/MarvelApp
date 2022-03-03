package com.example.data_layer.datasource

import com.example.data_layer.DataLayerContract
import com.example.data_layer.domain.*
import com.example.data_layer.service.MarvelService
import retrofit2.Response
import retrofit2.Retrofit

private const val HASH = "751d116d30cb878c764d95954fc8eef4"
private const val TS = "1"



class CharacterDataSource(private val retrofit: Retrofit) :
    DataLayerContract.CharacterDataSource {

    override suspend fun getCharacters(): Response<ResponseMarvelDto<CharacterDto>> =
        retrofit.create(MarvelService::class.java).getAllCharactersAsync(
            HASH,
            TS
        ).await()
}