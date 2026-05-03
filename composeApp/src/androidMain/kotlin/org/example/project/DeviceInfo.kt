package org.example.project

import android.os.Build

// Di androidMain, kita pakai 'actual' untuk ngambil data HP Android
actual class DeviceInfo actual constructor() {
    actual val osName: String = "Android"
    actual val osVersion: String = "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
    actual val deviceModel: String = "${Build.MANUFACTURER} ${Build.MODEL}"
}