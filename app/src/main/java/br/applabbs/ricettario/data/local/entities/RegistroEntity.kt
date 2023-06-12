package br.applabbs.ricettario.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "registroTB")
data class RegistroEntity (

    @ColumnInfo(name = "idRegistro")
    @PrimaryKey(autoGenerate = true)
    val idRegistro: Int? = null,

    @ColumnInfo(name = "productName")
    val productName: String? = "",

    @ColumnInfo(name = "productBrand")
    val productBrand: String? = "",

    @ColumnInfo(name = "qtd")
    val qtd: String? = "",

    @ColumnInfo(name = "productVality")
    val productVality: String? = "",

    @ColumnInfo(name = "dateRegister")
    val dateRegister: String? = "",

    @ColumnInfo(name = "hasImage")
    val hasImage: Boolean? = false,

    @ColumnInfo(name = "imageAddress")
    val imageAddress: String? = ""
)