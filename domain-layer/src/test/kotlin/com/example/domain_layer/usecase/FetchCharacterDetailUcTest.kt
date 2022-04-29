package com.example.domain_layer.usecase

import arrow.core.right
import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.ThumbnailBo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchCharacterDetailUcTest {

    @MockK
    private lateinit var mockRepository: DomainLayerContract.DataLayer.CharacterRepository
    private lateinit var fetchCharacterDetailUc: FetchCharacterDetailUc

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        fetchCharacterDetailUc = FetchCharacterDetailUc(mockRepository)
    }

    @Test
    fun `check if repository is called`() = runBlocking {
        // given
        val request = mockk<Int>(relaxed = true)
        coEvery { mockRepository.fetchCharacterDetail(any()) } returns getDummyCharacterBo().right()
        // when
        fetchCharacterDetailUc.run(request)
        // then
        coVerify(exactly = 1) { mockRepository.fetchCharacterDetail(any()) }
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