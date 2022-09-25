package br.applabbs.ricettario.domain.local.models

data class Receita(
    var id: Int,
    var titulo: String,
    var img: String,
    var isFavorito: Boolean,
    var steps: List<Step> = emptyList(),
    var fotos: List<Foto> = emptyList()
)