package br.applabbs.ricettario.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stepTB")
data class StepEntity(

    @ColumnInfo(name = "idStep")
    @PrimaryKey(autoGenerate = true)
    val idStep: Int? = null,

    @ColumnInfo(name = "idReceita")
    val idReceita: Int? = null,

    @ColumnInfo(name = "info")
    val info: String,

    @ColumnInfo(name = "isTemporary")
    val isTemporary: Boolean? = false
)
