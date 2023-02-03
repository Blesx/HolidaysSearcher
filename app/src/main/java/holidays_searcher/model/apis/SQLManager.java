package holidays_searcher.model.apis;

import holidays_searcher.model.Holiday;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLManager {
    private final String dbName = "dates.db";
    private final String dbURL = "jdbc:sqlite:" + dbName;

    public SQLManager() {
        buildDatabase();

    }

    /**
     * Builds the database if one does not exist already
     */
    public void buildDatabase() {
        // create the database
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            System.out.println("Database already created");
            return;
        }

        try (Connection ignored = DriverManager.getConnection(dbURL)) {
            // If we get here that means no exception raised from getConnection - meaning it worked
            System.out.println("A new database has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        // setup the database
        String createDatesTableSQL =
                """
                CREATE TABLE IF NOT EXISTS dates (
                id INTEGER PRIMARY KEY,
                name text,
                country text NOT NULL,
                location text,
                type text,
                date text NOT NULL,
                date_year text,
                date_month text,
                date_day text,
                isHoliday BOOLEAN
                )
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            statement.execute(createDatesTableSQL);
            System.out.println("Created tables");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

    }

    /**
     * Removes all entries from the database
     */
    public void resetDatabase() {
        String deleteEntriesSQL =
                """
                DELETE FROM dates
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            statement.execute(deleteEntriesSQL);
            System.out.println("deleted entries");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);

        }

    }

    /**
     * Records all the holidays (from an api call) into the database
     */
    public void cacheHolidays(List<Holiday> holidays) {
        String addHolidaySQL =
                """
                INSERT INTO dates(name, country, location, type, date, date_year, date_month, date_day, isHoliday) VALUES
                (?, ?, ?, ?, ?, ?, ?, ?, TRUE)
                """;

        for (Holiday holiday : holidays) {
            try (Connection conn = DriverManager.getConnection(dbURL);
                 PreparedStatement preparedStatement =
                         conn.prepareStatement(addHolidaySQL)) {

                preparedStatement.setString(1, holiday.getName());
                preparedStatement.setString(2, holiday.getCountry());
                preparedStatement.setString(3, holiday.getLocation());
                preparedStatement.setString(4, holiday.getType());
                preparedStatement.setString(5, holiday.getDate());
                preparedStatement.setString(6, holiday.getDate_year());
                preparedStatement.setString(7, holiday.getDate_month());
                preparedStatement.setString(8, holiday.getDate_day());
                int i = preparedStatement.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.exit(-1);

            }

        }

    }

    /**
     * Records the date without a holiday (from an api call) into the database
     */
    public void cacheDate(String date, String countryAbv) {
        String addDateSQL =
                """
                INSERT INTO dates(country, date, isHoliday) VALUES
                (?, ?, FALSE)
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                     conn.prepareStatement(addDateSQL)) {

            preparedStatement.setString(1, countryAbv);
            preparedStatement.setString(2, date);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);

        }

    }

    /**
     * Checks a date exists in the database
     */
    public boolean dateInDatabase(String date, String countryAbv) {
        String checkDateCheckedSQL =
                """
                SELECT * from dates
                WHERE date = ?
                AND country = ?   
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                conn.prepareStatement(checkDateCheckedSQL)) {
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, countryAbv);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next() == false) {
                return false;

            }
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);

        }

        return false;

    }

    /**
     * Checks a date has a holiday
     */
    public boolean dateHasHoliday(String date, String countryAbv) {
        String checkDateCheckedSQL =
                """
                SELECT * from dates
                WHERE date = ?
                AND country = ?
                AND isHoliday = TRUE
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                     conn.prepareStatement(checkDateCheckedSQL)) {
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, countryAbv);
            ResultSet results = preparedStatement.executeQuery();

            if (!results.next()) {
                return false;

            }
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);

        }

        return false;

    }

    /**
     * Gets all the holidays from a single date and country
     */
    public List<Holiday> getHolidaysOnDay(String date, String countryAbv) {
        String checkDateCheckedSQL =
                """
                SELECT * from dates
                WHERE date = ?
                AND country = ?
                AND isHoliday = TRUE
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                     conn.prepareStatement(checkDateCheckedSQL)) {
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, countryAbv);
            ResultSet results = preparedStatement.executeQuery();
            return convertResultsToHoliday(results);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);

        }

        return null;

    }

    /**
     * Changes results from an SQL call into a list of holidays
     */
    public List<Holiday> convertResultsToHoliday(ResultSet results) {
        List<Holiday> holidays = new ArrayList<>();

        try {
            while (results.next()) {
                holidays.add(new Holiday(
                        results.getString("name"),
                        "",
                        "",
                        "",
                        results.getString("country"),
                        results.getString("location"),
                        results.getString("type"),
                        results.getString("date"),
                        results.getString("date_year"),
                        results.getString("date_month"),
                        results.getString("date_day"),
                        ""
                ));
            }
            return holidays;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);

        }

        return null;

    }

    /**
     * Gets all the holidays from the currently selected year, month and country
     */
    public List<Holiday> getHolidaysInMonth(String year, String month, String countryAbv) {
        String checkDateCheckedSQL =
                """
                SELECT * from dates
                WHERE date_year = ?
                AND date_month = ?
                AND country = ?
                AND isHoliday = TRUE
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                     conn.prepareStatement(checkDateCheckedSQL)) {
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, month);
            preparedStatement.setString(3, countryAbv);
            ResultSet results = preparedStatement.executeQuery();
            return convertResultsToHoliday(results);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);

        }

        return null;

    }

    /**
     * Removes an entry from the database (to update the cache with a fresh api call)
     */
    public void deleteEntry(String date, String countryAbv) {
        String deleteDateSQL =
                """
                DELETE FROM dates
                WHERE date = ?
                AND country = ?
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement =
                     conn.prepareStatement(deleteDateSQL)) {
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, countryAbv);
            preparedStatement.executeUpdate();
            System.out.println("deleted date");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);

        }

    }

}
