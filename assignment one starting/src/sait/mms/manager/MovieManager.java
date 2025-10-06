package sait.mms.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import sait.mms.problemdomain.Movie;

public class MovieManager {
    private static final String FILE_PATH = "res/movies.txt";

    private final ArrayList<Movie> movies = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public MovieManager() {
        loadMovieList();
        displayMenu();
    }

    public void loadMovieList() {
        movies.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    int duration = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    int year = Integer.parseInt(parts[2].trim());
                    movies.add(new Movie(duration, title, year));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading movie list: " + e.getMessage());
        }
    }

    public void displayMenu() {
        int option;
        do {
            System.out.println("\nMovie Management system");
            System.out.println("1 Add New Movie and Save");
            System.out.println("2 Generate List of Movies Released in a Year");
            System.out.println("3 Generate List of Random Movies");
            System.out.println("4 Exit");
            System.out.print("\nEnter an option: ");

            try {
                option = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                option = -1;
            }

            switch (option) {
                case 1:
                    addMovie();
                    break;
                case 2:
                    generateMovieListInYear();
                    break;
                case 3:
                    generateRandomMovieList();
                    break;
                case 4:
                    saveMovieListToFile();
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        } while (option != 4);
    }

    private void addMovie() {
        try {
            System.out.print("Enter duration: ");
            int duration = Integer.parseInt(scanner.nextLine().trim());
            if (duration <= 0) {
                System.out.println("Duration must be positive.");
                return;
            }

            System.out.print("Enter movie title: ");
            String title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("Title cannot be empty.");
                return;
            }

            System.out.print("Enter year: ");
            int year = Integer.parseInt(scanner.nextLine().trim());
            if (year <= 0) {
                System.out.println("Year must be positive.");
                return;
            }

            movies.add(new Movie(duration, title, year));
            saveMovieListToFile(); // add-and-save behavior
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values where required.");
        }
    }

    private void generateMovieListInYear() {
        try {
            System.out.print("Enter in year: ");
            int year = Integer.parseInt(scanner.nextLine().trim());
            int total = 0;

            System.out.println("\nMovie List");
            System.out.println("Duration\tYear\tTitle");

            for (Movie m : movies) {
                if (m.getYear() == year) {
                    System.out.println(m);
                    total += m.getDuration();
                }
            }
            System.out.println("\nTotal duration: " + total + " minutes");
        } catch (NumberFormatException e) {
            System.out.println("Invalid year input.");
        }
    }

    private void generateRandomMovieList() {
        try {
            System.out.print("Enter number of movies: ");
            int count = Integer.parseInt(scanner.nextLine().trim());
            if (count <= 0 || count > movies.size()) {
                System.out.println("Number must be between 1 and " + movies.size());
                return;
            }

            List<Movie> shuffled = new ArrayList<Movie>(movies);
            Collections.shuffle(shuffled);

            int total = 0;

            System.out.println("\nMovie List");
            System.out.println("Duration\tYear\tTitle");

            for (int i = 0; i < count; i++) {
                Movie m = shuffled.get(i);
                System.out.println(m);
                total += m.getDuration();
            }
            System.out.println("\nTotal duration: " + total + " minutes");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number input.");
        }
    }

    private void saveMovieListToFile() {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(FILE_PATH)))) {
            for (Movie m : movies) {
                pw.println(m.getDuration() + "," + m.getTitle() + "," + m.getYear());
            }
            System.out.println("Saving movies...");
            System.out.println("Added movie to the data file.");
        } catch (IOException e) {
            System.out.println("Error saving movie list: " + e.getMessage());
        }
    }
}

