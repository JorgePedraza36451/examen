package com.project.examen.domain.usescases

import com.project.examen.base.Either
import com.project.examen.base.Failure
import com.project.examen.base.UseCase
import com.project.examen.data.repository.CharacterRepository
import com.project.examen.ui.model.CharacterUiModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class CharacterUsesCases @Inject constructor(
    private val repository: CharacterRepository,
): UseCase<List<CharacterUiModel>, Int>() {

    override suspend fun execute(
        params: Int,
        callback: (Either<Failure, List<CharacterUiModel>>) -> Unit
    ) {
        repository.getService(params).catch {
            it.message
            callback.invoke(Either.Left(Failure.ServerError))
        }.flowOn(getCoroutineDispatcher()).collect{
            callback.invoke(Either.Right(it))
        }
    }
}