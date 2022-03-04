package com.example.domain_layer

import arrow.core.Either
import com.example.domain_layer.domain.CharacterBo
import com.example.domain_layer.domain.FailureBo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

interface DomainLayerContract {

    interface PresentationLayer {

        interface UseCase<in T, out S> {
            fun invoke(
                scope: CoroutineScope, params: T? = null, onResult: (Either<FailureBo, S>) -> Unit
            ) {
                // task undertaken in a worker thread
                val job = scope.async { run(params) }
                // once completed, result sent in the Main thread
                scope.launch(Dispatchers.Main) { onResult(job.await()) }
            }

            suspend fun run(params: T? = null): Either<FailureBo, S>
        }

    }

    interface DataLayer {

        companion object {
            const val CHARACTER_REPOSITORY_TAG = "characterRepository"
        }

        interface CharacterRepository {
            suspend fun fetchCharacters(): Either<FailureBo, List<CharacterBo>?>
        }
    }

}