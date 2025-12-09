<div align="center">
  <h1>YoMovie</h1>
  
  <p>
    <strong>A modern, immersive Movie & TV Show discovery app built with Jetpack Compose.</strong>
  </p>

  <p>
    <a href="https://github.com/EngFred/YoMovie/releases/download/v1.1.0/YoMovie_v1.1.0.apk">
      <img src="https://img.shields.io/badge/Download-APK-28a745?style=for-the-badge&logo=android&logoColor=white" alt="Download APK">
    </a>
  </p>

  <p>
    <img src="https://img.shields.io/badge/Kotlin-1.9.0-7f52ff?style=flat&logo=kotlin&logoColor=white" alt="Kotlin" />
    <img src="https://img.shields.io/badge/Jetpack%20Compose-1.5.3-4285F4?style=flat&logo=jetpackcompose&logoColor=white" alt="Jetpack Compose" />
    <img src="https://img.shields.io/badge/Architecture-Clean%20%2B%20MVVM-success?style=flat" alt="Clean Architecture" />
    <img src="https://img.shields.io/badge/API-TMDB-01b4e4?style=flat&logo=themoviedatabase&logoColor=white" alt="TMDB API" />
    <img src="https://img.shields.io/badge/License-MIT-blue?style=flat" alt="License" />
  </p>
</div>

---

## Screenshots

<div align="center">
  <table>
    <tr>
      <td align="center"><img src="https://github.com/user-attachments/assets/b7ccba58-7a4a-4011-94d6-ab953a300919" width="200" alt="Home Screen" /></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/3977f35c-91d6-4206-90dd-b6213a69214e" width="200" alt="Detail Screen" /></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/05bebc62-5f93-4f43-9525-5ed80c4ddca9" width="200" alt="Cast" /></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/bc594b65-b778-4566-b086-8934059cae35" width="200" alt="Reviews" /></td>
    </tr>
    <tr>
      <td align="center"><img src="https://github.com/user-attachments/assets/699b5a56-9d71-49dd-9e50-827d5521e405" width="200" alt="Search" /></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/5060a17b-2f15-4a5d-9b33-ce98b71b353d" width="200" alt="Genres" /></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/1b312357-b827-4ae3-b11c-81d9ec506799" width="200" alt="Dark Mode" /></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/8b933a1f-da16-485b-8787-6a955ba3e275" width="200" alt="Player" /></td>
    </tr>
    <tr>
      <td align="center"><img src="https://github.com/user-attachments/assets/8b734412-e2f2-4845-a5ee-d8704f4bc8be" width="200" alt="TV Shows" /></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/cde2ba23-dad0-4c86-91b4-e4a9ac2b2657" width="200" alt="Grid View" /></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/f9b8d862-b5fc-483a-ac44-25aa9f11e083" width="200" alt="About" /></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/9457e501-3b69-418c-8051-3124ea458ea1" width="200" alt="Splash" /></td>
    </tr>
  </table>
</div>

---

## About

**YoMovie** is a robust Android application designed to provide movie and TV enthusiasts with a seamless discovery experience. Built using modern Android development standards, it leverages **Jetpack Compose** for UI and follows **Clean Architecture** principles to ensure scalability and maintainability.

The app fetches real-time data from **The Movie Database (TMDB)**, allowing users to explore trending content, watch trailers, and filter by genres with support for both Light and Dark themes.

---

## Key Features

- **Comprehensive Library:** Browse Now Playing, Popular, Top Rated movies, and On-Air/Popular TV shows.
- **Smart Search:** Instantly find movies and TV shows by title.
- **Video Player:** Integrated YouTube Player to watch official trailers directly within the app.
- **Genre Filtering:** Discover content based on categories (Action, Comedy, Drama, etc.)
- **Responsive UI:** Smooth, animated, and responsive interface built with Material 3.
- **Offline Support:** Caching mechanism using Room Database for viewing content offline.

---

## 🛠️ Tech Stack & Architecture

This project is built using the **MVVM (Model-View-ViewModel)** pattern combined with **Clean Architecture**.

### Libraries & Tools
* **[Kotlin](https://kotlinlang.org/)** - First-class and official programming language for Android development.
* **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Android’s modern toolkit for building native UI.
* **[Hilt](https://dagger.dev/hilt/)** - Dependency Injection framework.
* **[Retrofit](https://square.github.io/retrofit/)** - Type-safe HTTP client for Android.
* **[Room](https://developer.android.com/training/data-storage/room)** - Persistence library provides an abstraction layer over SQLite.
* **[Coil](https://coil-kt.github.io/coil/)** - An image loading library for Android backed by Kotlin Coroutines.
* **[Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3)** - Loads and displays small chunks of data at a time.
* **[Coroutines](https://github.com/Kotlin/kotlinx.coroutines)** & **[Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/)** - For asynchronous programming.
* **[YouTube Android Player](https://github.com/PierfrancescoSoffritti/android-youtube-player)** - For playing movie trailers.

---

## 📥 Installation

### Option 1: Direct Download (APK)
You can download the latest signed APK directly to your device:

<a href="https://github.com/EngFred/YoMovie/releases/download/v1.1.0/YoMovie_v1.1.0.apk">
  <img src="https://img.shields.io/badge/Download%20Latest%20APK-black?style=for-the-badge&logo=android" alt="Download Button">
</a>

### Option 2: Build from Source
1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/EngFred/YoMovie.git](https://github.com/EngFred/YoMovie.git)
    ```
2.  **Open in Android Studio:**
    Open Android Studio -> File -> Open -> Select the cloned directory.
3.  **Add API Key:**
    Create a `Constants.kt` file or add your TMDB API Key in `gradle.properties` (if configured).
4.  **Run the App:**
    Connect your device/emulator and hit the **Run** button (Shift+F10).

---

## 🤝 Contributing

Contributions are always welcome! Whether it's reporting a bug, suggesting an enhancement, or submitting a pull request.

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

---
