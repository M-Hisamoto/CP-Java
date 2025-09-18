package dao;

import model.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {

    public void registerNewGame(Game game) throws SQLException{
        String sql = "INSERT INTO Game (title, genre, platform, realeseYear, status, rating) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, game.getTitle());
            ps.setString(2, game.getGenre());
            ps.setString(3, game.getPlatform());
            ps.setInt(4, game.getRealeseYear());
            ps.setString(5, game.getStatus());
            ps.setInt(6, game.getRating());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) game.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Não foi possível cadastrar o jogo.");
        }
    }

    public Game searchById(int id) throws SQLException {
        String sql = "SELECT id, title, genre, platform, realeseYear, status, rating FROM game WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return map(rs);
            }
        }
        return null;
    }

    public List<Game> searchByTitle(String title) throws SQLException {
        String sql = "SELECT id, title, genre, platform, realeseYear, status, rating FROM game WHERE title = ?";
        List<Game> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public boolean validateExist (String title, String platform) throws SQLException {
        String sql = "SELECT title, platform FROM game WHERE title = ? AND platform = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, platform);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return true;
            }
        }
        return false;
    }

    public List<Game> listByTitle() throws SQLException {
        String sql = "SELECT id, title, genre, platform, realeseYear, status, rating FROM game ORDER BY title";
        List<Game> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<Game> listById() throws SQLException {
        String sql = "SELECT id, title, genre, platform, realeseYear, status, rating FROM game ORDER BY id";
        List<Game> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<Game> listByGenre() throws SQLException {
        String sql = "SELECT id, title, genre, platform, realeseYear, status, rating FROM game ORDER BY genre";
        List<Game> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<Game> listByPlatform() throws SQLException {
        String sql = "SELECT id, title, genre, platform, realeseYear, status, rating FROM game ORDER BY platform";
        List<Game> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<Game> listByStatus() throws SQLException {
        String sql = "SELECT id, title, genre, platform, realeseYear, status, rating FROM game ORDER BY status";
        List<Game> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public void updateGame(Game game) throws SQLException {
        String sql = "UPDATE game SET title=?, genre=?, platform=?, realeseYear=?, status=?, rating=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, game.getTitle());
            ps.setString(2, game.getGenre());
            ps.setString(3, game.getPlatform());
            ps.setInt(4, game.getRealeseYear());
            ps.setString(5, game.getStatus());
            ps.setInt(6, game.getId());
            ps.executeUpdate();
        }
    }

    public void deleteGame(int id) throws SQLException {
        String sql = "DELETE FROM game WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Game map(ResultSet rs) throws SQLException {
        return new Game(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getString("platfrom"),
                rs.getInt("realeseYear"),
                rs.getString("status"),
                rs.getInt("rating")
        );
    }

}