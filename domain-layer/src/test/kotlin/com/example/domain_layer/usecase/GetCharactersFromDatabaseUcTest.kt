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

class GetCharactersFromDatabaseUcTest {

    @MockK
    private lateinit var mockRepository: DomainLayerContract.DataLayer.CharacterRepository
    private lateinit var getCharactersFromDatabaseUc: GetCharactersFromDatabaseUc

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getCharactersFromDatabaseUc = GetCharactersFromDatabaseUc(mockRepository)
    }

    @Test
    fun `check if repository is called`() = runBlocking {
        // given
        coEvery { mockRepository.getCharactersFromDatabase() } returns getCharacterListBoMocked().right()
        // when
        getCharactersFromDatabaseUc.run(null)
        // then
        coVerify(exactly = 1) { mockRepository.getCharactersFromDatabase() }
    }

    private fun getCharacterListBoMocked() = listOf(
        CharacterBo(
            id = DEFAULT_INTEGER_VALUE,
            name = DEFAULT_STRING_VALUE,
            description = DEFAULT_STRING_VALUE,
            image = DEFAULT_STRING_VALUE
        )
    )

}