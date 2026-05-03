package org.example.project

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class NetworkMonitor actual constructor() {
    // Di PC kita anggap selalu nyala internetnya
    actual fun observeConnectivity(): Flow<Boolean> = flowOf(true)
}