package org.example.project.db

import app.cash.sqldelight.db.SqlDriver

// Ini adalah "blueprint" (cetak biru) untuk semua platform
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}