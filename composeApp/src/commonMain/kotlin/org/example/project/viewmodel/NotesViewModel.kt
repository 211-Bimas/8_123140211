package org.example.project.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.example.project.data.NotesRepository
import org.example.project.data.SettingsManager
import orgexampleproject.db.Note

sealed class NotesUiState {
    object Loading : NotesUiState()
    object Empty : NotesUiState()
    data class Success(val notes: List<Note>) : NotesUiState()
}

class NotesViewModel(
    private val repository: NotesRepository,
    val settingsManager: SettingsManager
) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val uiState: StateFlow<NotesUiState> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.getAllNotes()
            } else {
                repository.searchNotes(query)
            }
        }
        .map { notes ->
            if (notes.isEmpty()) NotesUiState.Empty else NotesUiState.Success(notes)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NotesUiState.Loading
        )

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.insertNote(title, content)
        }
    }

    fun updateNote(id: Long, title: String, content: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.updateNote(id, title, content)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.deleteNote(id)
        }
    }

    fun getNoteById(id: Long): Flow<Note?> {
        return repository.getNoteById(id)
    }

    // <--- TAMBAHAN BUAT FAVORIT --->
    fun toggleFavorite(note: Note) {
        val newFavoriteStatus = if (note.is_favorite == 1L) 0L else 1L
        viewModelScope.launch(Dispatchers.Default) {
            repository.updateFavoriteStatus(note.id, newFavoriteStatus)
        }
    }
}