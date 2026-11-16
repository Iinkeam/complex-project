public class Movie {
    private String title;
    private String director;
    private String genre;
    private int year;
    private double rating;

    public Movie(String title, String director, String genre, int year, double rating) {
        setTitle(title);
        setDirector(director);
        setGenre(genre);
        setYear(year);
        setRating(rating);
    }

    public String getName() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
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
        this.director = director.trim();
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be empty.");
        }
        this.genre = genre.trim();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1888 || year > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Invalid year.");
        }
        this.year = year;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be 0-10.");
        }
        this.rating = rating;
    }

    // TODO: String getTitle()        -> one line, e.g. "Inception (2010)"
    // TODO: String getDescription()  -> multi-line using "\n"
    // TODO: one extra method, e.g. getAge()
}