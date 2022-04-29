package com.example.presentation_layer.feature.character_list.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.right
import arrow.core.left
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo
import com.example.domain_layer.feature.CharacterListDomainLayerBridge
import com.example.presentation_layer.base.ScreenState
import com.example.presentation_layer.domain.boToVo
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
    fun `when 'loadCharacters' is invoked and 'fetchCharacters' call api is success and the list is empty, then call 'getCharactersFromDataBase', is success a not empty list is returned`() = runBlockingTest {
        //given
        coEvery { mockBridge.fetchCharacters() } returns emptyList<CharacterBo>().right()
        coEvery { mockBridge.getCharactersFromDataBase() } returns getDummyCharacterListBo().right()
        viewModel.screenState
        //when
        viewModel.loadCharacters()
        //then
        assert((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterListState.ShowCharacterList)
    }

    @Test
    fun `when 'loadCharacters' is invoked and 'fetchCharacters' call api is success and the list is empty, then call 'getCharactersFromDataBase' is success but an empty list is returned`() = runBlockingTest {
        //given
        coEvery { mockBridge.fetchCharacters() } returns emptyList<CharacterBo>().right()
        coEvery { mockBridge.getCharactersFromDataBase() } returns emptyList<CharacterBo>().right()
        viewModel.screenState
        //when
        viewModel.loadCharacters()
        //then
        assert((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterListState.NoData)
    }

    @Test
    fun `when 'loadCharacters' is invoked and 'fetchCharacters' call api is success and the list is empty, then call 'getCharactersFromDataBase' and the call is not an error is returned`() = runBlockingTest {
        //given
        coEvery { mockBridge.fetchCharacters() } returns emptyList<CharacterBo>().right()
        coEvery { mockBridge.getCharactersFromDataBase() } returns FailureBo.NoData.left()
        viewModel.screenState
        //when
        viewModel.loadCharacters()
        //then
        assert((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterListState.ShowError)
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

    @Test
    fun `when 'onCharacterItemClicked' is invoked the state is of type ShowCharacterDetail`() = runBlockingTest {
        //given
        viewModel.screenState
        //when
        viewModel.onCharacterItemClicked(item = getDummyCharacterBo().boToVo())
        //then
        assert((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterListState.ShowCharacterDetail)
    }


    @Test
    fun `when 'loadCharactersFromDatabase' is invoked and 'getCharactersFromDataBase' call api is success and the list is not empty the state is of type ShowCharacterList`() = runBlockingTest {
        //given
        coEvery { mockBridge.getCharactersFromDataBase() } returns getDummyCharacterListBo().right()
        viewModel.screenState
        //when
        viewModel.loadCharactersFromDatabase()
        //then
        assert((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterListState.ShowCharacterList)
    }

    @Test
    fun `when 'loadCharactersFromDatabase' is invoked and 'getCharactersFromDataBase' call api is success and the list is empty, state is of type NoData`() = runBlockingTest {
        //given
        coEvery { mockBridge.getCharactersFromDataBase() } returns emptyList<CharacterBo>().right()
        viewModel.screenState
        //when
        viewModel.loadCharactersFromDatabase()
        //then
        assert((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterListState.NoData)
    }

    @Test
    fun `when 'loadCharactersFromDatabase' is invoked and 'getCharactersFromDataBase' call api is not success, an error of type ShowError is returned`() = runBlockingTest {
        //given
        coEvery { mockBridge.getCharactersFromDataBase() } returns FailureBo.NoData.left()
        viewModel.screenState
        //when
        viewModel.loadCharactersFromDatabase()
        //then
        assert((viewModel.screenState.value as? ScreenState.Render)?.renderState is CharacterListState.ShowError)
    }

    private fun getDummyCharacterListBo() = listOf(
        getDummyCharacterBo()
    )

    private fun getDummyCharacterBo() = CharacterBo(
        id = DEFAULT_INTEGER_VALUE,
        name = DEFAULT_STRING_VALUE,
        description = DEFAULT_STRING_VALUE,
        image = DEFAULT_STRING_VALUE
    )

}