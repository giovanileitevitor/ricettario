package br.applabbs.ricettario.data.local.repositories

import br.applabbs.ricettario.data.local.entities.FotoEntity
import br.applabbs.ricettario.data.local.entities.ReceitaEntity
import br.applabbs.ricettario.data.local.entities.StepEntity

interface LocalRepository {

    suspend fun insertStepIntoDB(stepEntity: StepEntity)
    suspend fun getStepsfromDB(isTemporary: Boolean): List<StepEntity>
    suspend fun getStepsFromIdReceita(idReceita: Int): List<StepEntity>
    suspend fun deleteStepIntoDB(idStep: Int)
    suspend fun deleteAllTemporarySteps(isTemporary: Boolean)
    suspend fun updateStepsIntoDB(idReceita: Int)

    suspend fun insertFotoReceita(fotoEntity: FotoEntity)
    suspend fun getFotosfromDB(isTemporary: Boolean): List<FotoEntity>
    suspend fun deleteFotoIntoDB(idFoto: Int)
    suspend fun deleteAllTemporaryFotos(isTemporary: Boolean)
    suspend fun updateFotosIntoDB(idReceita: Int)

    suspend fun insertReceita(receitaEntity: ReceitaEntity): Long
    suspend fun getAllReceitas(): List<ReceitaEntity>
    suspend fun setReceitaAsFavorite(idReceita: Int)
    suspend fun deleteReceita(idReceita: Int)

}