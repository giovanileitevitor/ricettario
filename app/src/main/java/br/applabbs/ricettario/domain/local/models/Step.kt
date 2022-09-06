package br.applabbs.ricettario.domain.local.models

data class Step(
    var id: Int?  = 0,
    var info: String
)

data class Steps(
    var steps: List<Step>
)