package br.applabbs.ricettario.data.local.dao

import androidx.room.*
import br.applabbs.ricettario.data.local.entities.StepEntity

@Dao
interface StepDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStep(step: StepEntity)

    @Query("SELECT * FROM stepTB WHERE isTemporary = :isTemporary")
    suspend fun getStepsfromDB(isTemporary: Boolean): List<StepEntity>

    @Query("SELECT * FROM stepTB WHERE idReceita = :idReceita")
    suspend fun getStepsFromIdReceita(idReceita: Int): List<StepEntity>

    @Query("DELETE FROM stepTB WHERE idStep = :idStep")
    fun deleteStep(idStep: Int)

    @Query("DELETE FROM stepTB WHERE isTemporary = :isTemporary")
    fun deleteAllTemporarySteps(isTemporary: Boolean)

    @Query("UPDATE stepTB SET idReceita = :idReceita WHERE isTemporary = :isTemporary")
    suspend fun updateSteps(idReceita : Int, isTemporary: Boolean)

    @Query("UPDATE stepTB SET isTemporary = :removeTemporaryFlag WHERE idReceita = :idReceita")
    fun removeTemporaryFlagStep(idReceita: Int, removeTemporaryFlag: Boolean)

    @Query("DELETE FROM stepTB WHERE idReceita = :idReceita")
    suspend fun deleteAllStepsFromIdReceita(idReceita: Int)

}