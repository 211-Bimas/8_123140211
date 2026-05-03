package org.example.project

// Di commonMain, kita pakai 'expect'
expect class DeviceInfo() {
    val osName: String
    val osVersion: String
    val deviceModel: String
}