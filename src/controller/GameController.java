package controller;

import model.Game;
import dao.GameDAO;

import java.sql.SQLException;
import java.time.Year;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameController {

    private static final Set<String> VALID_PLATFORMS =
            new HashSet<>(Arrays.asList("PC", "Xbox", "PlayStation", "Nintendo"));

    private final GameDAO gameDAO;

    public GameController(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public void validateGame(Game game) throws SQLException {
        if (game.getTitle() == null || game.getTitle().trim().length() < 2 || game.getTitle().trim().length() > 255)
            throw new IllegalArgumentException("O título deve ter entre 2 e 255 caracteres.");
        if (game.getGenre() == null || game.getGenre().trim().isEmpty())
            throw new IllegalArgumentException("Informe o gênero do jogo.");
        int currentYear = Year.now().getValue();
        if (game.getRealeseYear() < 1952 || game.getRealeseYear() > currentYear)
            throw new IllegalArgumentException("Ano de lançamento deve estar entre 1952 e " + currentYear + ".");
        if (game.getPlatform() == null || !VALID_PLATFORMS.contains(game.getPlatform()))
            throw new IllegalArgumentException("Plataforma inválida. Válidas: " + VALID_PLATFORMS);
        if (game.getStatus() == null || game.getStatus().trim().isEmpty())
            throw new IllegalArgumentException("Informe o status do jogo.");
        if (game.getRating() < 0 || game.getRating() > 10)
            throw new IllegalArgumentException("Rating deve estar entre 0 e 10.");

        // Verificação de duplicidade somente para novos (sem ID)
        if (game.getId() == null && gameDAO.validateExist(game.getTitle(), game.getPlatform()))
            throw new IllegalArgumentException("Já existe jogo com este título nesta plataforma.");
    }
}