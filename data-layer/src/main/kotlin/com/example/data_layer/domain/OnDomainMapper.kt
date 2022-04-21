package com.example.data_layer.domain

import com.example.domain_layer.domain.*

fun List<CharacterDto>.characterDtoToBo(): List<CharacterBo> =
    map { it.dtoToBo() }

fun CharacterDto.dtoToBo(): CharacterBo =
    CharacterBo(
        id = id ?: -1,
        name = name ?: "",
        description = description ?: "",
        thumbnail = thumbnail?.dtoToBo() ?: dumbThumbnailBo()
    )

fun ResponseMarvelDto<CharacterDto>.dtoToBo() : ResponseMarvelBo<CharacterBo> =
    ResponseMarvelBo(
        code = code ?: -1,
        etag = etag ?: "",
        data = data?.dtoToBo() ?: dummyDataBo()
    )

fun DataDto<CharacterDto>.dtoToBo() : DataBo<CharacterBo> =
    DataBo(
        offset = offset ?: -1,
        limit = limit ?: -1,
        total = total ?: -1,
        count = count ?: -1,
        results = results?.characterDtoToBo() ?: listOf()
    )


fun ThumbnailDto.dtoToBo(): ThumbnailBo =
    ThumbnailBo(
        path = path ?: "",
        extension = extension ?: ""
    )

fun FailureDto.toFailureBo(): FailureBo = when (this) {
    is FailureDto.ClientError -> FailureBo.ClientError(code, message)
    is FailureDto.EmptyResponse -> FailureBo.EmptyResponse(message)
    FailureDto.NoNetwork -> FailureBo.NoNetwork
    is FailureDto.ServerError -> FailureBo.ServerError(code, message)
    is FailureDto.UnexpectedFailure -> FailureBo.UnexpectedFailure(code, localizedMessage)
}

fun dumbThumbnailBo() = ThumbnailBo(
    path = "",
    extension = ""
)

fun dummyDataBo() = DataBo<CharacterBo>(
    offset = -1,
    limit = -1,
    total = -1,
    results = listOf(),
    count = -1
)
