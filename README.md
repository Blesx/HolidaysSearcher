<h1>HolidaysSearcher</h1>

Lookup holidays dates for any country using the <a href = "https://www.abstractapi.com/api/holidays-api">Abstract 
Holidays API</a>! Current build is v2.1.0.

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
   2. Navigate to the _HolidaysSearcher\out\artifacts\major_project_jar_ directory and run startAppScript.sh. It loads the application with online capabilities

<h3>Current Features - v2.1.0</h3>

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

<h3>Upcoming Features - v2.2.0</h3>

1. Updated UI!
2. Can choose between online/offline mode an app opening
3. Easier way to access program (concept stage)