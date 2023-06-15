package br.applabbs.ricettario.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.applabbs.ricettario.data.local.dao.FotoDao
import br.applabbs.ricettario.data.local.dao.FotoRegistroDao
import br.applabbs.ricettario.data.local.dao.ReceitaDao
import br.applabbs.ricettario.data.local.dao.RegistroDao
import br.applabbs.ricettario.data.local.dao.StepDao
import br.applabbs.ricettario.data.local.entities.FotoEntity
import br.applabbs.ricettario.data.local.entities.FotoRegistroEntity
import br.applabbs.ricettario.data.local.entities.ReceitaEntity
import br.applabbs.ricettario.data.local.entities.RegistroEntity
import br.applabbs.ricettario.data.local.entities.StepEntity

@Database(
    entities = [
        ReceitaEntity::class,
        StepEntity::class,
        FotoEntity::class,
        RegistroEntity::class,
        FotoRegistroEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class LocalDB: RoomDatabase() {

    abstract fun stepDao() : StepDao
    abstract fun receitaDao(): ReceitaDao
    abstract fun fotoDao(): FotoDao
    abstract fun registroDao(): RegistroDao
    abstract fun fotoRegistroDao(): FotoRegistroDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: LocalDB? = null
        private const val DATABASE_NAME = "LocalDB"

        fun getDatabase(context: Context): LocalDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDB::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigrationFrom()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}