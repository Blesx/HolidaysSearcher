<h1>Holidays/Twilio API Implementation</h1>

Test commit: check the committer is Blesx and not Christian Mo

<h3>Submission Info</h3>

Input API: Holidays

Output API: Twilio

Claimed Tier: Credit

Credit Optional Feature 1: Help feature

Credit Optional Feature 2: Theme song

Distinction Optional Feature: N/A

High Distinction Optional Feature: N/A

Milestone 1 Submission:

SHA: c54eae0437ff51df60faf418412939cccd656315

URI: https://github.sydney.edu.au/chmo6791/SCD2_2022/tree/c54eae0437ff51df60faf418412939cccd656315

Milestone 1 Re-Submission (didn't have to resubmit):

SHA: c54eae0437ff51df60faf418412939cccd656315

URI: https://github.sydney.edu.au/chmo6791/SCD2_2022/tree/c54eae0437ff51df60faf418412939cccd656315

Milestone 2 Submission:

SHA: 705ba5e53612c3745da54a691b92fdd1d9d033b1

URI: https://github.sydney.edu.au/chmo6791/SCD2_2022/tree/705ba5e53612c3745da54a691b92fdd1d9d033b1

Milestone 2 Re-Submission:

SHA: 81204a910a98dbe8d751e5a5b39f589c443db130 

URI: https://github.sydney.edu.au/chmo6791/SCD2_2022/tree/81204a910a98dbe8d751e5a5b39f589c443db130

Exam Base Commit:

SHA: 81204a910a98dbe8d751e5a5b39f589c443db130

URI: https://github.sydney.edu.au/chmo6791/SCD2_2022/tree/81204a910a98dbe8d751e5a5b39f589c443db130

Exam Submission Commit:

SHA: a040586073833c3aefd6d45ec7e179c97d206544

URI: https://github.sydney.edu.au/chmo6791/SCD2_2022/tree/a040586073833c3aefd6d45ec7e179c97d206544

<h3>Run Program Instructions</h3>

- Use gradle run with the proper arguments. Currently accepted commands with arguments are:
  - gradle run --args="online online"
  - gradle run --args="online offline"
  - gradle run --args="offline online"
  - gradle run --args="offline offline"
- Twilio phone numbers: no '+' in env variables, just numbers
- Help function: press 'CTRL' + click on any selectable element (button, cell etc.) to get more information about it

<h3>Current features</h3>

- Input API
- Output API
- Dummy input & output
- MV design
- Test suite
- SQL/cache
- 'Help' feature
- Music feature

<h3>Present Level of Features</h3>

- Hurdle
- Pass
- Credit

<h3>Citations - Not Written</h3>

- line 66: InputCalendarOnline.java
  - usage: to parse json arrays with no name
  - source: https://stackoverflow.com/questions/13736122/using-gson-to-parse-json-array-and-object-with-no-name
- lines 141-155: CalendarWindow.java
  - usage: set row and column width/height for cells
  - source: https://stackoverflow.com/questions/57927816/how-to-make-all-cells-in-gridpane-visible-and-same-size
- lines 40-42: OutputCalendarOnline.java
  - usage: set authorization header for Twilio POST request
  - source: https://docs.agora.io/en/All/faq/restful_authentication
- line 166: CalendarWindow.java; line: 70 WorldMapWindow.java
  - usage: check if mediaplayer is playing song
  - source: https://stackoverflow.com/questions/18340125/how-to-tell-if-mediaplayer-is-playing
- resource: /src/main/resources/raindrops.mp3
  - usage: music for theme song
  - source: https://www.youtube.com/watch?v=sySlY1XKlhM

<h3>Citations - Written</h3>

- lines 50-61: InputCalendarOnline()
  - usage: similar structure for managing http request and response from HelloHTTP example & task2
  - source: HelloHTTP.java module 5, all models for task2