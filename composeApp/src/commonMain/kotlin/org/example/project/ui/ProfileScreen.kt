package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.ProfileUiState

// ============================================================
// WARNA TEMA
// ============================================================
object AppColors {
    val HeaderTop        = Color(0xFFAED581)
    val HeaderBottom     = Color(0xFF558B2F)
    val IconEmail        = Color(0xFF4CAF50)
    val IconPhone        = Color(0xFF2196F3)
    val IconLocation     = Color(0xFFFF5722)
    val IconWebsite      = Color(0xFF9C27B0)
    val IconSkill        = Color(0xFFFF6584)
    val BtnActive        = Color(0xFF7CB342)
    val BtnDefault       = Color(0xFF9E9E9E)
    val DividerColor     = Color(0xFFE0E0E0)
}

data class SkillInfo(
    val icon: ImageVector,
    val label: String,
    val value: String
)

// ============================================================
// COMPOSABLE 1: ProfileHeader (Ditambah Switch Dark Mode)
// ============================================================
@Composable
fun ProfileHeader(
    name: String,
    title: String,
    bio: String,
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(AppColors.HeaderTop, AppColors.HeaderBottom)
                )
            )
            .padding(bottom = 36.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // FITUR BARU: Switch Dark Mode di pojok kanan atas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isDarkMode) "Dark Mode" else "Light Mode",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = onToggleDarkMode,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = AppColors.HeaderBottom,
                        checkedTrackColor = Color.White,
                        uncheckedThumbColor = Color.LightGray,
                        uncheckedTrackColor = Color.White.copy(alpha = 0.5f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Foto profil circular
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(110.dp)
                    .border(3.dp, Color.White.copy(alpha = 0.8f), CircleShape)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(102.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Foto Profil",
                        modifier = Modifier.size(58.dp),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Nama (Dari ViewModel)
            Text(
                text = name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Title
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.18f))
                    .padding(horizontal = 16.dp, vertical = 5.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(14.dp))
            Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(1.dp)
                    .background(Color.White.copy(alpha = 0.4f))
            )
            Spacer(modifier = Modifier.height(14.dp))

            // Bio (Dari ViewModel)
            Text(
                text = bio,
                fontSize = 13.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 21.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

// ============================================================
// COMPOSABLE 2 & 3: Komponen UI Aslimu
// ============================================================
@Composable
fun StatItem(value: String, label: String, textColor: Color, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = textColor)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = label, fontSize = 11.sp, color = Color.Gray)
    }
}

@Composable
fun InfoItem(
    icon: ImageVector, label: String, value: String, iconTint: Color,
    textColorPrimary: Color, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(40.dp).clip(CircleShape).background(iconTint.copy(alpha = 0.12f))
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = iconTint, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, fontSize = 11.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, fontSize = 14.sp, color = textColorPrimary, fontWeight = FontWeight.SemiBold)
        }
        Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = null, tint = AppColors.DividerColor, modifier = Modifier.size(16.dp))
    }
    Divider(color = AppColors.DividerColor, thickness = 0.5.dp, modifier = Modifier.padding(start = 54.dp))
}

@Composable
fun ProfileCard(
    title: String, containerColor: Color, modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                text = title.uppercase(), fontSize = 11.sp, fontWeight = FontWeight.Bold,
                color = AppColors.BtnActive, letterSpacing = 1.5.sp, modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )
            content()
        }
    }
}

// ============================================================
// COMPOSABLE BARU: Form Edit Profil
// ============================================================
@Composable
fun EditProfileDialog(
    currentName: String,
    currentBio: String,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var nameInput by remember { mutableStateOf(currentName) }
    var bioInput by remember { mutableStateOf(currentBio) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Edit Profil") },
        text = {
            Column {
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Nama Lengkap") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = bioInput,
                    onValueChange = { bioInput = it },
                    label = { Text("Bio") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )
            }
        },
        confirmButton = { Button(onClick = { onSave(nameInput, bioInput) }) { Text("Simpan") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Batal") } }
    )
}

// ============================================================
// HALAMAN UTAMA: ProfileScreen (MVVM Version)
// ============================================================
@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onEditProfile: (String, String) -> Unit,
    onToggleDarkMode: (Boolean) -> Unit
) {
    var isFollowing by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    // Ambil warna dinamis dari MaterialTheme (Agar Dark Mode berfungsi pada UI lama kamu)
    val bgColor = MaterialTheme.colorScheme.background
    val surfaceColor = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onBackground

    val skills = listOf(
        SkillInfo(Icons.Filled.Code, "Mobile Development", "Kotlin, Compose"),
        SkillInfo(Icons.Filled.Storage, "Data Management", "MySQL, MongoDB"),
        SkillInfo(Icons.Filled.DesignServices, "Web Development", "HTML, CSS, JavaScript")
    )

    if (showEditDialog) {
        EditProfileDialog(
            currentName = uiState.name,
            currentBio = uiState.bio,
            onDismiss = { showEditDialog = false },
            onSave = { newName, newBio ->
                onEditProfile(newName, newBio)
                showEditDialog = false
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().background(bgColor).verticalScroll(scrollState)
    ) {
        // 1. Header
        ProfileHeader(
            name = uiState.name,
            title = uiState.title,
            bio = uiState.bio,
            isDarkMode = uiState.isDarkMode,
            onToggleDarkMode = onToggleDarkMode
        )

        // 2. Stat card melayang
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).offset(y = (-20).dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(value = "12", label = "Proyek", textColor = textColor)
                Divider(modifier = Modifier.height(36.dp).width(1.dp), color = AppColors.DividerColor)
                StatItem(value = "4.00", label = "IPK", textColor = textColor)
                Divider(modifier = Modifier.height(36.dp).width(1.dp), color = AppColors.DividerColor)
                StatItem(value = "6", label = "Semester", textColor = textColor)
            }
        }

        // 3. Tombol Follow & Tombol Edit (BERSEBELAHAN)
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { isFollowing = !isFollowing },
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFollowing) AppColors.BtnActive else AppColors.BtnDefault
                )
            ) {
                Icon(
                    imageVector = if (isFollowing) Icons.Filled.Check else Icons.Filled.PersonAdd,
                    contentDescription = null, modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isFollowing) "Following" else "Follow", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }

            // TOMBOL EDIT BARU
            OutlinedButton(
                onClick = { showEditDialog = true },
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit", modifier = Modifier.size(18.dp), tint = textColor)
                Spacer(Modifier.width(8.dp))
                Text("Edit Profil", color = textColor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Card Informasi Kontak
        ProfileCard(title = "Informasi Kontak", containerColor = surfaceColor) {
            InfoItem(Icons.Filled.Email, "Email", uiState.email, AppColors.IconEmail, textColor)
            InfoItem(Icons.Filled.Phone, "Telepon", uiState.phone, AppColors.IconPhone, textColor)
            InfoItem(Icons.Filled.LocationOn, "Lokasi", uiState.location, AppColors.IconLocation, textColor)
            InfoItem(Icons.Filled.Language, "Website / GitHub", uiState.website, AppColors.IconWebsite, textColor)
        }

        // 5. Card Keahlian
        ProfileCard(title = "Keahlian", containerColor = surfaceColor) {
            skills.forEach { skill ->
                InfoItem(skill.icon, skill.label, skill.value, AppColors.IconSkill, textColor)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}