package br.applabbs.ricettario.domain.local.models

data class Step(
    var id: Int? = 0,
    var idReceita: Int? = 0,
    var info: String,
    var isTemporary: Boolean? = false
)