package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.data.ProfileUiState

class ProfileViewModel : ViewModel() {
    // Menyimpan state di dalam ViewModel menggunakan StateFlow
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    // Fungsi untuk update Nama dan Bio (Fitur Edit Profile)
    fun updateProfile(newName: String, newBio: String) {
        _uiState.update { currentState ->
            currentState.copy(
                name = newName,
                bio = newBio
            )
        }
    }

    // Fungsi untuk update status Dark Mode
    fun toggleDarkMode(isDark: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isDarkMode = isDark)
        }
    }
}