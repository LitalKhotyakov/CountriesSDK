# 🌍 Countries SDK

A library to easily fetch and display a list of countries, allowing users to select a country and retrieve its details, including the country name, flag, phone code, and currency.

<img src="https://raw.githubusercontent.com/LitalKhotyakov/CountriesSDK/master/pic.png" width="288">

## Overview

The **Countries SDK** provides a simple and efficient way to fetch a list of all countries and present them in a list for selection. This SDK is designed to be easy to integrate into your Android project, enabling users to select a country and retrieve its details, including the country name, flag, phone code, and currency.

## Features

- **🌐 Fetch All Countries:** Retrieve a comprehensive list of all countries with relevant details.
- **🗺️ Country Selection:** Allow users to select a country from the list.
- **📋 Country Details:** Access the selected country's name, flag, phone code, and currency.
- **🔔 Listener for Country Selection:** Implement a listener to handle country selection events.

## Installation

To include the Countries SDK in your project, add the following dependency to your build.gradle file:

To use it in your module in your settings.gradle add this line:

```
include(":CountriesSDK")
```
Then in add this in your build.gradle:

```
implementation(project(":CountriesSDK"))

```

To see the countries List add CountriesSDk to your xml file as an xml element:
```
<com.example.countriessdk.CountriesSDk
android:layout_width="match_parent"
android:layout_height="wrap_content" />
```


## Usage

1. Fetching and Displaying the Country List
   To fetch and display the list of countries, call the SDK's method for retrieving the list. 
   The SDK will handle fetching the country data and displaying it in a list format.

2. Handling Country Selection
   To handle country selection and retrieve the selected country's details,
   implement the OnCountrySelectedListener interface and set it using the setOnCountrySelectedListener(OnCountrySelectedListener listener) method.
   The listener will provide the selected country's name, flag, phone code, and currency.



