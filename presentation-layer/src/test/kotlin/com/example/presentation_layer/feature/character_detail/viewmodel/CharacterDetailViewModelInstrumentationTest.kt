package com.example.presentation_layer.feature.character_detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.left
import arrow.core.right
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo
import com.example.domain_layer.domain.ThumbnailBo
import com.example.domain_layer.feature.CharacterDetailDomainLayerBridge
import com.example.presentation_layer.base.ScreenState
import com.example.presentation_layer.feature.character_detail.view.state.CharacterDetailState
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

    private fun getDummyCharacterBo() = CharacterBo(
        id = -1,
        name = "none",
        description = "none",
        image = getDummyThumbnailBo()
    )

    private fun getDummyThumbnailBo() = ThumbnailBo(
        path = "none",
        extension = "none"
    )

}