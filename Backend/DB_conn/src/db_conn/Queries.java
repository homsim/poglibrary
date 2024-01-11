package db_conn;

import java.sql.*;

public final class Queries {
    // utility class for all the needed SQL queries
    /*
     * Some best practices for JDBC queries:
     * - Using PreparedStatement objects instead if Statement
     * - For repeated requests, use Statement.addBatch() and .executeBatch()
     * -> Disable auto-commit mode
     */

    private static final String DB_URL = "jdbc:mariadb://localhost:3306/testdb";
    private static final String DB_USER = "exampleuser";
    private static final String DB_PW = "password";

    private Queries() {
        // private constructor to prevent instantiation
    }

    public static void addBook(Book book) throws SQLException {
        /*
         * -- add a new book.
         * -- if it already exists, add 1 to book quantity
         * INSERT INTO `Books` (isbn, author, title, quantity, borrowed)
         * VALUES ("9783423247863", "Sapkowski, Andrzej ", "Der Schwalbenturm", 1,
         * FALSE)
         * ON DUPLICATE KEY UPDATE quantity=quantity+1
         * ;
         * 
         */
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);

        String stmt;
        StringBuilder stmt_bld = new StringBuilder();
        stmt_bld.append("INSERT INTO `Books` (isbn, author, title, quantity, borrowed) ");
        stmt_bld.append("VALUES  (?, ?, ?, 1, FALSE)");
        stmt_bld.append("ON DUPLICATE KEY UPDATE quantity=quantity+1 ");
        stmt_bld.append(";");
        stmt = stmt_bld.toString();

        PreparedStatement prepStmt = conn.prepareStatement(stmt);
        prepStmt.setString(1, book.getIsbn().getIsbn());
        prepStmt.setString(2, book.getAuthor().getFormal());
        prepStmt.setString(3, book.getTitle());

        prepStmt.executeQuery();

    }


    public static void addBorrower(Person borrower, Book book) throws SQLException {
        /*
         * -- add new borrower if not already existing and
         * -- fill him in a as borrower of a book
         * SET @add_firstname="Max",
         * 
         * @add_lastname="Musterfrau",
         * 
         * @to_isbn="9783453317161"
         * ;
         * INSERT IGNORE INTO `Borrowers` (firstname, lastname)
         * VALUES (@add_firstname, @add_lastname)
         * ;
         * SELECT borrower_id FROM `Borrowers`
         * WHERE BINARY firstname=BINARY @add_firstname AND BINARY
         * lastname=BINARY @add_lastname
         * INTO @borrower_id
         * ;
         * UPDATE `Books`
         * SET borrower_id = @borrower_id, borrowed = TRUE
         * WHERE BINARY isbn=BINARY @to_isbn
         * ;
         * 
         * 
         * -- I dont really need the SQL variables (@<...>) with the prepared statement
         * -- this only makes me vulnerable for errors later on
         */

        Connection conn = DriverManager.getConnection(DB_URL + "?allowMultiQueries=true", DB_USER, DB_PW);

        String stmt;
        StringBuilder stmt_bld = new StringBuilder();
        stmt_bld.append("INSERT IGNORE INTO `Borrowers` (firstname, lastname) ");
        stmt_bld.append("VALUES (?, ?) ");
        stmt_bld.append("; ");
        stmt_bld.append("SELECT borrower_id FROM `Borrowers`  ");
        stmt_bld.append("WHERE BINARY firstname=BINARY ? AND BINARY lastname=BINARY ? ");
        stmt_bld.append("INTO @borrower_id ");
        stmt_bld.append("; ");
        stmt_bld.append("UPDATE `Books` ");
        stmt_bld.append("SET borrower_id = @borrower_id, borrowed = TRUE ");
        stmt_bld.append("WHERE BINARY isbn=BINARY ? ");
        stmt_bld.append("; ");
        stmt = stmt_bld.toString();

        PreparedStatement prepStmt = conn.prepareStatement(stmt);
        prepStmt.setString(1, borrower.getFirst());
        prepStmt.setString(2, borrower.getLast());
        prepStmt.setString(3, borrower.getFirst());
        prepStmt.setString(4, borrower.getLast());
        prepStmt.setString(5, book.getIsbn().getIsbn());

        prepStmt.executeQuery();

        /*
         * Analyse the result for errors !!!
         */
    }

    public static void decrQuant(Book book) throws SQLException {
        /*
         * -- decrease book quantity by 1
         * UPDATE `Books`
         * SET quantity=quantity-1
         * WHERE isbn="9783453317161"
         * ;
         * 
         */
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);

        String stmt;
        StringBuilder stmt_bld = new StringBuilder();
        stmt_bld.append("UPDATE `Books` ");
        stmt_bld.append("SET quantity=quantity-1 ");
        stmt_bld.append("WHERE isbn=? ");
        stmt_bld.append(";");
        stmt = stmt_bld.toString();

        PreparedStatement prepStmt = conn.prepareStatement(stmt);
        prepStmt.setString(1, book.getIsbn().getIsbn());

        prepStmt.executeQuery();

        /*
         * Analyse the result for errors !!!
         */
    }

    public static void incrQuant(Book book) throws SQLException {
        /*
         * -- increase book quantity by 1
         * UPDATE `Books`
         * SET quantity=quantity+1
         * WHERE isbn="9783453317161"
         * ;
         * 
         */
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);

        String stmt;
        StringBuilder stmt_bld = new StringBuilder();
        stmt_bld.append("UPDATE `Books` ");
        stmt_bld.append("SET quantity=quantity+1 ");
        stmt_bld.append("WHERE isbn=? ");
        stmt_bld.append(";");
        stmt = stmt_bld.toString();

        PreparedStatement prepStmt = conn.prepareStatement(stmt);
        prepStmt.setString(1, book.getIsbn().getIsbn());

        prepStmt.executeQuery();

        /*
         * Analyse the result for errors !!!
         */
    }

}