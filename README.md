<h1>HolidaysSearcher</h1>

Lookup holidays dates for any country using the <a href = "https://www.abstractapi.com/api/holidays-api">Abstract 
Holidays API</a>! Current build is v2.2.1.

![HolidaysSearcher Screenshot](https://user-images.githubusercontent.com/48426647/216824885-6eaef4f1-add3-4559-84ed-da5d9831ac98.png)

<h3>Run Program Instructions</h3>

At the moment users require an <a href = "https://www.abstractapi.com/api/holidays-api">Abstract API</a> and 
<a href = "https://www.twilio.com/">Twilio</a> account to run the application.

1. Configure some environmental variables:
   - Abstract API
     - **VARIABLE NAME**: INPUT_API_KEY, **VARIABLE VALUE**: paste your <a href="https://app.abstractapi.com/api/holidays/tester"> primary key</a> here
   - Twilio API: all these values can be found on the <a href = "https://console.twilio.com/">Twilio console</a> after logging in.
     - **VARIABLE NAME**: TWILIO_API_FROM, **VARIABLE VALUE**: your free issued Twilio phone number
     - **VARIABLE NAME**: TWILIO_API_KEY, **VARIABLE VALUE**: your secret auth token
     - **VARIABLE NAME**: TWILIO_API_SID, **VARIABLE VALUE**: your account SID
     - **VARIABLE NAME**: TWILIO_API_TO, **VARIABLE VALUE**: the phone number you want to send messages to
2. Run in 2 ways:
   1. Run using gradle
      - gradle run --args="online online"
      - gradle run --args="online offline"
      - gradle run --args="offline online"
      - gradle run --args="offline offline"
   2. Navigate to the _HolidaysSearcher\out\artifacts\major_project_jar directory and run startAppScript.sh. It loads the application with online capabilities

<h3>Features</h3>

- REST API integration
  - <a href = "https://www.abstractapi.com/api/holidays-api">Abstract Holidays API</a>
  - <a href = "https://www.twilio.com/">Twilio</a>
- <a href = "https://www.sqlite.org/index.html">SQLite</a> cache integration
- Model-view (MV) design
- TDD practices
- Extras
  - Theme music
  - Word matcher
  - Help function

<h3>Current Version - v2.2.1</h3>

- Added new menu
- Moved word matcher and music function to menu

<h3>Upcoming Features - v2.3.0</h3>

1. Ability to set AbstractHolidays and Twilio account information in the app!
2. Updated UI!
