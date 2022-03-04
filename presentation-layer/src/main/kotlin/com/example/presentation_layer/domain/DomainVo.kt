package com.example.presentation_layer.domain

import com.example.presentation_layer.R

data class CharacterVo(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailVo
)

class ThumbnailVo(
    val path: String,
    val extension: String
)

sealed class FailureVo(val msgResource: Int) {
    object NoNetwork : FailureVo(msgResource = R.string.no_connection_error_msg)
    object EmptyList : FailureVo(msgResource = R.string.empty_list_search_explanation)
    object TryAgain : FailureVo(msgResource = R.string.try_again)
    object LoginUserOrPassEmpty : FailureVo(msgResource = R.string.complete_fields)
}

