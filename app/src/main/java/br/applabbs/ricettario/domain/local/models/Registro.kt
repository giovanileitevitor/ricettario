package br.applabbs.ricettario.domain.local.models

data class Registro(
    val idRegistro: Int? = 0,
    val productName: String,
    val productBrand: String,
    val qtd: String,
    val productVality: String,
    val dateRegister: String,
    val hasImage: Boolean? = false,
    val imageAddress: String,
)
