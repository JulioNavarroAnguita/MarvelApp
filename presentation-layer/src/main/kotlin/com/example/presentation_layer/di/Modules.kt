package com.example.presentation_layer.di

import com.example.presentation_layer.feature.character_detail.viewmodel.CharacterDetailViewModel
import com.example.presentation_layer.feature.character_list.viewmodel.CharacterListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    //viewModel
    viewModel {
        CharacterListViewModel(bridge = get())
    }
    viewModel {
        CharacterDetailViewModel(bridge = get())
    }
}

val presentationLayerModule = listOf(
    viewModelModule
)