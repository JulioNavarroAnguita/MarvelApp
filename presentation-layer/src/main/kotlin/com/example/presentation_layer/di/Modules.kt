package com.example.presentation_layer.di

import com.example.domain_layer.feature.CHARACTER_DETAIL_DOMAIN_LAYER_BRIDGE_TAG
import com.example.domain_layer.feature.CHARACTER_LIST_DOMAIN_LAYER_BRIDGE_TAG
import com.example.presentation_layer.feature.character_list.viewmodel.CharacterListViewModel
import com.example.presentation_layer.feature.character_detail.viewmodel.CharacterDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationLayerModule = module {
    //viewModel
    viewModel {
        CharacterListViewModel(bridge = get(named(name = CHARACTER_LIST_DOMAIN_LAYER_BRIDGE_TAG)))
    }
    viewModel {
        CharacterDetailViewModel(bridge = get(named(CHARACTER_DETAIL_DOMAIN_LAYER_BRIDGE_TAG)))
    }
}