package com.example.presentation_layer.feature.character_list.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.right
import arrow.core.left
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo
import com.example.domain_layer.domain.ThumbnailBo
import com.example.domain_layer.feature.CharacterListDomainLayerBridge
import com.example.presentation_layer.base.ScreenState
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

@ExperimentalCoroutinesApi
class CharacterListViewModelInstrumentationTest {

    @MockK
    private lateinit var mockBridge: CharacterListDomainLayerBridge
    private lateinit var viewModel: CharacterListViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = CharacterListViewModel(mockBridge)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when loadCharacters is invoked and fetchCharacters call api is success and the list is not empty the state is of type ShowCharacterList`() = runBlockingTest {
        //given
        coEvery { mockBridge.fetchCharacters() } returns getDummyCharacterListBo().right()
        viewModel.screenState
        //when
        viewModel.loadCharacters()
        //then
        assert((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterListState.ShowCharacterList)
    }

    @Test
    fun `when loadCharacters is invoked and fetchCharacters call api is success and the list is empty the state is of type NoData`() = runBlockingTest {
        //given
        coEvery { mockBridge.fetchCharacters() } returns emptyList<CharacterBo>().right()
        viewModel.screenState
        //when
        viewModel.loadCharacters()
        //then
        assert((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterListState.NoData)
    }

    @Test
    fun `when loadCharacters is invoked and fetchCharacters call api is not success the state is of type ShowError`() = runBlockingTest {
        //given
        coEvery { mockBridge.fetchCharacters() } returns FailureBo.NoData.left()
        viewModel.screenState
        //when
        viewModel.loadCharacters()
        //then
        assert((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterListState.ShowError)
    }

    private fun getDummyCharacterListBo() = listOf(
        CharacterBo(
            id = -1,
            name = "none",
            description = "none",
            image = getDummyThumbnailBo()
        )
    )

    private fun getDummyThumbnailBo() = ThumbnailBo(
        path = "none",
        extension = "none"
    )

}