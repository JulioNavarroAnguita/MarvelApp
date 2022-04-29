package com.example.data_layer.repository

import arrow.core.Either
import com.example.data_layer.datasource.CharacterDatabaseDataSource
import com.example.data_layer.datasource.CharacterRemoteDataSource
import com.example.data_layer.datasource.ConnectivityDataSource
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
    private lateinit var mockRemoteDataSource: CharacterRemoteDataSource
    @MockK
    private lateinit var mockConnectivityDataSource: ConnectivityDataSource
    @MockK
    private lateinit var mockDatabaseDataSource: CharacterDatabaseDataSource
    private lateinit var characterRepository: CharacterRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        characterRepository = CharacterRepositoryImpl(mockRemoteDataSource, mockDatabaseDataSource, mockConnectivityDataSource)
    }

    @Test
    fun `when 'getCharactersFromDatabase' is successful, response is a list of characterBo type`() =
        runBlockingTest {
            // given
            coEvery { mockDatabaseDataSource.findCharactersFromDatabase() } returns getDummyCharacterListBo()
            // when
            val response = characterRepository.getCharactersFromDatabase()
            // then
            coVerify(exactly = ONE) { mockDatabaseDataSource.findCharactersFromDatabase() }
            assert(response.isRight() && response is Either.Right<List<CharacterBo>>)
        }

    @Test
    fun `when 'getCharacterDetailFromDatabase' is successful, response is a list of characterBo type`() =
        runBlockingTest {
            // given
            coEvery { mockDatabaseDataSource.findCharacterDetailFromDatabase(any()) } returns getDummyCharacterBo()
            // when
            val response = characterRepository.getCharacterDetailFromDatabase(ONE)
            // then
            coVerify(exactly = ONE) { mockDatabaseDataSource.findCharacterDetailFromDatabase(any()) }
            assert(response.isRight() && response is Either.Right<CharacterBo>)
        }

    @Test
    fun `when 'fetchCharacters' and connection is KO, error is returned`() =
        runBlockingTest {
            // given
            coEvery { mockConnectivityDataSource.checkNetworkConnectionAvailability() } returns false
            // when
            val response = characterRepository.fetchCharacters()
            // then
            assert(response.isLeft() && (response as Either.Left<FailureBo>).a is FailureBo.NoNetwork)
        }

    @Test
    fun `when 'fetchCharacters' if the call throw an exception, then return an error`() =
        runBlockingTest {
            // given
            coEvery { mockConnectivityDataSource.checkNetworkConnectionAvailability() } returns true
            coEvery { mockRemoteDataSource.fetchCharactersFromApi() }.throws(IllegalStateException())
            // when
            val response = characterRepository.fetchCharacters()
            // then
            assert(response.isLeft() && (response as Either.Left<FailureBo>).a is FailureBo.Unknown)
        }

    @Test
    fun `when 'fetchCharacters' is successful, response is a list of characterBo type`() =
        runBlockingTest {
            // given
            coEvery { mockConnectivityDataSource.checkNetworkConnectionAvailability() } returns true
            coEvery { mockRemoteDataSource.fetchCharactersFromApi() } returns Response.success(
                getDummyResponseMarvelDto()
            )
            coEvery { mockDatabaseDataSource.clearCharacterList() } returns Unit
            coEvery { mockDatabaseDataSource.insertCharacterListToDataBase(any()) } returns Unit
            // when
            val response = characterRepository.fetchCharacters()
            // then
            coVerify(exactly = ONE) { mockRemoteDataSource.fetchCharactersFromApi() }
            coVerify(exactly = ONE) { mockDatabaseDataSource.clearCharacterList() }
            coVerify(exactly = ONE) { mockDatabaseDataSource.insertCharacterListToDataBase(any()) }
            assert(response.isRight() && response is Either.Right<List<CharacterBo>>)
        }

    @Test
    fun `when 'fetchCharacters' is successful, but response is a null object failure is returned`() =
        runBlockingTest {
            // given
            coEvery { mockConnectivityDataSource.checkNetworkConnectionAvailability() } returns true
            coEvery { mockRemoteDataSource.fetchCharactersFromApi() } returns Response.success(null)
            // when
            val response = characterRepository.fetchCharacters()
            // then
            coVerify(exactly = ONE) { mockRemoteDataSource.fetchCharactersFromApi() }
            assert(response.isLeft() && (response as? Either.Left<FailureBo>)?.a is FailureBo.UnexpectedFailure)
        }

    @Test
    fun `when 'fetchCharacters' is not successful, failure is returned`() = runBlockingTest {
        // given
        coEvery { mockConnectivityDataSource.checkNetworkConnectionAvailability() } returns true
        coEvery { mockRemoteDataSource.fetchCharactersFromApi() } returns Response.error(
            CODE_ERROR,
            DEFAULT_STRING_VALUE.toResponseBody("text/plain".toMediaTypeOrNull())
        )
        // when
        val response = characterRepository.fetchCharacters()
        // then
        coVerify(exactly = ONE) { mockRemoteDataSource.fetchCharactersFromApi() }
        assert(response.isLeft() && (response as? Either.Left<FailureBo>)?.a is FailureBo.UnexpectedFailure)
    }

    @Test
    fun `when 'fetchCharacterDetail' and connection is KO, error is returned`() =
        runBlockingTest {
            // given
            coEvery { mockConnectivityDataSource.checkNetworkConnectionAvailability() } returns false
            // when
            val response = characterRepository.fetchCharacterDetail(ONE)
            // then
            assert(response.isLeft() && (response as Either.Left<FailureBo>).a is FailureBo.NoNetwork)
        }

    @Test
    fun `when 'fetchCharacterDetail' if the call throw an exception, then return an error`() =
        runBlockingTest {
            // given
            coEvery { mockConnectivityDataSource.checkNetworkConnectionAvailability() } returns true
            coEvery { mockRemoteDataSource.fetchCharacterDetailFromApi(any()) }.throws(IllegalStateException())
            // when
            val response = characterRepository.fetchCharacterDetail(ONE)
            // then
            assert(response.isLeft() && (response as Either.Left<FailureBo>).a is FailureBo.Unknown)
        }

    @Test
    fun `when 'fetchCharacterDetail' is successful, response is a object characterBo type`() =
        runBlockingTest {
            // given
            coEvery { mockConnectivityDataSource.checkNetworkConnectionAvailability() } returns true
            coEvery { mockRemoteDataSource.fetchCharacterDetailFromApi(any()) } returns Response.success(
                getDummyResponseMarvelDto()
            )
            coEvery { mockDatabaseDataSource.clearCharacter(any()) } returns Unit
            coEvery { mockDatabaseDataSource.insertCharacterToDataBase(any()) } returns Unit
            // when
            val response = characterRepository.fetchCharacterDetail(ONE)
            // then
            coVerify(exactly = ONE) { mockRemoteDataSource.fetchCharacterDetailFromApi(any()) }
            coVerify(exactly = ONE) { mockDatabaseDataSource.clearCharacter(any()) }
            coVerify(exactly = ONE) { mockDatabaseDataSource.insertCharacterToDataBase(any()) }
            assert(response.isRight() && response is Either.Right<CharacterBo>)
        }

    @Test
    fun `when 'fetchCharacterDetail' is successful, but response is a null object failure is returned`() =
        runBlockingTest {
            // given
            coEvery { mockConnectivityDataSource.checkNetworkConnectionAvailability() } returns true
            coEvery { mockRemoteDataSource.fetchCharacterDetailFromApi(any()) } returns Response.success(null)
            // when
            val response = characterRepository.fetchCharacterDetail(ONE)
            // then
            coVerify(exactly = ONE) { mockRemoteDataSource.fetchCharacterDetailFromApi(any()) }
            assert(response.isLeft() && (response as? Either.Left<FailureBo>)?.a is FailureBo.UnexpectedFailure)
        }

    @Test
    fun `when 'fetchCharacterDetail' is not successful, failure is returned`() = runBlockingTest {
        // given
        coEvery { mockConnectivityDataSource.checkNetworkConnectionAvailability() } returns true
        coEvery { mockRemoteDataSource.fetchCharacterDetailFromApi(any()) } returns Response.error(
            CODE_ERROR,
            DEFAULT_STRING_VALUE.toResponseBody("text/plain".toMediaTypeOrNull())
        )
        // when
        val response = characterRepository.fetchCharacterDetail(ONE)
        // then
        coVerify(exactly = ONE) { mockRemoteDataSource.fetchCharacterDetailFromApi(any()) }
        assert(response.isLeft() && (response as? Either.Left<FailureBo>)?.a is FailureBo.UnexpectedFailure)
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
                id = DEFAULT_INTEGER_VALUE,
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