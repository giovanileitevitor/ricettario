package br.applabbs.ricettario.data.local.dao

import androidx.room.*
import br.applabbs.ricettario.data.local.entities.FotoEntity
import br.applabbs.ricettario.data.local.entities.FotoRegistroEntity

@Dao
interface FotoDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoto(foto: FotoEntity)

    @Query("SELECT * FROM fotoTB WHERE isTemporary = :isTemporary")
    suspend fun getFotosfromDB(isTemporary: Boolean): List<FotoEntity>

    @Query("DELETE FROM fotoTB WHERE idFoto = :idFoto")
    fun deleteFoto(idFoto: Int)

    @Query("DELETE FROM fotoTB WHERE isTemporary = :isTemporary")
    fun deleteAllTemporaryFotos(isTemporary: Boolean)

    @Query("UPDATE fotoTB SET idReceita = :idReceita WHERE isTemporary = :isTemporary")
    fun updateFotos(idReceita: Int, isTemporary: Boolean)

    @Query("UPDATE fotoTB SET isTemporary = :removeTemporaryFlag WHERE idReceita = :idReceita")
    fun removeTemporaryFlagFoto(idReceita: Int, removeTemporaryFlag: Boolean)

    @Query("DELETE FROM fotoTB WHERE idReceita = :idReceita")
    suspend fun deleteAllFotosFromIdReceita(idReceita: Int)
}