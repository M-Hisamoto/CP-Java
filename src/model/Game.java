package model;

public class Game {
    private Integer id;
    private String title;
    private String genre;
    private String plataform;
    private int releaseDate;
    private String status;

    public Game(Integer id, String title, String genre, String plataform, int releaseDate, String status) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.plataform = plataform;
        this.releaseDate = releaseDate;
        this.status = status;
    }


}
