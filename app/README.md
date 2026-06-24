# D308 Vacation Planner

## Purpose of the Application

The purpose of this application is to help users organize vacations and excursions in one place. 
Users can create vacations, add excursions associated with vacations, set notifications for vacation and excursion
dates, and share vacation or excursion details using Android sharing features.

This app was created using Java compatible with Android 8.0 and higher, Android Studio, and uses Room Framework 
for the local database management.

---

# How to Use the Application

## Home Screen

When the application launches, the user is brought to the Home Screen.

* Select the **Enter** button to open the Vacation List screen.

---

## Vacation List Screen

The Vacation List screen displays all vacations currently stored in the application.

#### Features

* View all vacations in a RecyclerView list.
* Select an existing vacation to open Vacation Details.
* Select the Floating Action Button (FAB) to create a new vacation.
* Open the menu in the upper-right corner to add sample data.

#### Adding Sample Data

1. Open the menu in the upper-right corner.
2. Select **Add Sample Data**.
3. The RecyclerView refreshes and displays sample vacations and excursions.

---

### Adding, Editing, and Deleting Vacations

1. Select the Floating Action Button (FAB) from the Vacation List screen.
2. Enter the vacation title, hotel name, start date, and end date.
3. Select the menu in the upper-right corner.
4. Select **Save Vacation** to create the vacation.
5. To edit a vacation, select the vacation from the RecyclerView, update the information, and save again.
6. To delete a vacation, open the vacation and select **Delete Vacation** from the menu.
7. Vacations with associated excursions cannot be deleted until the excursions are removed.

#### Vacation Validation

* Dates must use the format:

  * MM/dd/yyyy
* Vacation end dates cannot occur before the vacation start date.

---

## Vacation Details Screen

The Vacation Details screen displays detailed vacation information and all associated excursions.

#### Features

* View vacation title, hotel, start date, and end date.
* View all excursions associated with the vacation.
* Select the Floating Action Button (FAB) to add excursions.
* Select an excursion from the RecyclerView to open Excursion Details.

#### Vacation Menu Options

* Save Vacation
* Delete Vacation
* Share Vacation
* Vacation Notifications

#### Vacation Notifications

Users can create notifications for:

* Vacation start dates
* Vacation end dates

#### Sharing Vacation Information

* Vacation details can be shared using Android sharing features such as messaging or email applications.

---

### Adding, Editing, and Deleting Excursions

1. Open a vacation from the Vacation List screen.
2. Select the Floating Action Button (FAB) to create an excursion.
3. Enter the excursion title and excursion date.
4. Open the menu and select **Save Excursion**.
5. To edit an excursion, select it from the RecyclerView and update the information.
6. To delete an excursion, open the menu and select **Delete Excursion**.

#### Excursion Validation

* Dates must use the format:

  * MM/dd/yyyy
* Excursion dates must occur during the associated vacation dates.

---

## Excursion Details Screen

The Excursion Details screen displays excursion information and allows the user to manage excursions.

#### Features

* View or update excursion title and excursion date.
* Share excursion details.
* Set excursion notifications.

#### Excursion Menu Options

* Save Excursion
* Delete Excursion
* Share Excursion
* Excursion Notifications

#### Excursion Notifications

* Users can create notifications for excursion dates using Android notifications.

---

# Rubric Aspects Covered

### Application Layout and Navigation

* The application includes:

  * A Home Screen
  * A Vacation List screen
  * A Vacation Details screen
  * An Excursion Details screen
* Navigation between screens is handled using Android Intents, RecyclerViews, Floating Action Buttons (FABs), and Action Bar menus.

---

### Vacation Management Features

* Users can:

  * Add vacations
  * Update vacations
  * Delete vacations
  * View detailed vacation information
* Each vacation includes:

  * Vacation title
  * Hotel name
  * Vacation start date
  * Vacation end date

---

### Excursion Management Features

* Users can:

  * Add excursions
  * Update excursions
  * Delete excursions
  * View detailed excursion information
* Excursions are associated with individual vacations and displayed within the selected vacation details screen.

---

### RecyclerView and Database Features

* RecyclerViews are used to display:

  * Vacation lists
  * Excursion lists
* The application uses the Room persistence library for local database storage and management.

---

### Date Validation Features

* Vacation dates are validated to ensure:

  * Dates use the MM/dd/yyyy format
  * Vacation end dates occur after vacation start dates
* Excursion dates are validated to ensure:

  * Dates use the MM/dd/yyyy format
  * Excursion dates occur within the associated vacation dates

---

### Notification Features

* Users can create notifications for:

  * Vacation start dates
  * Vacation end dates
  * Excursion dates
* Notifications are created using Android AlarmManager, BroadcastReceivers, and NotificationChannels.

---

### Sharing Features

* Vacation details can be shared using Android sharing features.
* Excursion details can also be shared through Android sharing options such as messaging or email applications.

---

### Sample Data Features

* Sample vacation and excursion data can be added through the Vacation List menu for testing and demonstration purposes.

---

# Android Version Compatibility

* Minimum SDK: Android 8.0 (Oreo) - API Level 26
* Target SDK: Android 16 - API Level 36 
* Tested On: Pixel 6 Emulator - API Level 36

---

# Git Repository

[GitHub Repository](https://gitlab.com/wgu-gitlab-environment/student-repos/jfr1002/d308-mobile-application-development-android.git)
