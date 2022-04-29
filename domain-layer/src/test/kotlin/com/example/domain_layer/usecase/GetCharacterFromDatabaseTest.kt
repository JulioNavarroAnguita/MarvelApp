package com.example.domain_layer.usecase

import arrow.core.right
import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.domain.CharacterBo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

private const val DEFAULT_STRING_VALUE = "none"
private const val DEFAULT_INTEGER_VALUE = -1

class GetCharacterFromDatabaseTest {

    @MockK
    private lateinit var mockRepository: DomainLayerContract.DataLayer.CharacterRepository
    private lateinit var getCharacterFromDatabaseUc: GetCharacterFromDatabaseUc

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getCharacterFromDatabaseUc = GetCharacterFromDatabaseUc(mockRepository)
    }

    @Test
    fun `check if repository is called`() = runBlocking {
        // given
        coEvery { mockRepository.getCharacterDetailFromDatabase(any()) } returns getCharacterListBoMocked().right()
        // when
        getCharacterFromDatabaseUc.run(DEFAULT_INTEGER_VALUE)
        // then
        coVerify(exactly = 1) { mockRepository.getCharacterDetailFromDatabase(any()) }
    }

    private fun getCharacterListBoMocked() = CharacterBo(
            id = DEFAULT_INTEGER_VALUE,
            name = DEFAULT_STRING_VALUE,
            description = DEFAULT_STRING_VALUE,
            image = DEFAULT_STRING_VALUE
        )

}