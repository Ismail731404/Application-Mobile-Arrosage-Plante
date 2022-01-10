package fr.uparis.diderot

import android.app.PendingIntent
import android.app.Service
import android.app.TaskStackBuilder
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AppService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.i("Alarme122", "In onStarttCommand: ")
        val intent = Intent(this, AppWateringPlantActivity::class.java)

        val pendingIntent : PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val notification = NotificationCompat.Builder(this, AppApplication.CHANNEL_ID)
            .setContentTitle("Arrosage des plants")
            .setContentText("Cliquer pour voir les plants à arroser aujourd'hui")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.plante)
            .build()


        with(NotificationManagerCompat.from(this)) {
            notify(1, notification)
        }

        return START_NOT_STICKY

    }
}