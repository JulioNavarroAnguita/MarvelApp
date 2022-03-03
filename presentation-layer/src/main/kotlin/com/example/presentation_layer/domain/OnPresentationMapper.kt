package com.example.presentation_layer.domain

import com.example.domain_layer.domain.*

fun FailureBo.toVo(): FailureVo = when (this) {
    is FailureBo.NoNetwork -> FailureVo.NoNetwork
    is FailureBo.NoData -> FailureVo.TryAgain
    is FailureBo.ClientError -> FailureVo.TryAgain
    is FailureBo.EmptyResponse -> FailureVo.TryAgain
    is FailureBo.ServerError -> FailureVo.TryAgain
    is FailureBo.UnexpectedFailure -> FailureVo.TryAgain
    is FailureBo.FeatureFailure -> FailureVo.LoginUserOrPassEmpty
}

fun List<CharacterBo>.characterBoToVo(): List<CharacterVo> =
    map { it.boToVo() }

fun List<CharacterVo>.characterVoToBo(): List<CharacterBo> =
    map { it.voToBo() }

fun CharacterBo.boToVo(): CharacterVo =
    CharacterVo(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail.boToVo()
    )

fun CharacterVo.voToBo(): CharacterBo =
    CharacterBo(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail.voToBo()
    )

fun ThumbnailVo.voToBo(): ThumbnailBo =
    ThumbnailBo(
        path = path,
        extension = extension
    )

fun ThumbnailBo.boToVo(): ThumbnailVo =
    ThumbnailVo(
        path = path,
        extension = extension
    )