package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    public static void main(String[] args) {
        Connection conexao = null;
        ResultSet rsgames = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // optional for JDBC 4.0+
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "123456");
            Statement stmt = conexao.createStatement();
            rsgames = stmt.executeQuery("select * from games");
            while (rsgames.next()) {
                System.out.println("Nome: " + rsgames.getString("nome"));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver do banco de dados não localizado.");
        } catch (SQLException e) {
            throw new RuntimeException("Ocorreu um erro ao acessar o banco." + e.getMessage());
        } finally {
            try {
                if (rsgames != null) rsgames.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}