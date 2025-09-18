package model;

public class Game {
    private Integer id;
    private String title;
    private String genre;
    private String platform;
    private int realeseYear;
    private String status;
    private int rating;

    public Game(Integer id, String title, String genre, String platform, int realeseYear, String status, int rating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.realeseYear = realeseYear;
        this.status = status;
        this.rating = rating;
    }

    public Game(String title, String genre, String platform, int realeseYear, String status, int rating) {
        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.realeseYear = realeseYear;
        this.status = status;
        this.rating = rating;
    }

    public Game() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public int getRealeseYear() { return realeseYear; }
    public void setRealeseYear(int releaseDate) { this.realeseYear = releaseDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
}