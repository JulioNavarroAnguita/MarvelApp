package com.example.data_layer.repository

import arrow.core.Either
import com.example.data_layer.datasource.CharacterRemoteDataSource
import com.example.data_layer.domain.CharacterDto
import com.example.data_layer.domain.DataDto
import com.example.data_layer.domain.ResponseMarvelDto
import com.example.data_layer.domain.ThumbnailDto
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response

private const val CODE_ERROR = 401
private const val DEFAULT_STRING_VALUE = "none"
private const val DEFAULT_INTEGER_VALUE = -1
private const val ONE = 1

@ExperimentalCoroutinesApi
class CharacterRepositoryImplTest {

    @MockK
    private lateinit var mockDataSource: CharacterRemoteDataSource
    private lateinit var characterRepository: CharacterRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        characterRepository = CharacterRepositoryImpl(mockDataSource)
    }

    @Test
    fun `when 'fetchCharacters' is successful, response is a list of characterBo type`() =
        runBlockingTest {
            // given
            coEvery { mockDataSource.fetchCharactersFromApi() } returns Response.success(
                getDummyResponseMarvelDto()
            )
            // when
            val response = characterRepository.fetchCharacters()
            // then
            coVerify(exactly = ONE) { mockDataSource.fetchCharactersFromApi() }
            assert(response.isRight() && response is Either.Right<List<CharacterBo>>)
        }

    @Test
    fun `when 'fetchCharacters' is successful, but response is a null object failure is returned`() =
        runBlockingTest {
            // given
            coEvery { mockDataSource.fetchCharactersFromApi() } returns Response.success(null)
            // when
            val response = characterRepository.fetchCharacters()
            // then
            coVerify(exactly = ONE) { mockDataSource.fetchCharactersFromApi() }
            assert(response.isLeft() && (response as? Either.Left<FailureBo>)?.a is FailureBo.UnexpectedFailure)
        }

    @Test
    fun `when 'fetchCharacters' is not successful, failure is returned`() = runBlockingTest {
        // given
        coEvery { mockDataSource.fetchCharactersFromApi() } returns Response.error(
            CODE_ERROR,
            DEFAULT_STRING_VALUE.toResponseBody("text/plain".toMediaTypeOrNull())
        )
        // when
        val response = characterRepository.fetchCharacters()
        // then
        coVerify(exactly = ONE) { mockDataSource.fetchCharactersFromApi() }
        assert(response.isLeft() && (response as? Either.Left<FailureBo>)?.a is FailureBo.UnexpectedFailure)
    }

    @Test
    fun `when 'fetchCharacterDetail' is successful, response is a object characterBo type`() =
        runBlockingTest {
            // given
            coEvery { mockDataSource.fetchCharacterDetailFromApi(any()) } returns Response.success(
                getDummyResponseMarvelDto()
            )
            // when
            val response = characterRepository.fetchCharacterDetail(ONE)
            // then
            coVerify(exactly = ONE) { mockDataSource.fetchCharacterDetailFromApi(any()) }
            assert(response.isRight() && response is Either.Right<CharacterBo>)
        }

    @Test
    fun `when 'fetchCharacterDetail' is successful, but response is a null object failure is returned`() =
        runBlockingTest {
            // given
            coEvery { mockDataSource.fetchCharacterDetailFromApi(any()) } returns Response.success(null)
            // when
            val response = characterRepository.fetchCharacterDetail(ONE)
            // then
            coVerify(exactly = ONE) { mockDataSource.fetchCharacterDetailFromApi(any()) }
            assert(response.isLeft() && (response as? Either.Left<FailureBo>)?.a is FailureBo.UnexpectedFailure)
        }

    @Test
    fun `when 'fetchCharacterDetail' is not successful, failure is returned`() = runBlockingTest {
        // given
        coEvery { mockDataSource.fetchCharacterDetailFromApi(any()) } returns Response.error(
            CODE_ERROR,
            DEFAULT_STRING_VALUE.toResponseBody("text/plain".toMediaTypeOrNull())
        )
        // when
        val response = characterRepository.fetchCharacterDetail(ONE)
        // then
        coVerify(exactly = ONE) { mockDataSource.fetchCharacterDetailFromApi(any()) }
        assert(response.isLeft() && (response as? Either.Left<FailureBo>)?.a is FailureBo.UnexpectedFailure)
    }

    private fun getDummyResponseMarvelDto() = ResponseMarvelDto(
        code = DEFAULT_INTEGER_VALUE,
        etag = DEFAULT_STRING_VALUE,
        data = getDummyDataDto(),
    )

    private fun getDummyDataDto() = DataDto(
        offset = DEFAULT_INTEGER_VALUE,
        limit = DEFAULT_INTEGER_VALUE,
        total = DEFAULT_INTEGER_VALUE,
        count = DEFAULT_INTEGER_VALUE,
        results = listOf(
            CharacterDto(
                id = -1,
                name = DEFAULT_STRING_VALUE,
                description = DEFAULT_STRING_VALUE,
                thumbnail = getDummyThumbnail()
            )
        )
    )

    private fun getDummyThumbnail() = ThumbnailDto(
        path = DEFAULT_STRING_VALUE,
        extension = DEFAULT_STRING_VALUE
    )

}