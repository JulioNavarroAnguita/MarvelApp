package com.example.data_layer.domain

data class CharacterDto(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val thumbnail: ThumbnailDto? = null
)

data class ThumbnailDto(
    val path: String? = null,
    val extension: String? = null
)

data class DataDto<T>(
    val offset: Int? = null,
    val limit: Int? = null,
    val total: Int? = null,
    val count: Int? = null,
    val results: List<T>? = null
)

data class ResponseMarvelDto<T>(
    val code: Int? = null,
    val etag: String? = null,
    val data: DataDto<T>? = null
)

sealed class FailureDto {
    object NoNetwork : FailureDto()
    class EmptyResponse(val message: String?) : FailureDto()
    class ServerError(val code: Int, val message: String?) : FailureDto()
    class ClientError(val code: Int, val message: String?) : FailureDto()
    class UnexpectedFailure(val code: Int, val localizedMessage: String?) : FailureDto()
    object Unknown : FailureDto()

}