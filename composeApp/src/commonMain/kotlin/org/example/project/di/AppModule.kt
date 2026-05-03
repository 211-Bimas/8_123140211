package org.example.project.di

import org.koin.dsl.module
import org.example.project.data.NotesRepository
import org.example.project.data.SettingsManager
import org.example.project.viewmodel.NotesViewModel
import org.example.project.viewmodel.ProfileViewModel
import org.example.project.DeviceInfo
import org.example.project.NetworkMonitor
import org.example.project.BatteryInfo

val sharedModule = module {
    // Daftarkan Repository & Settings sebagai Singleton (single)
    single { NotesRepository(get()) }
    single { SettingsManager(get()) }

    // Daftarkan Platform-Specific Features ke Koin
    single { DeviceInfo() }
    single { NetworkMonitor() }
    single { BatteryInfo() }

    // Daftarkan ViewModel sebagai Factory
    factory { NotesViewModel(get(), get()) }
    factory { ProfileViewModel() }
}