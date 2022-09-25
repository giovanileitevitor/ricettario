package br.applabbs.ricettario.domain.local.usecases

import br.applabbs.ricettario.data.local.entities.FotoEntity
import br.applabbs.ricettario.data.local.entities.ReceitaEntity
import br.applabbs.ricettario.data.local.entities.StepEntity
import br.applabbs.ricettario.data.local.repositories.LocalRepository
import br.applabbs.ricettario.domain.local.models.Foto
import br.applabbs.ricettario.domain.local.models.Receita
import br.applabbs.ricettario.domain.local.models.Step

class ReceitaUseCaseImpl(
    private val localRepository: LocalRepository
): ReceitaUseCase {

    override suspend fun insertStepReceita(step: Step) {
        localRepository.insertStepIntoDB(convertToStepEntity(step = step))
    }

    override suspend fun getSteps(isTemporary: Boolean): List<Step> {
        return convertStepEntityIntoStep(localRepository.getStepsfromDB(isTemporary = isTemporary))
    }

    override suspend fun getStepsFromIdReceita(idReceita: Int): List<Step> {
        return convertStepEntityIntoStep(localRepository.getStepsFromIdReceita(idReceita = idReceita))
    }

    override suspend fun deleteStep(idStep: Int) {
        localRepository.deleteStepIntoDB(idStep = idStep)
    }

    override suspend fun deleteAllTemporarySteps(isTemporary: Boolean) {
        localRepository.deleteAllTemporarySteps(isTemporary = isTemporary)
    }

    override suspend fun updateSteps(idReceita: Int) {
        localRepository.updateStepsIntoDB(idReceita = idReceita)
    }

    override suspend fun insertFotoReceita(foto: Foto) {
        localRepository.insertFotoReceita(convertToFotoEntity(foto = foto))
    }

    override suspend fun getFotos(isTemporary: Boolean): List<Foto> {
        return convertFotoEntityIntoFoto(localRepository.getFotosfromDB(isTemporary = isTemporary))
    }

    override suspend fun deleteFoto(idFoto: Int) {
        localRepository.deleteFotoIntoDB(idFoto = idFoto)
    }

    override suspend fun deleteAllTemporaryFotos(isTemporary: Boolean) {
        localRepository.deleteAllTemporaryFotos(isTemporary)
    }

    override suspend fun insertReceita(tituloReceita: String, steps: List<Step>, fotos: List<Foto>): Long {
        return localRepository.insertReceita(
            ReceitaEntity(
                titulo = tituloReceita,
                isFavorite = false,
                imgPrincipal = fotos[0].imgAddress,
                qtdSteps = steps.size,
                qtdFotos = fotos.size
            )
        )
    }

    override suspend fun updateFotos(idReceita: Int) {
        localRepository.updateFotosIntoDB(idReceita = idReceita)
    }

    override suspend fun getAllReceitas(): List<Receita> {
        return convertReceitaEntityToReceita(localRepository.getAllReceitas())
    }

    override suspend fun setReceitaAsFavorite(idReceita: Int) {
        localRepository.setReceitaAsFavorite(idReceita = idReceita)
    }

    override suspend fun deleteReceita(idReceita: Int) {
        localRepository.deleteReceita(idReceita = idReceita)
    }


    private fun convertToStepEntity(step: Step): StepEntity{
        return StepEntity(
            info = step.info,
            isTemporary = step.isTemporary
        )
    }

    private fun convertStepEntityIntoStep(steps: List<StepEntity>) : List<Step>{
        val stepsList = mutableListOf<Step>()
        steps.forEach {
            stepsList.add(
                Step(
                    id = it.idStep,
                    info = it.info,
                    isTemporary = it.isTemporary
                )
            )
        }
        return stepsList
    }

    private fun convertToFotoEntity(foto: Foto): FotoEntity{
        return FotoEntity(
            imgAddress = foto.imgAddress,
            isTemporary = foto.isTemporary
        )
    }

    private fun convertFotoEntityIntoFoto(fotos: List<FotoEntity>) : List<Foto>{
        val fotosList = mutableListOf<Foto>()
        fotos.forEach {
            fotosList.add(
                Foto(
                    id = it.idFoto,
                    imgAddress = it.imgAddress,
                    isTemporary = it.isTemporary
                )
            )
        }
        return fotosList
    }

    private fun convertReceitaEntityToReceita(receitas: List<ReceitaEntity>): List<Receita>{
        val receitasList = mutableListOf<Receita>()
        receitas.forEach {
            receitasList.add(
                Receita(
                    id = it.idReceita ?: 0,
                    titulo = it.titulo ?: "",
                    img = it.imgPrincipal ?: "",
                    isFavorito = it.isFavorite ?: false,
                    steps = emptyList(),
                    fotos = emptyList()
                )
            )
        }

        return receitasList
    }

}