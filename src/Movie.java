import java.time.Year;

public class Movie {
    private String title;
    private String director;
    private String genre;
    private int year;
    private double rating;

    // Movie constructor
    public Movie(String title, String director, String genre, int year, double rating) {
        setTitle(title);
        setDirector(director);
        setGenre(genre);
        setYear(year);
        setRating(rating);
    }

    // Simple getters & setters
    public String getName() {
        return title;
    }
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (title.contains(";")) {
            throw new IllegalArgumentException("Title cannot contain semicolons (;).");
        }
        this.title = title.trim();
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        if (director == null || director.trim().isEmpty()) {
            throw new IllegalArgumentException("Director cannot be empty.");
        }
        if (director.contains(";")) {
            throw new IllegalArgumentException("Director cannot contain semicolons (;).");
        }
        this.director = director.trim();
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be empty.");
        }
        if (genre.contains(";")) {
            throw new IllegalArgumentException("Genre cannot contain semicolons (;).");
        }
        this.genre = genre.trim();
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        int currentYear = Year.now().getValue();
        if (year < 1888 || year > currentYear) {
            throw new IllegalArgumentException("Year must be between 1888 and " + currentYear + ".");
        }
        this.year = year;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10.");
        }
        this.rating = Math.round(rating * 10.0) / 10.0;
    }
    public int getAge() {
        return Year.now().getValue() - year;
    }
    public String getTitle() {
        return title + " (" + year + ")";
    }

    // properties methods
    public String getDetails() {
        return "| Title: " + title + "\n" +
                "| Director: " + director + "\n" +
                "| Genre: " + genre + "\n" +
                "| Year: " + year + "\n" +
                "| Rating: " + rating + "/10\n" +
                "| Age: " + getAge() + " years\n";
    }
    public String getDescription() {
        return "|======= MOVIE INFO =======|\n" +
                getDetails() +
                "|==========================|";
    }
}