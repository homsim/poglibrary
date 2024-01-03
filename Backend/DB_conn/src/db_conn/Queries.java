package db_conn;

import java.sql.*;

// put here all the SQL queries needed

public class Queries {
    // maybe make it an abstract base class and make Books inherit from it

    // somehow make them less accessible later on
    // ########################
    static final String DB_URL = "jdbc:mariadb://localhost:3306/testdb";
    static final String DB_USER = "exampleuser";
    static final String DB_PW = "password";
    // ########################

    private static void exec_query() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(""); // execute some query
        ) {
            // do something with the query, i.e.
            // check the result set for validity
            // or return some result
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public class BookQueries {
        private static Book find_book(Isbn isbn) {
            OpenLibraryAPI new_book = new OpenLibraryAPI(isbn);
            Book ret_book = new Book(new_book.title,
                    new_book.author, new_book.isbn_13);

            return ret_book;
        }

        public static void add_book(Book book) {
        }
    }

}
