package com.example.presentation_layer.domain

import com.example.domain_layer.domain.*

fun List<CharacterBo>.characterBoToVo(): List<CharacterVo> =
    map { it.boToVo() }

fun List<CharacterVo>.characterVoToBo(): List<CharacterBo> =
    map { it.voToBo() }

fun CharacterBo.boToVo() = CharacterVo(
    id = id,
    name = name,
    description = description,
    image = image
)

fun CharacterVo.voToBo() = CharacterBo(
    id = id,
    name = name,
    description = description,
    image = image
)

fun FailureBo.toVo(): FailureVo = when (this) {
    is FailureBo.NoNetwork -> FailureVo.NoNetwork
    is FailureBo.NoData -> FailureVo.TryAgain
    is FailureBo.ClientError -> FailureVo.TryAgain
    is FailureBo.EmptyResponse -> FailureVo.TryAgain
    is FailureBo.ServerError -> FailureVo.TryAgain
    is FailureBo.UnexpectedFailure -> FailureVo.TryAgain
    FailureBo.Unknown -> FailureVo.Unknown
}
