package org.example.project.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import orgexampleproject.db.Note
import org.example.project.db.NotesDatabase
import kotlinx.datetime.Clock

class NotesRepository(database: NotesDatabase) {
    private val queries = database.noteQueries

    fun getAllNotes(): Flow<List<Note>> {
        return queries.selectAll().asFlow().mapToList(Dispatchers.Default)
    }

    fun getNoteById(id: Long): Flow<Note?> {
        return queries.selectById(id).asFlow().mapToOneOrNull(Dispatchers.Default)
    }

    fun searchNotes(query: String): Flow<List<Note>> {
        return queries.search(title = "%$query%", content = "%$query%").asFlow().mapToList(Dispatchers.Default)
    }

    fun insertNote(title: String, content: String) {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        queries.insert(title, content, currentTime, currentTime)
    }

    fun updateNote(id: Long, title: String, content: String) {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        queries.update(title, content, currentTime, id)
    }

    fun deleteNote(id: Long) {
        queries.delete(id)
    }

    // <--- TAMBAHAN BUAT FAVORIT --->
    fun updateFavoriteStatus(id: Long, isFavorite: Long) {
        queries.updateFavorite(is_favorite = isFavorite, id = id)
    }
}