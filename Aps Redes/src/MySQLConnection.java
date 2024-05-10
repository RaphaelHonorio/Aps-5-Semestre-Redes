import java.sql.*;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://banco-de-dados-aps-5-semestre-redes.cna0m2kkworc.us-east-2.rds.amazonaws.com:3306/BancoUsuarios";
    // jdbc:mysql://localhost:3306/chat_db
    private static final String USER = "root";
    private static final String PASSWORD = "12341234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}