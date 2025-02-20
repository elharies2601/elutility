package id.elutility.core.base.usecase

import id.elutility.core.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class BaseFlowUseCase<in Param, Output>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(param: Param): Flow<Result<Output>> {
        return execute(param)
            .catch { e ->
                emit(Result.Error(e.message ?: "Unknown error", e))
            }
            .flowOn(coroutineDispatcher)
    }

    protected abstract fun execute(param: Param): Flow<Result<Output>>
}