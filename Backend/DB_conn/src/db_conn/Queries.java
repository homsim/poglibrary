package db_conn;

import java.sql.*;

import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        StringBuilder stmtBld = new StringBuilder();
        stmtBld.append("INSERT INTO `Books` (isbn, author, title, quantity, borrowed) ");
        stmtBld.append("VALUES  (?, ?, ?, 1, FALSE)");
        stmtBld.append("ON DUPLICATE KEY UPDATE quantity=quantity+1 ");
        stmtBld.append(";");
        stmt = stmtBld.toString();

        PreparedStatement prepStmt = conn.prepareStatement(stmt);
        prepStmt.setString(1, book.getIsbn().getIsbn());
        prepStmt.setString(2, Arrays.toString(book.getAuthors())
                .replace("[", "").replace("]", "")); // this has to be changed
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
        StringBuilder stmtBld = new StringBuilder();
        stmtBld.append("INSERT IGNORE INTO `Borrowers` (firstname, lastname) ");
        stmtBld.append("VALUES (?, ?) ");
        stmtBld.append("; ");
        stmtBld.append("SELECT borrower_id FROM `Borrowers`  ");
        stmtBld.append("WHERE BINARY firstname=BINARY ? AND BINARY lastname=BINARY ? ");
        stmtBld.append("INTO @borrower_id ");
        stmtBld.append("; ");
        stmtBld.append("UPDATE `Books` ");
        stmtBld.append("SET borrower_id = @borrower_id, borrowed = TRUE ");
        stmtBld.append("WHERE BINARY isbn=BINARY ? ");
        stmtBld.append("; ");
        stmt = stmtBld.toString();

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
         * -- decrease book quantity by 1 and delete book if quantity <= 0
         * UPDATE `Books`
         * SET quantity=quantity-1
         * WHERE isbn="9783453317161"
         * ;
         * DELETE FROM `Books` WHERE quantity <= 0
         * ;
         * 
         */
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);

        String stmt;
        StringBuilder stmtBld = new StringBuilder();
        stmtBld.append("UPDATE `Books` ");
        stmtBld.append("SET quantity=quantity-1 ");
        stmtBld.append("WHERE isbn=? ");
        stmtBld.append(";");
        stmtBld.append("DELETE FROM `Books` WHERE quantity <= 0");
        stmtBld.append(";");
        stmt = stmtBld.toString();

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
        StringBuilder stmtBld = new StringBuilder();
        stmtBld.append("UPDATE `Books` ");
        stmtBld.append("SET quantity=quantity+1 ");
        stmtBld.append("WHERE isbn=? ");
        stmtBld.append(";");
        stmt = stmtBld.toString();

        PreparedStatement prepStmt = conn.prepareStatement(stmt);
        prepStmt.setString(1, book.getIsbn().getIsbn());

        prepStmt.executeQuery();

        /*
         * Analyse the result for errors !!!
         */
    }

    public static String recBooks(String columnsStr) throws SQLException {
        /*
         * -- get entries from the Books table. columns are decided by columnStr.
         * SELECT
         * Books.isbn,Books.title,CONCAT(Borrowers.firstname," ",Borrowers.lastname)
         * FROM `Books`
         * LEFT JOIN `Borrowers` ON Books.borrower_id = Borrowers.borrower_id
         * ;
         * 
         */
        final String BORROWER_ALIAS = "borrowed_by";

        StringTokenizer columnTok = new StringTokenizer(columnsStr, ",", false);
        int nColumns = columnTok.countTokens();
        List<String> columns = new ArrayList<String>();
        for (int i = 0; i < nColumns; i++) {
            columns.add(columnTok.nextToken());
        }
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW);

        String stmt;
        StringBuilder stmtBld = new StringBuilder();
        stmtBld.append("SELECT ");
        for (String column : columns) {
            if (column.equals(BORROWER_ALIAS)) {
                // alias for borrowed_by
                stmtBld.append("(CONCAT(Borrowers.firstname,\" \",Borrowers.lastname)) AS borrowed_by ");
            } else {
                stmtBld.append("Books." + column);
            }
            stmtBld.append(",");
        }
        stmtBld.deleteCharAt(stmtBld.length() - 1); // remove last ','
        stmtBld.append(" FROM `Books` ");
        stmtBld.append("LEFT JOIN `Borrowers` ON Books.borrower_id = Borrowers.borrower_id ");
        stmtBld.append(";");
        stmt = stmtBld.toString();

        PreparedStatement prepStmt = conn.prepareStatement(stmt);
        ResultSet result = prepStmt.executeQuery();

        // convert the reuslt to JSON
        JSONArray resultJsonArray = new JSONArray();
        while (result.next()) {
            JSONObject row = new JSONObject();
            columns.forEach(cn -> {
                try {
                    row.put(cn, result.getObject(cn));
                } catch (JSONException | SQLException e) {
                    e.printStackTrace();
                }
            });
            resultJsonArray.put(row);
        }

        return resultJsonArray.toString();
    }

}