package db;

import dao.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Cria tabela se não existir. Ajuste tipos/tamanhos conforme necessidade.
 */
public class DBInitializer {
    public static void ensureCreated() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS game (" +
                "id INT NOT NULL AUTO_INCREMENT," +
                "title VARCHAR(255) NOT NULL," +
                "genre VARCHAR(100) NOT NULL," +
                "platform VARCHAR(50) NOT NULL," +
                "realeseYear INT NOT NULL," +          /* Mantido nome 'realeseYear' (com erro) para compatibilidade */
                "status VARCHAR(100) NOT NULL," +
                "rating INT NOT NULL," +
                "PRIMARY KEY (id)," +
                "UNIQUE KEY uk_title_platform (title, platform)" +
                ")";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement()) {
            st.execute(sql);
        }
    }
}