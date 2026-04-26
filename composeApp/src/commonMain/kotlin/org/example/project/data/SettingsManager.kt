package org.example.project.data

// --- Menggunakan ObservableSettings ---
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.flow.Flow

class SettingsManager(settings: ObservableSettings) {
    private val flowSettings: FlowSettings = settings.toFlowSettings()

    // Key untuk DataStore
    companion object {
        private const val KEY_THEME = "app_theme"
        private const val KEY_SORT_ORDER = "sort_order"
    }

    // Mengambil data sebagai Flow (Reactive)
    val themeFlow: Flow<String> = flowSettings.getStringFlow(KEY_THEME, "system")
    val sortOrderFlow: Flow<String> = flowSettings.getStringFlow(KEY_SORT_ORDER, "DESC")

    // Fungsi untuk mengubah data (Menyimpan secara lokal)
    suspend fun setTheme(theme: String) {
        flowSettings.putString(KEY_THEME, theme)
    }

    suspend fun setSortOrder(order: String) {
        flowSettings.putString(KEY_SORT_ORDER, order)
    }
}