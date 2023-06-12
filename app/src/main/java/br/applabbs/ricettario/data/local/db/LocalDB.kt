package br.applabbs.ricettario.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.applabbs.ricettario.data.local.dao.FotoDao
import br.applabbs.ricettario.data.local.dao.ReceitaDao
import br.applabbs.ricettario.data.local.dao.RegistroDao
import br.applabbs.ricettario.data.local.dao.StepDao
import br.applabbs.ricettario.data.local.entities.FotoEntity
import br.applabbs.ricettario.data.local.entities.ReceitaEntity
import br.applabbs.ricettario.data.local.entities.RegistroEntity
import br.applabbs.ricettario.data.local.entities.StepEntity

@Database(
    entities = [
        ReceitaEntity::class,
        StepEntity::class,
        FotoEntity::class,
        RegistroEntity::class
    ],
    version = 3,
    exportSchema = false
)

abstract class LocalDB: RoomDatabase() {

    abstract fun stepDao() : StepDao
    abstract fun receitaDao(): ReceitaDao
    abstract fun fotoDao(): FotoDao
    abstract fun registroDao(): RegistroDao

    companion object{

        private const val DATABASE_NAME = "LocalDB"

        fun createDatabase(context: Context): LocalDB{
            return Room.databaseBuilder(
                context.applicationContext,
                LocalDB::class.java,
                DATABASE_NAME
                ).fallbackToDestructiveMigration()
                .build()

        }
    }

}