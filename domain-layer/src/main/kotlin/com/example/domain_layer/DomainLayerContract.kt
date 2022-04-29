package com.example.domain_layer

import arrow.core.Either
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo

interface DomainLayerContract {

    interface PresentationLayer {

        interface UseCase<in T, out S> {
            /**
             * Executes the use-cause
             *
             * @param [params] arguments used in the query
             * @return An [S] value if successful or a [FailureBo] otherwise
             */
            suspend fun run(params: T): Either<FailureBo, S>
        }

    }

    interface DataLayer {
        interface CharacterRepository {
            suspend fun fetchCharacters(): Either<FailureBo, List<CharacterBo>>
            suspend fun getCharactersFromDatabase(): Either<FailureBo, List<CharacterBo>>
            suspend fun fetchCharacterDetail(params: Int): Either<FailureBo, CharacterBo>
            suspend fun getCharacterDetailFromDatabase(params: Int): Either<FailureBo, CharacterBo>
        }
    }

}