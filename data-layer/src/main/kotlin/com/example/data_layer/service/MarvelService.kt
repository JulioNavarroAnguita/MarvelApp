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
    fun fetchCharactersAsync(
        @Query("hash") hash: String,
        @Query("ts") ts: String,
        @Query("limit") limit: Int? = null
    ): Deferred<Response<ResponseMarvelDto<CharacterDto>>>


    @GET("characters/{characterId}")
    fun fetchCharacterDetailAsync(
        @Path("characterId") characterId: Int? = null,
        @Query("hash") hash: String? = null,
        @Query("ts") ts: String? = null
    ): Deferred<Response<ResponseMarvelDto<CharacterDto>>>

}