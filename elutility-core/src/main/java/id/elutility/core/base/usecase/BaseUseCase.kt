package id.elutility.core.base.usecase

import id.elutility.core.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in Param, Output>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(param: Param): Result<Output> {
        return try {
            withContext(coroutineDispatcher) {
                execute(param)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error", e)
        }
    }

    protected abstract suspend fun execute(param: Param): Result<Output>
}