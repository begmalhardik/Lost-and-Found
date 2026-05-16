# Lost & Found App

---

## Overview
The **Lost & Found App** is an Android application developed using **Kotlin** and **Jetpack Compose**, designed to help users report lost or found items and reconnect them with their rightful owners.

The application now includes **Geo Features and Google Maps integration**, allowing users to select locations using autocomplete search or current device location. Users can also view all lost/found items directly on a map with a **radius-based search filter**.

---

## Features

### Post Lost/Found Items
- Add item name and description
- Select category (Electronics, Pets, Wallets, Keys, etc.)
- Upload an image of the item
- Automatically store date & time of posting

### Search & Filter
- Filter items based on categories
- Easily browse relevant listings

### Remove Listings
- Users can remove posts once the item is returned to its owner

### Image Upload
- Upload and preview images for better identification

### Timestamp
- Each listing includes date & time to indicate recency

---

## Geo Features & Maps

### Location Selection
Users can add location in two ways:
- Select a location using autocomplete search
- Use **GET CURRENT LOCATION** to automatically fetch device location

### Google Maps Integration
- All lost/found items are displayed on Google Maps
- Markers are dynamically loaded from Room Database
- Each marker contains item title and location details

### Radius-Based Search
- Users can filter items within a selected radius
- Nearby items are shown based on the user's current location
- Radius can be adjusted dynamically using a slider

---

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM (Model-View-ViewModel)
- **Database:** SQLite (Room Database)
- **Maps:** Google Maps Compose
- **Location Services:** Fused Location Provider
- **Image Loading:** Coil

---

## Key Functionalities

- Scrollable UI with fixed action button
- Category selection using dropdown menu
- Image picker integration
- Google Maps integration
- Current location detection
- Radius-based filtering for nearby items
- Dynamic map markers from Room Database
- Efficient data handling with Room DB

---

## Future Improvements

- Real Google Places API autocomplete integration
- AI-based image recognition for auto-categorization
- Real-time notifications for nearby matching items
- User authentication and profile verification
- In-app chat between users
- Cloud integration using Firebase
- Advanced map clustering for large datasets

---

## Screenshots

<img src="https://github.com/user-attachments/assets/d2b4a851-6726-4b46-98a7-354b94e869b4" width="300" height="auto">
<img src="https://github.com/user-attachments/assets/07729b02-df21-4801-81b9-b9a6b6faf33b" width="300" height="auto">
<img src="https://github.com/user-attachments/assets/5c4e46e4-3146-4428-a7a8-1739b518700d" width="300" height="auto">
<img src="https://github.com/user-attachments/assets/4ca19b2a-053f-4ad7-b10d-672a90908deb" width="300" height="auto">
<img src="https://github.com/user-attachments/assets/26338308-630a-44c4-9499-0e48ccb54e11" width="300" height="auto">
<img src="https://github.com/user-attachments/assets/ab23c13b-2028-494c-b6cd-36c8b14cfd16" width="300" height="auto">
<img src="https://github.com/user-attachments/assets/dc20ba9c-4430-4f62-b86e-f574fbde9f86" width="300" height="auto">
<img src="https://github.com/user-attachments/assets/82a0a608-138c-4a46-9824-f6b9a9fad81f" width="300" height="auto">