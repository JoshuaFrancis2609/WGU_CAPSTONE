# Vacation Planner

## Overview

Vacation Planner is an Android application developed for WGU D424 Software Engineering Capstone project.
The application allows users to organize vacations and excursions in a single location. Users can create vacations,
manage excursions, schedule notifications, share vacation information, and search for a vacation and generate a vacation report.

The application was developed using Java, Android Studio, XML, Room Persistence Library, and SQLite.

---

## Features

* Create, edit, and delete vacations
* Create, edit, and delete excursions
* Vacation and excursion notifications
* Vacation and excursion sharing
* Vacation report generation
* Search functionality with multiple results
* Input validation
* Local database storage using Room and SQLite
* Sample data generation

---

## System Requirements

* Android 8.0 (API Level 26) or higher
* Android device or Android emulator
* Android Studio (for development)

### Tested Configuration

* Pixel 6 Emulator
* Android API Level 36

---

# Installing the Application

1. Download the APK from the Releases section of this repository.
2. Transfer the APK to an Android device if necessary.
3. Enable installation from unknown sources if prompted.
4. Open the APK file.
5. Select Install.
6. Launch the Vacation Planner application.

---

# Using the Application

## Home Screen

When the application launches, the Home Screen is displayed.

* Select **Enter** to access the Vacation List screen.

---

## Vacation List

The Vacation List screen displays all vacations currently stored in the application.

Users can:

* View existing vacations.
* Select a vacation to view details.
* Create a new vacation using the Floating Action Button.
* Add sample data from the menu.
* Generate reports from the menu.

---

## Vacation Management

Users can:

* Add vacations.
* Edit vacations.
* Delete vacations.
* Share vacation information.
* Schedule vacation notifications.

Vacation dates are validated to ensure:

* Dates use the MM/dd/yyyy format.
* Vacation end dates occur after vacation start dates.

Vacations containing excursions cannot be deleted until associated excursions have been removed.

---

## Excursion Management

Users can:

* Add excursions.
* Edit excursions.
* Delete excursions.
* Share excursion information.
* Schedule excursion notifications.

Excursion dates are validated to ensure:

* Dates use the MM/dd/yyyy format.
* Excursion dates occur during the associated vacation dates.

---

## Report Generator

The Report Generator allows users to search for vacations and display associated vacation and excursion information.

Features include:

* Search by vacation title.
* Search by hotel name.
* Multiple row results.
* Multiple column reports.
* Report date and time stamp.
* Validation messages for invalid searches.

Search requirements:

* Search terms must contain at least three characters.
* Searches with no matching results display informational messages.

---

## Sample Data

Sample vacations and excursions can be added from the Vacation List menu. The sample data is intended 
for demonstration, testing, and evaluation purposes.

---

# Development Environment

This application was developed using:

* Android Studio
* Java
* XML
* Room Persistence Library
* SQLite
* RecyclerView
* JUnit
* Git

---

# Setting Up the Project

1. Clone the repository.
2. Open the project in Android Studio.
3. Allow Gradle to synchronize.
4. Install any required SDK components if prompted.
5. Select Build → Make Project.
6. Run the application on an emulator or Android device.

---

# Project Structure

* **UI Package** – Activities and user interface screens.
* **Entities Package** – Vacation and Excursion data models.
* **Database Package** – Room database, DAO interfaces, and repository classes.
* **Receivers Package** – Notification receivers and alarm functionality.
* **Adapters Package** – RecyclerView adapters.

---

# Building the APK

1. Select **Build**.
2. Select **Build Bundle(s) / APK(s)**.
3. Select **Build APK(s)**.
4. Install the generated APK on an Android device or emulator.

---

# Maintenance

Future developers should:

* Modify UI changes within XML layouts and activity classes.
* Update entities, DAOs, and repository methods for database changes.
* Update unit tests when validation logic changes.
* Test all modified functionality.
* Commit changes with descriptive Git messages.

---

# Repository Information

Repository:

https://github.com/JoshuaFrancis2609/WGU_CAPSTONE

Branch:

working_branch

Version:

Vacation Planner v4.0


---
