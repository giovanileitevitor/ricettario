package br.applabbs.ricettario.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receitaTB")
data class ReceitaEntity (

    @ColumnInfo(name = "idReceita")
    @PrimaryKey(autoGenerate = true)
    val idReceita: Int? = null,

    @ColumnInfo(name = "titulo")
    val titulo: String? = "",

    @ColumnInfo(name = "imgPrincipal")
    val imgPrincipal: String? = "",

    @ColumnInfo(name = "qtdStep")
    val qtdSteps: Int? = null,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean? = false,

    @ColumnInfo(name = "qtdFotos")
    val qtdFotos: Int? = null
)