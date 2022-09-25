package br.applabbs.ricettario.data.local.dao

import androidx.room.*
import br.applabbs.ricettario.data.local.entities.ReceitaEntity

@Dao
interface ReceitaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceita(receita: ReceitaEntity): Long

    @Query("SELECT * FROM receitaTB")
    suspend fun getReceitas(): List<ReceitaEntity>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateReceita(receita: ReceitaEntity)

    @Query("UPDATE receitaTB SET isFavorite = :isFavorite WHERE idReceita = :idReceita")
    suspend fun setReceitaAsFavorite(idReceita: Int, isFavorite : Boolean)

    @Query("DELETE FROM receitaTB WHERE idReceita = :idReceita")
    suspend fun deleteReceita(idReceita: Int)


}



