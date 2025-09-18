package dao;

import model.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {

    public void registerNewGame(Game game) throws SQLException {
        String sql = "INSERT INTO game (title, genre, platform, realeseYear, status, rating) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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
        }
    }

    public Game searchById(int id) throws SQLException {
        String sql = "SELECT id, title, genre, platform, realeseYear, status, rating FROM game WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public List<Game> searchByTitle(String title) throws SQLException {
        String sql = "SELECT id, title, genre, platform, realeseYear, status, rating FROM game WHERE title LIKE ?";
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

    public boolean validateExist(String title, String platform) throws SQLException {
        String sql = "SELECT 1 FROM game WHERE title = ? AND platform = ? LIMIT 1";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, platform);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<Game> listByTitle() throws SQLException {
        return listGeneric("ORDER BY title");
    }

    public List<Game> listById() throws SQLException {
        return listGeneric("ORDER BY id");
    }

    public List<Game> listByGenre() throws SQLException {
        return listGeneric("ORDER BY genre");
    }

    public List<Game> listByPlatform() throws SQLException {
        return listGeneric("ORDER BY platform");
    }

    public List<Game> listByStatus() throws SQLException {
        return listGeneric("ORDER BY status");
    }

    private List<Game> listGeneric(String order) throws SQLException {
        String sql = "SELECT id, title, genre, platform, realeseYear, status, rating FROM game " + order;
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
            ps.setInt(6, game.getRating());
            ps.setInt(7, game.getId());
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
                rs.getString("platform"),
                rs.getInt("realeseYear"),
                rs.getString("status"),
                rs.getInt("rating")
        );
    }
}