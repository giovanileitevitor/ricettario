package br.applabbs.ricettario.domain.local.usecases

import br.applabbs.ricettario.data.local.entities.RegistroEntity
import br.applabbs.ricettario.data.local.repositories.LocalRepository
import br.applabbs.ricettario.domain.local.models.Registro

class InventarioUseCaseImpl(
    private val localRepository: LocalRepository
): InventarioUseCase {

    override suspend fun addRegistro(registro: Registro) {
        localRepository.addRegistro(registro = convertRegistro(input = registro))
    }

    override suspend fun updateRegistro(registro: Registro) {
        localRepository.updateRegistro(registro = convertRegistro(input = registro))
    }

    override suspend fun getAllRegistros(): List<Registro> {
        return convertRegistroEntityIntoRegistro(localRepository.getAllRegistros())
    }

    override suspend fun deleteRegistro(registro: Registro) {
        localRepository.deleteRegistro(registro = convertRegistro(input = registro))
    }

    private fun convertRegistro(input: Registro): RegistroEntity{
        return RegistroEntity(
            idRegistro = input.idRegistro,
            productName = input.productName,
            productBrand = input.productBrand,
            qtd = input.qtd,
            productVality = input.productVality,
            dateRegister = input.dateRegister,
            hasImage = input.hasImage,
            imageAddress = input.imageAddress
        )
    }

    private fun convertRegistroEntity(input: RegistroEntity): Registro{
        return Registro(
            idRegistro = input.idRegistro ?: 0,
            productName = input.productName ?: "",
            productBrand = input.productBrand ?: "",
            qtd = input.qtd ?: "",
            productVality = input.productVality ?: "",
            dateRegister = input.dateRegister ?: "",
            hasImage = input.hasImage ?: false,
            imageAddress = input.imageAddress ?: ""
        )
    }

    private fun convertRegistroEntityIntoRegistro(registros: List<RegistroEntity>) : List<Registro>{
        val registroList = mutableListOf<Registro>()
        registros.forEach {
            registroList.add(
                Registro(
                    idRegistro = it.idRegistro,
                    productName = it.productName ?: "",
                    productBrand = it.productBrand ?: "",
                    qtd = it.qtd ?: "",
                    productVality = it.productVality ?: "",
                    dateRegister = it.dateRegister ?: "",
                    hasImage = it.hasImage ?: false,
                    imageAddress = it.imageAddress ?: ""
                )
            )
        }
        return registroList
    }

}
