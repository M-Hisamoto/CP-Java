import dao.GameDAO;
import model.Game;
import controller.GameController;


import java.sql.SQLException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        GameDAO gameDAO = new GameDAO();

        GameController validate = new GameController(gameDAO);

        Game g = new Game();
        g.setTitle("qsf");
        g.setGenre("fight");
        g.setPlatform("PC");
        g.setRealeseYear(2026);
        g.setStatus("Playing");
        validate.validateGame(g);

        new GameDAO().registerNewGame(g);

    }
}
