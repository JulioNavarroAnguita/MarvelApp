package com.example.data_layer.service

import com.example.data_layer.domain.CharacterDto
import com.example.data_layer.domain.ResponseMarvelDto
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {

    @GET("characters")
    fun getAllCharactersAsync(
        @Query("hash") hash: String? = null,
        @Query("ts") ts: String? = null
    ): Deferred<Response<ResponseMarvelDto<CharacterDto>>>


    @GET("characters/{id}")
    fun getCharacterByIdAsync(
        @Path("id") id: Int? = null,
        @Query("apikey") apikey: String? = null,
    ): Deferred<Response<CharacterDto>>

}