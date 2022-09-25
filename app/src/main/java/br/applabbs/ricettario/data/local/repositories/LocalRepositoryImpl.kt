package br.applabbs.ricettario.data.local.repositories

import br.applabbs.ricettario.data.local.dao.FotoDao
import br.applabbs.ricettario.data.local.dao.ReceitaDao
import br.applabbs.ricettario.data.local.dao.StepDao
import br.applabbs.ricettario.data.local.entities.FotoEntity
import br.applabbs.ricettario.data.local.entities.ReceitaEntity
import br.applabbs.ricettario.data.local.entities.StepEntity
import br.applabbs.ricettario.domain.local.models.Receita

class LocalRepositoryImpl(
    private val stepDao: StepDao,
    private val fotoDao: FotoDao,
    private val receitaDao: ReceitaDao
): LocalRepository {

    override suspend fun insertStepIntoDB(stepEntity: StepEntity){
        stepDao.insertStep(step = stepEntity)
    }

    override suspend fun getStepsfromDB(isTemporary : Boolean): List<StepEntity>{
        return stepDao.getStepsfromDB(isTemporary = isTemporary)
    }

    override suspend fun getStepsFromIdReceita(idReceita: Int): List<StepEntity> {
        return stepDao.getStepsFromIdReceita(idReceita = idReceita)
    }

    override suspend fun deleteStepIntoDB(idStep: Int){
        stepDao.deleteStep(idStep = idStep)
    }

    override suspend fun deleteAllTemporarySteps(isTemporary: Boolean) {
        stepDao.deleteAllTemporarySteps(isTemporary = isTemporary)
    }

    override suspend fun insertFotoReceita(fotoEntity: FotoEntity) {
        fotoDao.insertFoto(foto = fotoEntity)
    }

    override suspend fun getFotosfromDB(isTemporary: Boolean): List<FotoEntity> {
        return fotoDao.getFotosfromDB(isTemporary = isTemporary)
    }

    override suspend fun deleteFotoIntoDB(idFoto: Int) {
        fotoDao.deleteFoto(idFoto = idFoto)
    }

    override suspend fun deleteAllTemporaryFotos(isTemporary: Boolean) {
        fotoDao.deleteAllTemporaryFotos(isTemporary = isTemporary)
    }

    override suspend fun insertReceita(receitaEntity: ReceitaEntity) : Long{
        return receitaDao.insertReceita(receita = receitaEntity)
    }

    override suspend fun updateStepsIntoDB(idReceita: Int) {
        stepDao.updateSteps(idReceita = idReceita, isTemporary = true)
        stepDao.removeTemporaryFlagStep(idReceita = idReceita, removeTemporaryFlag = false)
    }

    override suspend fun updateFotosIntoDB(idReceita: Int) {
        fotoDao.updateFotos(idReceita = idReceita, isTemporary = true)
        fotoDao.removeTemporaryFlagFoto(idReceita = idReceita, removeTemporaryFlag = false)
    }

    override suspend fun getAllReceitas(): List<ReceitaEntity> {
        return receitaDao.getReceitas()
    }

    override suspend fun setReceitaAsFavorite(idReceita: Int) {
        receitaDao.setReceitaAsFavorite(idReceita = idReceita, isFavorite = true)
    }

    override suspend fun deleteReceita(idReceita: Int) {
        receitaDao.deleteReceita(idReceita = idReceita)
        stepDao.deleteAllStepsFromIdReceita(idReceita = idReceita)
        fotoDao.deleteAllFotosFromIdReceita(idReceita = idReceita)
    }

}