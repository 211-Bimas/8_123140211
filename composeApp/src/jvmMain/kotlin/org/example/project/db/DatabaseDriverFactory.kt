package org.example.project.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.io.File

// Ini adalah implementasi khusus Desktop (PC/Laptop)
actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:notes_desktop.db")
        try {
            NotesDatabase.Schema.create(driver)
        } catch (e: Exception) {
            // Abaikan jika tabel sudah ada
        }
        return driver
    }
}