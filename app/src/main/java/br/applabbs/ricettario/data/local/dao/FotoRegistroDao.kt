package br.applabbs.ricettario.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.applabbs.ricettario.data.local.entities.FotoRegistroEntity

@Dao
interface FotoRegistroDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoto(foto: FotoRegistroEntity)

    @Query("DELETE FROM fotoRegistroTB WHERE idFoto = :idFoto")
    suspend fun deleteFoto(idFoto: Int)

    @Query("SELECT * FROM fotoRegistroTB")
    suspend fun getFotosfromDB(): List<FotoRegistroEntity>


}