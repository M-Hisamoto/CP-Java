package db;

import dao.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    public static void ensureCreated() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS game (" +
                "`id` INT NOT NULL AUTO_INCREMENT," +
                "`title` VARCHAR(45) NOT NULL," +
                "`genre` VARCHAR(45) NOT NULL," +
                "`platform` VARCHAR(45) NOT NULL,"+
                "`realeseYear` INT NOT NULL," +
                "`status` VARCHAR(45) NOT NULL,"+
                "PRIMARY KEY (`id`)"+
                ")";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement()) {
            st.execute(sql);
        }
    }
}
