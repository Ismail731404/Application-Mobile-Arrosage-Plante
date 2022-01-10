package fr.uparis.diderot

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.activity.viewModels
import androidx.room.Room
import fr.uparis.diderot.data.AppDatabase


class AppApplication : Application() {

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { AppRepository(database.wateringDao()) }


    override fun onCreate() {
        super.onCreate()
        datbase = Room.databaseBuilder(this,AppDatabase::class.java,"Pays")
            .build()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Watering Plant",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    companion object {
        const val CHANNEL_ID = "planting"
        lateinit var datbase: AppDatabase

    }
}