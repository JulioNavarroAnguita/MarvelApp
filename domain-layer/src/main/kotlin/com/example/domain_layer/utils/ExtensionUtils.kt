package com.example.domain_layer.utils

import arrow.core.Either
import arrow.core.EitherOf
import arrow.core.fix

/**
 * This extension function provides an inline 'flatMap' feature for the 'Either' type. All credits
 */
inline fun <A, B, C> EitherOf<A, B>.flatMapInline(f: (B) -> Either<A, C>): Either<A, C> =
    fix().let {
        when (it) {
            is Either.Right -> f(it.b)
            is Either.Left -> it
        }
    }

/**
 * This extension function provides an inline 'map' feature for the 'Either' type. All credits to
 */
inline fun <A, B, C> EitherOf<A, B>.mapInline(f: (B) -> C): Either<A, C> =
    flatMapInline { Either.Right(f(it)) }

/**
 * This extension function maps all the possible [Exception] cases which normally apply to service queries.
 */

