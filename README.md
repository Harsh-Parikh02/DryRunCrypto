# DryRunCrypto

An Android application built with Kotlin and Jetpack Compose that fetches live market data from a cryptocurrency API and stores it offline. Follows an MVVM architecture using Retrofit with GSON for network requests and Room Database as the local data cache. Implements Jetpack WorkManager for constrained periodic background updates.

## Architecture & Tech Stack
- **UI Layer:** Jetpack Compose for reactive, declarative interface components.
- **Design Pattern:** MVVM (Model-View-ViewModel) with the Repository pattern.
- **Networking:** Retrofit + OkHttp for execution, GSON for parsing JSON API payloads.
- **Local Cache:** Room SQLite Database serving as the offline single source of truth.
- **Background Engine:** Jetpack WorkManager implementing resource-constrained periodic background updates.
