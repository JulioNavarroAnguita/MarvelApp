package com.example.presentation_layer.base

import androidx.lifecycle.LifecycleOwner
import com.example.domain_layer.base.BaseDomainLayerBridge

/**
 * This parametrized interface is intended to be implemented by any app presentation-layer view which aims to be
 * integrated within the MVVM architecture pattern.
 *
 * @param T represents the ViewModel, and thus it must extend from @see{BaseMvvmViewModel}
 * @param S represents the bridge to the domain layer, and must extend from @see{BaseDomainLayerBridge}
 * @param U represents the state of the view, and must extend from @see{BaseState}
 * @property viewModel a reference to the [BaseMvvmViewModel] associated to this view
 *
 */
interface BaseMvvmView<T : BaseMvvmViewModel<S, U>, S : BaseDomainLayerBridge, U : BaseState> {

    val viewModel: T?

    /**
     * Handles the possible state values
     *
     * @param renderState the actual state, extending from U
     */
    fun processRenderState(renderState: U?)


    fun observeViewModel(lifecycleOwner: LifecycleOwner) {
        viewModel?.screenState?.observe(lifecycleOwner) { screenState ->
            when (screenState) {
                is ScreenState.Render<U> -> processRenderState(screenState.renderState)
            }
        }
    }

}