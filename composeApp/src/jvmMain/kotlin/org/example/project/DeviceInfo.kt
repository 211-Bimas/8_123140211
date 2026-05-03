package org.example.project

// Di jvmMain, kita ambil info sistem operasi PC/Laptop
actual class DeviceInfo actual constructor() {
    actual val osName: String = System.getProperty("os.name") ?: "Desktop"
    actual val osVersion: String = System.getProperty("os.version") ?: "Unknown"
    actual val deviceModel: String = "PC/Laptop"
}