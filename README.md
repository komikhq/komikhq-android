# KomikHQ Android App

Modern, fast, and buttery-smooth Manga, Manhwa, and Manhua reader application for Android built with Jetpack Compose, Material 3, and Kotlin.

## Features (In Development)
- **Webtoon & Manga Reader Engine**: Gapless vertical continuous scroll & horizontal paged mode with pinch-to-zoom.
- **Offline Download Manager**: Background chapter downloads with WorkManager.
- **Sync & Library**: Seamless reading history and bookmark synchronization powered by KomikHQ Better Auth session.
- **AMOLED Dark Mode**: Battery-saving deep black theme.
- **Modular & Modern**: Consumes official `komikhq-android-sdk` (`com.komikhq:komikhq-android-sdk`).

## Architecture & Tech Stack
- **UI Framework**: Jetpack Compose + Material 3
- **Language**: Kotlin
- **Networking**: `komikhq-android-sdk` (Retrofit2 + OkHttp + kotlinx.serialization)
- **Image Pipeline**: Coil 3
- **Local Storage**: Room & Preferences DataStore
- **Async**: Coroutines & Flow

## License
Proprietary / KomikHQ Team
