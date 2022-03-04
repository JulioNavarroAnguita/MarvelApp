package com.example.domain_layer.domain

data class CharacterBo(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailBo
)

data class ThumbnailBo(
    val path: String,
    val extension: String
)

data class DataBo<T>(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<T>
)

data class ResponseMarvelBo<T>(
    val code: Int,
    val etag: String,
    val data: DataBo<T>
)

sealed class FailureBo {
    object NoNetwork : FailureBo()
    object NoData : FailureBo()
    class EmptyResponse(val message: String? = null) : FailureBo()
    class ServerError(val code: Int, val message: String?) : FailureBo()
    class ClientError(val code: Int, val message: String?) : FailureBo()
    class UnexpectedFailure(val code: Int, val localizedMessage: String?) : FailureBo()
    abstract class FeatureFailure : FailureBo()
}


