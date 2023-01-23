<h1>Holidays/Twilio API Implementation</h1>

Lookup holidays dates for any country using the <a href ="https://app.abstractapi.com/api/holidays/tester">Abstract 
Holidays API</a>. It provides a simple user interface and some fun features like word matching and sending an SMS of
holiday dates to your phone!

This project was inherited from a university project, the previous repository can be found here (only available via 
a private DM): https://github.sydney.edu.au/chmo6791/SCD2_2022/tree/a040586073833c3aefd6d45ec7e179c97d206544

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