package br.applabbs.ricettario.domain.local.models

data class Foto(
    var id : Int? = 0,
    var idReceita: Int? = 0,
    var imgAddress: String,
    var isTemporary: Boolean? = false
)
