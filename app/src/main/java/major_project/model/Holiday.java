package major_project.model;

public class Holiday {
    private final String name;
    private final String name_local;
    private final String language;
    private final String description;
    private final String country;
    private final String location;
    private final String type;
    private final String date;
    private final String date_year;
    private final String date_month;
    private final String date_day;
    private final String week_day;

    public Holiday(String name, String name_local, String language, String description, String country, String location, String type, String date, String date_year, String date_month, String date_day, String week_day) {
        this.name = name;
        this.name_local = name_local;
        this.language = language;
        this.description = description;
        this.country = country;
        this.location = location;
        this.type = type;
        this.date = date;
        this.date_year = date_year;
        this.date_month = date_month;
        this.date_day = date_day;
        this.week_day = week_day;

    }

    public String getName() {
        return name;

    }

    public String getName_local() {
        return name_local;

    }

    public String getLanguage() {
        return language;

    }

    public String getDescription() {
        return description;

    }

    public String getCountry() {
        return country;

    }

    public String getLocation() {
        return location;

    }

    public String getType() {
        return type;

    }

    public String getDate() {
        return date;

    }

    public String getDate_year() {
        return date_year;

    }

    public String getDate_month() {
        return date_month;

    }

    public String getDate_day() {
        return date_day;

    }

    public String getWeek_day() {
        return week_day;

    }

}
