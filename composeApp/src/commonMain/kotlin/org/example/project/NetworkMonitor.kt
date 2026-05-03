package org.example.project

import kotlinx.coroutines.flow.Flow

// Expect declaration (fondasi)
expect class NetworkMonitor() {
    fun observeConnectivity(): Flow<Boolean>
}