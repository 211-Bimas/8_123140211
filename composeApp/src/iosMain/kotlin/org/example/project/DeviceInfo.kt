package org.example.project

import platform.UIKit.UIDevice

actual class DeviceInfo actual constructor() {
    actual val osName: String = UIDevice.currentDevice.systemName
    actual val osVersion: String = UIDevice.currentDevice.systemVersion
    actual val deviceModel: String = UIDevice.currentDevice.model
}
