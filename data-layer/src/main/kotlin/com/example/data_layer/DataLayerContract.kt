package com.example.data_layer

import com.example.data_layer.domain.CharacterDto
import com.example.data_layer.domain.ResponseMarvelDto
import retrofit2.Response

interface DataLayerContract {

    companion object {
        const val CHARACTER_DATA_SOURCE_TAG = "characterDataSource"
        const val RETROFIT_TAG = "retrofit"
    }
    interface CharacterDataSource {
        suspend fun getCharacters(): Response<ResponseMarvelDto<CharacterDto>>
    }

}