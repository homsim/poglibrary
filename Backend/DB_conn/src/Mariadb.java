import java.sql.*;

public class Mariadb {
    static final String DB_URL = "jdbc:mariadb://localhost:3306/testdb";
    static final String USER = "exampleuser";
    static final String PASS = "password";
    static final String QUERY = "SELECT * FROM Books";

    public static void main(String[] args) {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                System.out.print("ISBN: " + rs.getString("isbn"));
                System.out.print(", Author: " + rs.getString("author"));
                System.out.println(", Title: " + rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
