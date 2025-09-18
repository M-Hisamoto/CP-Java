package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Fornece uma nova conexão a cada chamada.
 * NÃO mantém conexão estática para evitar reutilização de conexão fechada.
 */
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao obter conexão com o banco: " + e.getMessage(), e);
        }
    }
}