package org.example.project

// "expect" berarti kita menuntut tiap OS (Android/iOS/PC) punya caranya sendiri
expect class BatteryInfo() {
    fun getBatteryLevel(): String
}