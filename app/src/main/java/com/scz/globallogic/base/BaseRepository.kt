package com.scz.globallogic.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseRepository {

    fun <T> safeFlowCall(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> T
    ): Flow<T> {
        return flow {
            val response = block.invoke()
            emit(response)
        }.flowOn(dispatcher)
    }

}