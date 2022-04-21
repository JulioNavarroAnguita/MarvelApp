package com.example.presentation_layer.feature.character_detail.viewmodel

import androidx.test.runner.AndroidJUnit4
import arrow.core.Either
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo
import com.example.domain_layer.feature.CHARACTER_DETAIL_DOMAIN_LAYER_BRIDGE_TAG
import com.example.domain_layer.feature.CharacterDetailDomainLayerBridge
import com.example.presentation_layer.base.ScreenState
import com.example.presentation_layer.di.presentationLayerModule
import com.example.presentation_layer.feature.character_detail.view.state.CharacterDetailState
import com.nhaarman.mockitokotlin2.*
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.ArgumentCaptor

private const val DEFAULT_STRING_VALUE = ""
private const val DEFAULT_INTEGER_VALUE = 1

@RunWith(AndroidJUnit4::class)
class CharacterDetailViewModelTest : KoinTest {

    private val viewModel: CharacterDetailViewModel by inject()
    private val mockBridge = mock<CharacterDetailDomainLayerBridge>()

    @Before
    fun setUp() {
        startKoin {
            modules(listOf(
                presentationLayerModule,
                module { factory(named(name = CHARACTER_DETAIL_DOMAIN_LAYER_BRIDGE_TAG)) { mockBridge } }
            ))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    suspend fun whenLoadCharacterDetailIsInvokedAndFetchCharacterDetailIsNotSuccessfulScreenStateIsOfTypeShowError() {
        // given
        val argumentCaptor = argumentCaptor<(Either<FailureBo, List<CharacterBo>>) -> Unit>()
        viewModel.screenState
        // when
        viewModel.loadCharacterDetail()
        // then
        verify(mockBridge).fetchCharacterDetail(any())
        argumentCaptor.firstValue.invoke(
            Either.left(
                FailureBo.UnexpectedFailure(
                    code = DEFAULT_INTEGER_VALUE,
                    localizedMessage = DEFAULT_STRING_VALUE
                )
            )
        )
        Assert.assertTrue(((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterDetailState.ShowError))
    }

}