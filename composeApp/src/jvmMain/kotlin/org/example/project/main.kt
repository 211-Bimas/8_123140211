package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.russhwolf.settings.PreferencesSettings
import org.example.project.db.DatabaseDriverFactory
import java.util.prefs.Preferences

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Notes App",
    ) {
        // Membuat mesin Driver & Settings khusus Komputer
        val driverFactory = DatabaseDriverFactory()
        val preferences = Preferences.userRoot()
        val settings = PreferencesSettings(preferences)

        App(driverFactory = driverFactory, settings = settings) // Kirim ke App.kt
    }
}