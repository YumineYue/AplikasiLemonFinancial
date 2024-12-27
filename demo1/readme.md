# LemonFinancial App

**LemonFinancial** adalah aplikasi manajemen keuangan pribadi yang memungkinkan pengguna untuk melacak, mengelola, dan merencanakan keuangan mereka secara efisien. Aplikasi ini memiliki tampilan dashboard interaktif yang menampilkan ringkasan keuangan, fitur transaksi untuk mencatat pemasukan dan pengeluaran, laporan keuangan yang mendetail, serta fitur target keuangan untuk membantu pengguna mencapai tujuan finansial mereka. Selain itu, aplikasi ini dilengkapi dengan kemampuan untuk mengekspor data ke dalam format PDF, Excel, atau CSV untuk kemudahan analisis dan pelaporan.

## Fitur Utama

### 1. Dashboard Keuangan
- Menyediakan tampilan ringkasan keuangan pengguna, termasuk total pendapatan, pengeluaran, dan saldo.
- Grafik visual untuk menunjukkan distribusi pengeluaran dan pemasukan.
- Ringkasan statistik penting, seperti pengeluaran terbesar, saldo bulan ini, dan lain-lain.

### 2. Transaksi
- Pengguna dapat menambah transaksi pemasukan (pendapatan) atau pengeluaran (biaya) dengan informasi tanggal, jumlah, kategori, dan keterangan.
- Daftar transaksi yang telah dimasukkan ditampilkan dalam bentuk tabel, dan pengguna dapat memfilter transaksi berdasarkan tanggal atau kategori.

### 3. Laporan Keuangan
- Menyediakan laporan detail mengenai pengeluaran dan pendapatan untuk periode tertentu.
- Laporan dapat dilihat dalam bentuk grafik maupun tabel, memberikan gambaran jelas tentang aliran keuangan pengguna.
- Dapat dihasilkan untuk jangka waktu harian, mingguan, bulanan, atau tahunan.

### 4. Target Keuangan
- Pengguna dapat menetapkan target keuangan (misalnya menabung sejumlah uang dalam periode tertentu).
- Aplikasi akan memantau progres pencapaian target dan memberi tahu pengguna tentang status target tersebut.
- Target bisa berupa tabungan, investasi, atau pengeluaran untuk kategori tertentu.

### 5. Ekspor Data
- Pengguna dapat mengekspor data transaksi dan laporan keuangan ke dalam format **PDF**, **Excel (XLSX)**, atau **CSV** untuk tujuan analisis lebih lanjut atau pencetakan.
- Fungsi ekspor memungkinkan pengguna untuk menyimpan data secara lokal atau berbagi dengan pihak lain (misalnya, akuntan atau partner bisnis).

## Teknologi yang Digunakan
- **JavaFX**: Untuk antarmuka pengguna grafis (GUI).
- **Apache POI**: Digunakan untuk mengekspor data ke dalam format Excel (XLSX).
- **iText**: Digunakan untuk membuat file PDF.
- **Java**: Bahasa pemrograman utama untuk logika aplikasi dan pengolahan data.
- **CSV File Handling**: Menggunakan pustaka Java untuk menangani ekspor data ke dalam format CSV.

## Cara Menggunakan

1. **Masuk ke Aplikasi**
    - Pengguna dapat login menggunakan akun yang telah dibuat sebelumnya.

2. **Tambahkan Transaksi**
    - Pilih jenis transaksi (pendapatan atau pengeluaran) dan masukkan data yang relevan, kemudian simpan transaksi tersebut.

3. **Pantau Dashboard**
    - Dashboard akan memperbarui data secara otomatis untuk memberi gambaran keuangan terkini.

4. **Laporan Keuangan**
    - Gunakan fitur laporan untuk melihat dan menganalisis laporan keuangan berdasarkan periode waktu tertentu.

5. **Tetapkan Target Keuangan**
    - Atur target keuangan dan pantau progres pencapaian target tersebut.

6. **Ekspor Data**
    - Pilih laporan atau transaksi yang ingin diekspor dan pilih format file (PDF, Excel, CSV).

## Instalasi

1. Clone atau download repository ini.
2. Pastikan Java Development Kit (JDK) sudah terpasang di komputer Anda.
3. Pastikan Anda memiliki dependensi yang diperlukan untuk **JavaFX**, **Apache POI**, dan **iText** dalam proyek Anda.
4. Kompilasi dan jalankan aplikasi menggunakan IDE atau command line.

## Kontribusi
Jika Anda tertarik untuk berkontribusi pada pengembangan aplikasi ini, silakan buka **Issues** untuk melaporkan bug atau usulan fitur. Anda juga dapat membuat 
