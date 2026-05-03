package org.example.project.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

import orgexampleproject.db.Note

// --- 1. LAYAR DAFTAR CATATAN ---
@Composable
fun NoteListScreen(
    notes: List<Note>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onNoteClick: (Long) -> Unit,
    onAddClick: () -> Unit,
    onFavoriteClick: (Note) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddClick,
                icon = { Icon(Icons.Default.Add, "Tambah") },
                text = { Text("Catatan Baru") },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                placeholder = { Text("Cari catatan...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            )

            if (notes.isEmpty()) {
                val message = if (searchQuery.isEmpty()) "Belum ada catatan.\nYuk, tulis sesuatu!" else "Catatan tidak ditemukan."
                EmptyStateView(icon = Icons.Default.Create, message = message)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(notes) { note -> NoteCard(note, onNoteClick, onFavoriteClick) }
                }
            }
        }
    }
}

// --- 2. LAYAR FAVORIT (SUDAH DIPERBAIKI!) ---
@Composable
fun FavoritesScreen(
    notes: List<Note>,
    onNoteClick: (Long) -> Unit,
    onFavoriteClick: (Note) -> Unit
) {
    if (notes.isEmpty()) {
        EmptyStateView(
            icon = Icons.Outlined.FavoriteBorder,
            message = "Belum ada catatan favorit.\nKlik icon Love untuk menambahkan!"
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(notes) { note ->
                NoteCard(
                    note = note,
                    onClick = onNoteClick,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}

// --- KOMPONEN EMPTY STATE ---
@Composable
fun EmptyStateView(icon: androidx.compose.ui.graphics.vector.ImageVector, message: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Kosong",
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

// --- KOMPONEN KARTU CATATAN ---
@Composable
fun NoteCard(note: Note, onClick: (Long) -> Unit, onFavoriteClick: (Note) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick(note.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = note.content,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
            }

            IconButton(onClick = { onFavoriteClick(note) }) {
                Icon(
                    imageVector = if (note.is_favorite == 1L) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Tombol Favorit",
                    tint = if (note.is_favorite == 1L) Color.Red else Color.Gray
                )
            }
        }
    }
}

// --- 3. LAYAR TAMBAH CATATAN ---
@Composable
fun AddNoteScreen(onSave: (String, String) -> Unit, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Tulis Catatan Baru", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul Catatan") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Isi Catatan...") },
            modifier = Modifier.fillMaxWidth().weight(1f),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = onBack, modifier = Modifier.padding(end = 8.dp)) { Text("Batal") }
            Button(onClick = { if (title.isNotBlank()) { onSave(title, content); onBack() } }) { Text("Simpan") }
        }
    }
}

// --- 4. LAYAR DETAIL CATATAN ---
@Composable
fun NoteDetailScreen(note: Note?, onBack: () -> Unit, onEdit: (Long) -> Unit, onDelete: (Long) -> Unit) {
    if (note == null) return
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(note.title, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(24.dp))

        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.5
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = onBack) { Text("Kembali") }
            Row {
                OutlinedButton(
                    onClick = { onDelete(note.id); onBack() },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                    modifier = Modifier.padding(end = 8.dp)
                ) { Text("Hapus") }
                Button(onClick = { onEdit(note.id) }) { Text("Edit") }
            }
        }
    }
}

// --- 5. LAYAR EDIT CATATAN ---
@Composable
fun EditNoteScreen(note: Note?, onSave: (Long, String, String) -> Unit, onBack: () -> Unit) {
    if (note == null) return
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Edit Catatan", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul Catatan") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Isi Catatan...") },
            modifier = Modifier.fillMaxWidth().weight(1f),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = onBack, modifier = Modifier.padding(end = 8.dp)) { Text("Batal") }
            Button(onClick = { if (title.isNotBlank()) { onSave(note.id, title, content); onBack() } }) { Text("Simpan") }
        }
    }
}

// --- 6. LAYAR ABOUT ---
@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Info",
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(16.dp))
        Text("My Notes App", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Versi 2.0 (SQL Database)", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(24.dp))
        Text(
            text = "Dibuat oleh Muhammad Bimastiar (123140211) untuk Tugas Praktikum PAM Minggu 7.\nSudah Offline-first menggunakan SQLDelight dan DataStore!",
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}