package br.applabbs.ricettario.domain.local.usecases

import br.applabbs.ricettario.domain.local.models.Registro

interface InventarioUseCase {

    suspend fun addRegistro(registro: Registro)
    suspend fun updateRegistro(registro: Registro)
    suspend fun getAllRegistros(): List<Registro>
    suspend fun deleteRegistro(registro: Registro)

}
