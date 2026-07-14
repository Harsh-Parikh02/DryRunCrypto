# DryRunCrypto

An Android application built with Kotlin and Jetpack Compose that fetches live market data from a cryptocurrency API and stores it offline. Follows an MVVM architecture using Retrofit with GSON for network requests and Room Database as the local data cache. Implements Jetpack WorkManager for constrained periodic background updates.

## 📱 App Preview & UI Showcase

<p align="center">
  <img  width="250" alt="SS" src="https://github.com/user-attachments/assets/0a72400a-b5c1-4f12-86e5-7da0e399206b" alt="DryRun Crypto Dashboard Screenshot" />

</p>

### Key Dashboard Features:
- **Unified Modern Interface:** Crafted entirely with Jetpack Compose, featuring a clean, responsive layout designed with rounded cards and elevation for visual depth.
- **Dynamic Color Indicators:** Market percentages and sparkline colors dynamically adapt based on 24-hour price movements—rendering in vibrant green for positive trends and solid red for negative pullbacks.
- **Custom-Drawn Sparklines:** Instead of heavy, bloated charting libraries, historical 7-day price data is parsed directly from the local database and plotted on a lightweight, custom-built Android Canvas component (`TrendChart.kt`).
- **One-Tap Syncing:** Includes a seamless toolbar actions integration allowing users to force-refresh market data on demand with a responsive sync button.

---

## Architecture & Tech Stack
- **UI Layer:** Jetpack Compose for reactive, declarative interface components.
- **Design Pattern:** MVVM (Model-View-ViewModel) with the Repository pattern.
- **Networking:** Retrofit + OkHttp for execution, GSON for parsing JSON API payloads.
- **Local Cache:** Room SQLite Database serving as the offline single source of truth.
- **Background Engine:** Jetpack WorkManager implementing resource-constrained periodic background updates.
