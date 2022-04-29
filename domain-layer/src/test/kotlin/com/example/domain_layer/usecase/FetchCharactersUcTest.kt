package com.example.domain_layer.usecase

import arrow.core.right
import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.ThumbnailBo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchCharactersUcTest {

    @MockK
    private lateinit var mockRepository: DomainLayerContract.DataLayer.CharacterRepository
    private lateinit var fetchCharactersUc: FetchCharactersUc

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        fetchCharactersUc = FetchCharactersUc(mockRepository)
    }

    @Test
    fun `check if repository is called`() = runBlocking {
        // given
        coEvery { mockRepository.fetchCharacters() } returns getCharacterListBoMocked().right()
        // when
        fetchCharactersUc.run(null)
        // then
        coVerify(exactly = 1) { mockRepository.fetchCharacters() }
    }

    private fun getCharacterListBoMocked() = listOf(
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