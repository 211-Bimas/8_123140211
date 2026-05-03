package org.example.project

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import org.koin.java.KoinJavaComponent.getKoin

// "actual" adalah eksekusi nyata khusus untuk Android
actual class BatteryInfo actual constructor() {
    actual fun getBatteryLevel(): String {
        return try {
            // Mengambil Context Android yang sudah didaftarkan Koin di MainActivity
            val context = getKoin().get<Context>()

            // Membaca status baterai dari sistem Android
            val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            val level = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
            val scale = intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

            if (level != -1 && scale != -1) {
                val batteryPct = level * 100 / scale
                "$batteryPct%"
            } else {
                "Level baterai tidak terbaca"
            }
        } catch (e: Exception) {
            // Fallback aman kalau Context Koin gagal dipanggil
            "Mendukung Fitur Baterai (Actual Android)"
        }
    }
}