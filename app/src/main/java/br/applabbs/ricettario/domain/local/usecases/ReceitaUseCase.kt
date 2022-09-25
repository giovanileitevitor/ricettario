package br.applabbs.ricettario.domain.local.usecases

import br.applabbs.ricettario.domain.local.models.Foto
import br.applabbs.ricettario.domain.local.models.Receita
import br.applabbs.ricettario.domain.local.models.Step

interface ReceitaUseCase {

    suspend fun insertStepReceita(step : Step)
    suspend fun getSteps(isTemporary : Boolean): List<Step>
    suspend fun getStepsFromIdReceita(idReceita: Int): List<Step>
    suspend fun deleteStep(idStep : Int)
    suspend fun deleteAllTemporarySteps(isTemporary : Boolean)
    suspend fun updateSteps(idReceita: Int)

    suspend fun insertFotoReceita(foto : Foto)
    suspend fun getFotos(isTemporary : Boolean): List<Foto>
    suspend fun deleteFoto(idFoto : Int)
    suspend fun deleteAllTemporaryFotos(isTemporary : Boolean)
    suspend fun updateFotos(idReceita: Int)

    suspend fun insertReceita(tituloReceita: String, steps: List<Step>, fotos: List<Foto>): Long
    suspend fun getAllReceitas(): List<Receita>
    suspend fun setReceitaAsFavorite(idReceita: Int)
    suspend fun deleteReceita(idReceita: Int)

}