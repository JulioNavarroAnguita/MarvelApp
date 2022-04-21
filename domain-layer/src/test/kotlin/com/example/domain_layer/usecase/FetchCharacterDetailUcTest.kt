package com.example.domain_layer.usecase

import arrow.core.Either
import com.example.domain_layer.DomainLayerContract
import com.example.domain_layer.di.domainLayerModule
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@ExperimentalCoroutinesApi
class FetchCharacterDetailUcTest : KoinTest {

    private val fetchCharacterDetailUc: DomainLayerContract.PresentationLayer.UseCase<Int?, List<CharacterBo>>
            by inject(named(name = FETCH_CHARACTER_DETAIL_UC_TAG))
    private lateinit var mockRepository: DomainLayerContract.DataLayer.CharacterRepository

    @Before
    fun setUp() {
        mockRepository = mock()
        startKoin {
            modules(
                listOf(
                    domainLayerModule,
                    module {
                        factory(named(name = DomainLayerContract.DataLayer.CHARACTER_REPOSITORY_TAG)) { mockRepository }
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
        fetchCharacterDetailUc.run(any())
        // then
        verify(mockRepository).fetchCharacterDetail(any())
    }

}