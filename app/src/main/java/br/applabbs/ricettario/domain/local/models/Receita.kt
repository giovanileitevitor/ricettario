package br.applabbs.ricettario.domain.local.models

data class Receita(
    var id: Int,
    var titulo: String,
    var detalhes: String,
    var img: String,
    var isFavorito: Boolean,
    var steps: List<Step> = emptyList(),
    var fotos: List<Foto> = emptyList()
)

data class ReceitaCompleta(
    var id: Int,
    var titulo: String,
    var img: String,
    var isFavorito: Boolean,
    var steps: List<String>,
    var imgs: List<String>,
    var comentarios: List<String>,
    var atualizadoEm: String?
)