package com.example.composefcm.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.composefcm.R
import com.example.composefcm.constant.Constants.CHANNEL_ID
import com.example.composefcm.constant.Constants.CHANNEL_NAME
import com.example.composefcm.constant.Constants.NOTIFICATION_ID
import com.example.composefcm.constant.Constants.body
import com.example.composefcm.constant.Constants.title
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        initiateNotificationChannel(notificationManager)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if(remoteMessage.notification != null){
            title = remoteMessage.notification!!.title!!
            body = remoteMessage.notification!!.body!!

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//            set FCM notification to the notification content
        var notificationBuilder = if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT){
            NotificationCompat.Builder(this, CHANNEL_ID)
        }else{
            NotificationCompat.Builder(this)
        }
            notificationBuilder = notificationBuilder
                .setSmallIcon(R.drawable.autochek_logo)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setAutoCancel(true)

            initiateNotificationChannel(notificationManager)
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())


        }
    }

    private fun initiateNotificationChannel(notificationManager: NotificationManager){
        if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT){
            notificationManager.createNotificationChannelIfNoneExists(
                CHANNEL_ID,
                CHANNEL_NAME
            )
        }
    }
}

//create notification channel

@RequiresApi(Build.VERSION_CODES.O)
fun NotificationManager.createNotificationChannelIfNoneExists(
    channelId : String,
    channelName : String,
    importance : Int = NotificationManager.IMPORTANCE_HIGH
){
    var channel = this.getNotificationChannel(channelId)

    if (channel == null) {
        channel = NotificationChannel(channelId,channelName,importance)

        this.createNotificationChannel(channel)
    }

}

