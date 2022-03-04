package com.example.presentation_layer.di

import com.example.domain_layer.feature.characterList.CHARACTER_LIST_DOMAIN_LAYER_BRIDGE_TAG
import com.example.presentation_layer.feature.ui.characterList.viewmodel.CharacterListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationLayerModule = module(override = true) {
    //viewModel
    viewModel {
        CharacterListViewModel(bridge = get(named(name = CHARACTER_LIST_DOMAIN_LAYER_BRIDGE_TAG)))
    }
}