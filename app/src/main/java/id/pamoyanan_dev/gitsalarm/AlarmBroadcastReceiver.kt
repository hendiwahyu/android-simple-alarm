package id.pamoyanan_dev.gitsalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import id.pamoyanan_dev.gitsalarm.NotificationUtil.createNotificationChannel
import java.text.SimpleDateFormat
import java.util.*

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            // pengecekan dilakukan agar notifikasi tidak muncul berulang
            if (getTimeNow() == intent.getStringExtra("validationTime")) {
                if (context != null) createNotificationChannel(context)
            }

            if (intent.action == "android.intent.action.TIME_SET") {
                context?.stopService(Intent(context, AlarmService::class.java))

                // langkah ini dilakukan untuk memicu ulang agar service kembali menyala
                // setelah melakukan uji coba mengganti tanggal service mati
                Handler().postDelayed({ context?.startService(Intent(context, AlarmService::class.java)) }, 1000)
            }
        }
    }

    private fun getTimeNow(): String {
        val dateTimeMillis = System.currentTimeMillis()

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateTimeMillis

        return SimpleDateFormat("HH:mm:ss").format(calendar.time)
    }

}