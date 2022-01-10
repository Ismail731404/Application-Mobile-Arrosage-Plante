package fr.uparis.diderot

import android.app.Application
import fr.uparis.diderot.data.AppDatabase


class AppApplication : Application() {

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { AppRepository(database.wateringDao()) }
}