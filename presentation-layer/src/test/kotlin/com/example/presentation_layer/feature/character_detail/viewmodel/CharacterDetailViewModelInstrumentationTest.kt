package com.example.presentation_layer.feature.character_detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.left
import arrow.core.right
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo
import com.example.domain_layer.feature.CharacterDetailDomainLayerBridge
import com.example.presentation_layer.base.ScreenState
import com.example.presentation_layer.feature.character_detail.view.state.CharacterDetailState
import com.example.presentation_layer.feature.character_list.view.state.CharacterListState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val DEFAULT_STRING_VALUE = "none"
private const val DEFAULT_INTEGER_VALUE = -1

@ExperimentalCoroutinesApi
class CharacterDetailViewModelInstrumentationTest {

    @MockK
    private lateinit var mockBridge: CharacterDetailDomainLayerBridge
    @MockK
    private lateinit var viewModel: CharacterDetailViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = CharacterDetailViewModel(mockBridge)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when loadCharacterDetail is invoked and fetchCharacterDetail call api is success and the list is not empty the state is of type ShowCharacterList`() = runBlockingTest {
            //given
            coEvery { mockBridge.fetchCharacterDetail(any()) } returns getDummyCharacterBo().right()
            viewModel.screenState
            //when
            viewModel.loadCharacterDetail(1)
            //then
            assert((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterDetailState.ShowCharacterDetail)
        }

    @Test
    fun `when loadCharacterDetail is invoked and fetchCharacterDetail call api is not success the state is of type ShowError`() = runBlockingTest {
        //given
        coEvery { mockBridge.fetchCharacterDetail(any()) } returns FailureBo.NoData.left()
        viewModel.screenState
        //when
        viewModel.loadCharacterDetail(1)
        //then
        assert((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterDetailState.ShowError)
    }

    @Test
    fun `when 'loadCharacterDetailFromDatabase' is invoked and 'getCharactersFromDataBase' call api is success, state is of type ShowCharacterDetail`() = runBlockingTest {
        //given
        coEvery { mockBridge.getCharacterDetailFromDatabase(any()) } returns getDummyCharacterBo().right()
        viewModel.screenState
        //when
        viewModel.loadCharacterDetailFromDatabase(DEFAULT_INTEGER_VALUE)
        //then
        assert((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterDetailState.ShowCharacterDetail)
    }

    @Test
    fun `when 'loadCharacterDetailFromDatabase' is invoked and 'getCharacterDetailFromDatabase' call api is not success, an error of type ShowError is returned`() = runBlockingTest {
        //given
        coEvery { mockBridge.getCharacterDetailFromDatabase(any()) } returns FailureBo.NoData.left()
        viewModel.screenState
        //when
        viewModel.loadCharacterDetailFromDatabase(DEFAULT_INTEGER_VALUE)
        //then
        assert((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterDetailState.ShowError)
    }

    private fun getDummyCharacterBo() = CharacterBo(
        id = DEFAULT_INTEGER_VALUE,
        name = DEFAULT_STRING_VALUE,
        description = DEFAULT_STRING_VALUE,
        image = DEFAULT_STRING_VALUE
    )


}