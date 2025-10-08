import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/sistema_analise?useSSL=false&serverTimezone=UTC",
                "root",
                ""
            );
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro de conexão: " + e.getMessage());
        }
        return conn;
    }
}