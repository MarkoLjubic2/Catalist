# Catalist

Catalist is a small Android application that provides detailed information about various cat breeds.

## Table of Contents
- [Screens](#screens)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Backend API](#backend-api)
- [Setup](#setup)

## Screens

### Breeds List Screen
- Displays a list of cat breeds.

### Breeds Details Screen
- Shows detailed information about the selected breed.

## Technology Stack

- **Jetpack Compose**: Used for building the user interface.
- **Kotlin Coroutines**: For asynchronous programming.
- **Retrofit and OkHttp**: For handling web service requests.
- **Kotlinx Serialization**: For data serialization.

## Architecture

The app follows the MVI (Model-View-Intent) architecture to ensure a clear separation of concerns and to provide a scalable structure for managing UI states and actions.

## Backend API

Catalist uses TheCatApi.com service to fetch data and photos about cat breeds. An API key is required to access the service, which is provided via email immediately after registration.

## Setup

To set up the project locally, follow these steps:

1. **Clone the repository**
  
2. **Open the project in Android Studio**.

3. **Build the project to install the necessary dependencies.**

4. **Run the app on an emulator or a physical device.**
