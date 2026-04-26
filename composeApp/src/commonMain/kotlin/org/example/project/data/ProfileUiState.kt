package org.example.project.data

// Ini adalah Data Class untuk menyimpan semua State di layar
data class ProfileUiState(
    val name: String = "Muhammad Bimastiar",
    val title: String = "Mahasiswa Teknik Informatika",
    val bio: String = "Saya adalah mahasiswa yang sedang menempuh pendidikan di Institut Teknologi Sumatera (ITERA) pada program studi Informatika. Sebagai mahasiswa, saya aktif mengikuti kegiatan perkuliahan serta mempelajari berbagai bidang ilmu yang berkaitan dengan teknologi dan pengembangan sistem informasi.",
    val email: String = "muhammad.123140211@student.itera.ac.id",
    val phone: String = "+62 812-7846-5250",
    val location: String = "Bandar Lampung, Indonesia",
    val website: String = "github.com/211-Bimas",
    val isDarkMode: Boolean = false // State untuk Dark Mode
)