# Tugas Praktikum Minggu 6 - Akses Jaringan dan Pengambilan Data API (Ktor)

* **Nama : Muhammad Bimastiar**
* **NIM : 123140211**

## Deskripsi Tugas

Mengembangkan proyek aplikasi dari minggu sebelumnya dengan menambahkan fitur untuk mengambil data dari internet (REST API) menggunakan **Ktor HTTP Client** pada Kotlin Multiplatform. Berikut adalah fitur dan ketentuan yang diimplementasikan pada praktikum ini:

1.  **Konfigurasi Ktor & Serialization:**
    - Menambahkan *dependency* Ktor dan mendaftarkan *plugin* `kotlinx.serialization` pada Gradle untuk membaca format JSON.
    - Menambahkan izin akses internet (`android.permission.INTERNET`) pada `AndroidManifest.xml`.
2.  **Pemodelan Data & Parsing JSON:**
    - Membuat *data class* `NewsArticle` yang ditandai dengan anotasi `@Serializable` agar JSON dari API dapat diubah menjadi list objek Kotlin.
3.  **State Management (Loading, Success, Error):**
    - Mengimplementasikan `sealed class NewsUiState` untuk mengelola tiga kondisi layar: saat mengambil data (*Loading*), saat berhasil (*Success*), dan saat gagal/internet terputus (*Error*).
4.  **Repository Pattern:**
    - Membuat `NewsRepository` yang bertugas khusus menembak endpoint API (`https://jsonplaceholder.typicode.com/posts`) dan mengembalikan data *List*.
5.  **Integrasi ViewModel & UI (Tab News):**
    - Menggunakan `viewModelScope.launch` pada `NewsViewModel` untuk mengambil data secara *asynchronous* (Coroutines).
    - Menampilkan data pada `NewsListScreen` menggunakan `LazyColumn` yang diletakkan pada salah satu menu di *Bottom Navigation* (Tab News). Dilengkapi juga dengan tombol *Refresh*.

## Struktur Folder

Proyek ini mempertahankan struktur dari tugas minggu sebelumnya dengan penambahan file khusus untuk menangani *networking* dan data berita. Berikut adalah susunan *package* utamanya:

```text
composeApp/src/commonMain/kotlin/org/example/project/
├── App.kt                 # Entry point & inisialisasi NavHost/BottomBar
├── components/
│   └── BottomNav.kt       # Komponen UI Bottom Navigation (Navigasi antar tab)
├── data/
│   ├── NewsData.kt        # Berisi Model (NewsArticle), State (NewsUiState), & NewsRepository
│   └── ProfileUiState.kt  # (Dari praktikum sebelumnya)
├── navigation/
│   └── Routes.kt          # Definisi Rute layar untuk navigasi
├── ui/
│   ├── NewsScreens.kt     # Layar utama berita (NewsListScreen) dan layar detailnya
│   ├── NotesScreens.kt    # (Dari praktikum sebelumnya)
│   └── ProfileScreen.kt   # (Dari praktikum sebelumnya)
└── viewmodel/
    ├── NewsViewModel.kt   # State holder untuk mengambil data API dan menyimpannya ke UI State
    └── ProfileViewModel.kt# (Dari praktikum sebelumnya)
```

## Cara Menjalankan Aplikasi (Langkah-langkah)

Proyek ini menggunakan basis **Jetpack Compose Multiplatform**. Berikut panduan untuk menjalankannya:

1.  **Persiapan:** Pastikan Anda menggunakan **Android Studio** versi terbaru dan perangkat/emulator **terhubung ke internet**.
2.  **Buka Proyek:** Pilih menu `File > Open...` dan arahkan ke folder proyek ini.
3.  **Tunggu Gradle Sync:** Pastikan *dependency* Ktor dan plugin Serialization sudah terunduh dengan melihat indikator sinkronisasi Gradle (atau klik *Sync Now*).
4.  **Jalankan Aplikasi:** - Untuk **Android**: Pilih emulator atau perangkat fisik Android Anda, lalu klik tombol **Run** (segitiga hijau) atau tekan `Shift + F10`.
5.  **Uji Coba Fitur:** Setelah jendela aplikasi terbuka:
    - Gunakan **Bottom Navigation** untuk masuk ke tab **News** (Berita).
    - Aplikasi akan menampilkan status *Loading* ("Sedang mengambil berita...").
    - Jika sukses, layar akan menampilkan daftar artikel menggunakan `LazyColumn`.
    - Coba matikan internet pada emulator/HP Anda, lalu klik **Icon Refresh** di pojok kanan atas. Layar akan berubah menampilkan pesan *Error* "Gagal Memuat 😭".
    - Klik salah satu *Card* berita untuk masuk ke halaman detail yang menampilkan teks lengkapnya.

## Hasil

### 1\. Tampilan State Success (Daftar Berita)

*(Aplikasi berhasil mengambil data JSON dari API dan memetakannya ke dalam LazyColumn)*



### 2\. Tampilan State Loading

*(Indikator putaran saat fungsi asinkron Ktor sedang menunggu respons dari server)*



### 3\. Tampilan State Error

*(Penanganan error saat aplikasi gagal menerjemahkan data atau saat tidak ada koneksi internet)*



### 4\. Tampilan Detail Berita

*(Navigasi passing argument yang membawa judul dan isi body artikel yang diklik)*



### 5. Video Demo Aplikasi
*(Video demonstrasi berjalannya aplikasi, menampilkan proses pengambilan data dari internet, efek loading, dan interaksi navigasi)*

**Tonton Video Demo:** 