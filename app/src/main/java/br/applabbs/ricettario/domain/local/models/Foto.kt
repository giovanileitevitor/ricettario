package br.applabbs.ricettario.domain.local.models

data class Foto(
    var imgAddress: String
)

data class Fotos(
    var fotos: List<Foto>
)
