# 📊 Portfolio Holdings App

### **Objective**

An Android application that displays a list of stock holdings with **expandable and collapsible UI states** to show portfolio summaries.
The app fetches data from a provided API and calculates key metrics like **Current Value**, **Total Investment**, **Total P&L**, and **Today’s P&L**.

---
<img src = "https://github.com/user-attachments/assets/18605050-600b-40c9-bb62-595ef926d963" width = "200" height = "400">
<img src = "https://github.com/user-attachments/assets/84481e5e-f967-4081-80e1-0f55d3b4d616" width = "200" height = "400">
<img src = "https://github.com/user-attachments/assets/b10b50f5-a49e-4cc9-b0b3-40818eb4912e" width = "200" height = "400">


## 🚀 Features

* 📡 Fetches live holdings data from the given endpoint.
* 💹 Dynamically calculates portfolio performance values.
* 🧩 Expand/Collapse UI for portfolio summary.
* ⚠️ Handles **no internet**, **API errors**, and **empty states** gracefully.
* 🎨 Built entirely using **Jetpack Compose** with **Material 3 guidelines**.
* 🧱 Follows **Clean Architecture** principles for scalability and testability.
* 🧩 Modularized by feature (`:feature-holdings`) for future growth.
* ⚙️ Uses **MVVM architecture** and **Unidirectional Data Flow (UDF)**.
* 🧪 Unit tests and coverage reports integrated.

---

## 🏗️ Architecture Overview

### **Clean + Modular Architecture**

Each feature in the app is built as a separate module to improve **maintainability, scalability, and build performance**.

**Layered Responsibility:**

| Layer            | Description                                               |
| ---------------- | --------------------------------------------------------- |
| **Presentation** | Jetpack Compose UI, ViewModels, and state management.     |
| **Domain**       | Business logic, use-cases, and entity models.             |
| **Data**         | API integration, repository implementations, and mappers. |

---

## 🧱 Module Structure

### 🧩 **App Module (`:app`)**

* Entry point of the application.
* Manages navigation and dependency graph setup.
* Depends on feature modules like `:feature-holdings` and shared modules like `:core-ui`.

### 🧩 **Feature Module (`:feature-holdings`)**

Contains the complete implementation of the **Portfolio Holdings feature**.

```
feature-holdings/
├── data/            # API services, repositories, DTOs, mappers
├── domain/          # Entities, business logic, and use-cases
├── presentation/    # Compose screens, UI states, and ViewModels
└── di/              # Hilt module for dependency injection
```

### 🧩 **Core UI Module (`:core-ui`)**

A shared module that provides reusable resources and utilities across all features:

```
core-ui/
├── theme/           # ColorScheme, Typography, Shapes, Dimens
├── components/      # Reusable UI Composables (e.g., ErrorView, Loader)
├── utils/           # CommonUtils, NetworkChecker, extensions
├── resources/       # Strings, Icons, and shared assets
└── di/              # Core DI (if required)
```

This modular setup ensures:

* Independent feature development
* Reusability of UI and utility components
* Reduced build time for incremental changes

---

## 🧮 Calculations

| Metric               | Formula                          |
| -------------------- | -------------------------------- |
| **Current Value**    | Σ (Last traded price × Quantity) |
| **Total Investment** | Σ (Average price × Quantity)     |
| **Total P&L**        | Current Value − Total Investment |
| **Today’s P&L**      | Σ ((Close − LTP) × Quantity)     |

---

## 🌐 API Endpoint

```
https://35dee773a9ec441ef98d5fc24940c6ee.api.mockbin.io/
```

---

## ⚡ Tech Stack

* **Language:** Kotlin
* **UI:** Jetpack Compose (Material 3)
* **Architecture:** MVVM + Clean Architecture
* **Modularization:** Feature-based (e.g., `:feature-holdings`)
* **Dependency Injection:** Dagger
* **Networking:** Retrofit + OkHttp
* **Coroutines & Flow:** For async and reactive programming
* **Error Handling:** Sealed classes & UI state models
* **Testing:** JUnit, MockK

---

## 🧠 Edge Cases Handled

* No Internet / Network unavailable
* API Timeout or Server Error
* Empty or Invalid Data from API
* Safe UI State handling during configuration changes
* Loading & Error states with smooth transitions

---

## 🧪 Testing

* Unit tests for:

  * UseCases
  * Repository implementations
  * ViewModel logic
* MockK for mocking dependencies
* Coverage reports generated using JaCoCo:

```bash
./gradlew jacocoTestReport
```

Reports available at:

```
feature-holdings/build/reports/jacoco/jacocoTestReport/html/index.html
```

---

## 📂 Overall Project Structure

```
portfolio-holdings/
│
├── app/                        # Application module (entry point)
│   ├── di/                     # App-level Hilt setup
│   └── navigation/             # Feature navigation handling
│
├── core-ui/                    # Shared resources & UI components
│   ├── theme/                  # Colors, Dimens, Typography
│   ├── utils/                  # Common utilities and extensions
│   └── components/             # Shared composables
│
├── feature-holdings/           # Portfolio feature module
│   ├── data/                   # Network & repository layer
│   ├── domain/                 # Business logic layer
│   ├── presentation/           # UI layer (Jetpack Compose)
│   └── di/                     # Hilt bindings for dependencies
│
└── build.gradle
```

---

## 🧭 How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/<your-repo>/portfolio-holdings.git
   ```
2. Open in **Android Studio Ladybug (or newer)**.
3. Run the app on a device/emulator with internet access.

---

## 🧰 Requirements

* **Min SDK:** 21
* **Target SDK:** 35
* **Kotlin:** 2.x
* **Gradle:** 8.x
* **Android Studio:** Ladybug / Iguana+

---

## 🎯 Evaluation Highlights

✅ Clean Architecture + MVVM
✅ Feature-based modularization (`:feature-holdings`)
✅ Shared **core-ui** module for reusability
✅ Proper error and edge case handling
✅ SOLID principles applied
✅ Unit-tested code with coverage
✅ Smooth and responsive UI built with Compose

---

## 💡 Future Improvements

* Add caching layer using Room or DataStore.
* Introduce Offline-first approach.
* Add dynamic theme switching (dark/light).
* Integrate CI/CD pipeline with Codecov coverage reports.

---
