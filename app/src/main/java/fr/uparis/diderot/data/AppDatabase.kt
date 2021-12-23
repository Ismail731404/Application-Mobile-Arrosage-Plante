package fr.uparis.diderot.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.uparis.diderot.data.dao.Watering_Nutriment_PlantDao
import fr.uparis.diderot.data.entity.Arronsage_En_saison
import fr.uparis.diderot.data.entity.Converters
import fr.uparis.diderot.data.entity.Nutriment
import fr.uparis.diderot.data.entity.Watering_Plant
import kotlinx.coroutines.CoroutineScope

/**
 *
 */
@Database(entities = [Watering_Plant::class,Arronsage_En_saison::class,Nutriment::class],version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wateringDao(): Watering_Nutriment_PlantDao



    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "word_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


}
