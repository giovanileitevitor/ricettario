package br.applabbs.ricettario.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fotoTB")
data class FotoEntity(

    @ColumnInfo(name = "idFoto")
    @PrimaryKey(autoGenerate = true)
    val idFoto: Int? = null,

    @ColumnInfo(name = "idReceita")
    val idReceita: Int? = null,

    @ColumnInfo(name = "imgAddress")
    val imgAddress: String,

    @ColumnInfo(name = "isTemporary")
    val isTemporary: Boolean? = false
)
