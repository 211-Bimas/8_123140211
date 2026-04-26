package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.content.Context
import com.russhwolf.settings.SharedPreferencesSettings
import org.example.project.db.DatabaseDriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Membuat mesin Driver & Settings khusus HP Android
        val driverFactory = DatabaseDriverFactory(this)
        val sharedPrefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val settings = SharedPreferencesSettings(sharedPrefs)

        setContent {
            App(driverFactory = driverFactory, settings = settings) // Kirim ke App.kt
        }
    }
}