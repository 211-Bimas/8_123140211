# Tugas Praktikum Minggu 8 - Platform APIs (expect/actual) & Dependency Injection (Koin)

* **Nama : Muhammad Bimastiar**
* **NIM : 123140211**

## Deskripsi Tugas
Mengembangkan proyek aplikasi dari minggu sebelumnya dengan melakukan *upgrade* arsitektur standar industri menggunakan **Dependency Injection (Koin)** dan berinteraksi langsung dengan *hardware* serta sistem operasi perangkat keras menggunakan **Platform APIs (pola `expect`/`actual`)**. Berikut adalah fitur dan ketentuan yang diimplementasikan pada praktikum ini:

1. **Implementasi Dependency Injection (Koin):**
    - Mengganti inisialisasi manual (seperti pembuatan *Database*, *Repository*, dan *ViewModel* di `App.kt`) menggunakan pola *Dependency Injection* terpusat pada file `AppModule.kt`.
    - Kode UI menjadi jauh lebih bersih dengan memanggil *dependency* menggunakan `koinInject()`.
2. **Platform APIs (Pola `expect` / `actual`):**
    - Menerapkan jembatan antara *Common Code* (Kotlin Multiplatform) dan *Native Android Code* untuk mengakses spesifikasi perangkat keras.
    - **DeviceInfo:** Membaca dan menampilkan nama model perangkat fisik (misal: "Samsung SM-A525F") serta versi sistem operasi.
3. **Network Monitor & Android Permissions:**
    - Memantau status jaringan keras (Wi-Fi/Data Seluler) secara *real-time*. 
    - Jika pengguna mematikan koneksi internet, aplikasi akan memunculkan *Banner Merah (Offline Mode)*.
    - Menambahkan izin `ACCESS_NETWORK_STATE` pada `AndroidManifest.xml` agar aplikasi tidak *Force Close* saat menyadap status koneksi.
4. **🌟 BONUS FITUR (+10%) - Battery Info:**
    - Menambahkan fitur Info Baterai menggunakan pola `expect/actual` untuk membaca status `BatteryManager` bawaan OS Android.
    - Menampilkan persentase sisa baterai perangkat secara *real-time* di halaman Profile.

## Struktur Folder
Proyek ini mengadopsi pemisahan *layer* yang terstruktur. Terdapat penambahan folder `di` (Dependency Injection) dan file spesifik platform (`commonMain` vs `androidMain`):

```text
composeApp/src/
├── commonMain/kotlin/org/example/project/
│   ├── di/
│   │   └── AppModule.kt             # Gudang (Module) Koin untuk mendaftarkan semua dependency
│   ├── BatteryInfo.kt               # Kontrak 'expect' untuk Info Baterai (Bonus)
│   ├── DeviceInfo.kt                # Kontrak 'expect' untuk Info Perangkat
│   ├── NetworkMonitor.kt            # Kontrak 'expect' untuk Monitor Jaringan
│   ├── ui/
│   │   ├── NotesScreens.kt          # UI Catatan (Ditambah Banner Merah Offline)
│   │   └── ProfileScreen.kt         # UI Profil (Ditambah Info HP & Baterai)
│   └── ... (komponen ViewModel, Data, dll seperti tugas 7)
│
└── androidMain/kotlin/org/example/project/
    ├── BatteryInfo.android.kt       # Implementasi 'actual' baca baterai di Android
    ├── DeviceInfo.android.kt        # Implementasi 'actual' baca spesifikasi HP Android
    └── NetworkMonitor.android.kt    # Implementasi 'actual' cek Wi-Fi/Data Android
```

## Cara Menjalankan Aplikasi (Langkah-langkah)

Proyek ini menggunakan basis **Jetpack Compose Multiplatform**. Berikut panduannya:

1.  **Persiapan IDE:** Gunakan **Android Studio (Ladybug / versi terbaru)**.
2.  **Buka & Build Proyek:** Buka folder proyek dan tunggu proses sinkronisasi Gradle selesai.
3.  **Jalankan Aplikasi (PENTING):** - Sangat disarankan untuk menjalankan aplikasi pada **Perangkat Fisik Asli (HP Android via USB Debugging)**, bukan Emulator. Hal ini agar data `DeviceInfo` (Model HP) dan `BatteryInfo` (Persentase Baterai) terbaca secara nyata.
    - Tekan `Shift + F10` atau klik tombol hijau **Run**.
4.  **Uji Coba Fitur Baru:** - Buka halaman/tab **Profile**, *scroll* ke paling bawah untuk melihat *Card* "Informasi Perangkat" (Model HP, OS, dan Persentase Baterai).
    - Buka halaman/tab **Catatan Ku**, lalu tarik *Quick Settings* (menu atas) di HP Anda dan **matikan Wi-Fi / Data Seluler**. Banner merah peringatan *Offline Mode* akan langsung muncul secara *real-time*.

## Hasil

### 1. Tampilan Notes & Network Monitor (Banner Offline)
*(Tampilan daftar catatan saat online dan saat koneksi internet dimatikan)*



### 2. Tampilan Profile & Device/Battery Info
*(Halaman profil yang menampilkan data spesifikasi fisik perangkat dan status persentase baterai)*



### 3. Tampilan Tambah/Edit/Detail & Favorit
*(Fitur CRUD persisten dengan SQLDelight dari Tugas 7 tetap berjalan normal)*



### 4. Video Demo
*(Tautan/File Video Demo Maksimal 45 Detik yang menampilkan pengujian Jaringan dan Baterai)*

**Tonton Video Demo:** [Link Video Kamu Di Sini]