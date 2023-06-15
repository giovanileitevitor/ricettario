package br.applabbs.ricettario.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fotoRegistroTB")
data class FotoRegistroEntity(

    @ColumnInfo(name = "idFoto")
    @PrimaryKey(autoGenerate = true)
    val idFoto: Int? = null,

    @ColumnInfo(name = "idReceita")
    val idRegistro: Int? = null,

    @ColumnInfo(name = "imgAddress")
    val imgAddress: String

)
