package com.example.notification


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        createNotificationChannel()

        val type = remoteMessage.data["type"]?.let {
            NotificationType.valueOf(it)
        }
        val title = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]

        type ?: return



        NotificationManagerCompat.from(this)
            .notify(type.id, createNotification(type, title, message))
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESCRIPTION
            //채널을 notification 매니저에 추가
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)

        }
    }

    private fun createNotification(
        type: NotificationType,
        title: String?,
        message: String?
    ): Notification {
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        when (type) {
            NotificationType.NORMAL -> Unit
            NotificationType.EXPANDABLE -> {
                notificationBuilder.setStyle(
                    NotificationCompat.BigTextStyle().bigText(
                        "\uD83D\uDE0D\uD83D\uDE0D" +
                                "\uD83D\uDE0D\uD83D\uDE0D" +
                                "\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D" + "\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D" + "\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D"
                    )
                )
            }
            NotificationType.CUSTOM -> {
                notificationBuilder.setStyle(
                    NotificationCompat.DecoratedCustomViewStyle()
                ).setCustomContentView(
                    RemoteViews(
                        packageName, R.layout.view_custom_notification
                    ).apply { //setText로 바로 못한다.
                        setTextViewText(R.id.txt_title, title)
                        setTextViewText(R.id.txt_message, message)
                    }
                )

            }
        }
        return notificationBuilder.build()
    }

    companion object {
        private const val CHANNEL_NAME = "Emoji Party"
        private const val CHANNEL_DESCRIPTION = "Emoji party를 위한 채널 "
        private const val CHANNEL_ID = "Channel_Id"
    }
}