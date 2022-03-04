package com.example.presentation_layer.base

/**
 * This sealed class is the baseline upon which any screen state is constructed
 *
 */
sealed class ScreenState<out T : BaseState> {
    object Loading : ScreenState<Nothing>()
    class Render<out T : BaseState>(val renderState: T?) : ScreenState<T>()
}