package com.example.yummynutrition.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.yummynutrition.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val mealId = intent.getIntExtra("MEAL_ID", 0)

        val notification = NotificationCompat.Builder(context, "MEAL_CHANNEL")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Hora de comer 🍽️")
            .setContentText("Es momento de tu comida $mealId")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(context)
            .notify(mealId, notification)
    }
}
