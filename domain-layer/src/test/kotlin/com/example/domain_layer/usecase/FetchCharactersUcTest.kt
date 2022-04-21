package com.example.domain_layer.usecase

import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.DomainLayerContract.DataLayer.Companion.CHARACTER_REPOSITORY_TAG
import com.example.domain_layer.di.domainLayerModule
import com.example.domain_layer.domain.CharacterBo
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@ExperimentalCoroutinesApi
class FetchCharactersUcTest : KoinTest {

    private val fetchCharactersUc: DomainLayerContract.PresentationLayer.UseCase<Any?, List<CharacterBo>>
            by inject(named(name = FETCH_CHARACTERS_UC_TAG))
    private lateinit var mockRepository: DomainLayerContract.DataLayer.CharacterRepository

    @Before
    fun setUp() {
        mockRepository = mock()
        startKoin {
            modules(
                listOf(
                    domainLayerModule,
                    module {
                        factory(named(name = CHARACTER_REPOSITORY_TAG)) { mockRepository }
                    }
                )
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `check if repository is called`() = runBlockingTest {
        // when
        fetchCharactersUc.run(null)
        // then
        verify(mockRepository).fetchCharacters()
    }

}