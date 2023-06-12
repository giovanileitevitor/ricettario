package br.applabbs.ricettario.data.local.dao

import androidx.room.*
import androidx.room.Dao
import br.applabbs.ricettario.data.local.entities.RegistroEntity

@Dao
interface RegistroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRegistro(registro: RegistroEntity): Long

    @Query("SELECT * FROM registroTB")
    suspend fun getAllRegistros(): List<RegistroEntity>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateRegistro(registro: RegistroEntity)

    @Query("DELETE FROM registroTB WHERE idRegistro = :idRegistro")
    suspend fun deleteRegistro(idRegistro: Int)

}



