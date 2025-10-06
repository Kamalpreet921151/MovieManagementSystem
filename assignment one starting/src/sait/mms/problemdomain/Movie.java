package sait.mms.problemdomain;

public class Movie {
	private int duration;
    private String title;
    private int year;

    // Constructor
    public Movie(int duration, String title, int year) {
        this.duration = duration;
        this.title = title;
        this.year = year;
    }

    // Getters
    public int getDuration() { return duration; }
    public String getTitle() { return title; }
    public int getYear() { return year; }

    
    public String toString() {
        // Format columns to match lists
        return String.format("%d\t\t%d\t%s", duration, year, title);
    }
}
