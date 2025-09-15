package controller;

import model.Game;
import dao.GameDAO;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameController {

    private static final Set<String> VALID_PLATFORMS = new HashSet<>(Arrays.asList("PC", "Xbox", "PlayStation", "Nintendo"));
    private final GameDAO gameDAO;

    public GameController(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    /**
     * 1) Validação de regras de negócio
     */

    public void validateGame(Game game) throws SQLException {

        if (game.getTitle() == null || game.getTitle().trim().length() < 2 || game.getTitle().trim().length() > 255)
            throw new IllegalArgumentException("O título do jogo deve ser diferente de um já existente ou ter entre 2 e 255 caracteres.");
        if (game.getGenre() == null)
            throw new IllegalArgumentException("Por favor informe o gênero do jogo");
        if (game.getRealeseYear() < 1952 || game.getRealeseYear() > 2025)
            throw new IllegalArgumentException("O jogo deve ter sido lançado entre 1952 e 2025.");
        if (game.getPlatform() == null || !VALID_PLATFORMS.contains(game.getPlatform()))
            throw new IllegalArgumentException("Plataforma inválida. Plataformas válidas: " + VALID_PLATFORMS);
        if (game.getStatus() == null)
            throw new IllegalArgumentException("Por favor informe o status do jogo");
        if (gameDAO.validateExist(game.getTitle(), game.getPlatform()))
            throw new IllegalArgumentException("Já existe um jogo com este título e nesta plataforma");

    }
}


