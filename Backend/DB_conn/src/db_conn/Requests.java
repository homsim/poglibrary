package db_conn;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * A utility class (non-instantiable) which provides the methods that are
 * callable from the frontend.
 * <p>
 * All request string have to be of the format
 * <code>&#60;&#34;r&#34;/&#34;w&#34;&#62;&#38;&#60;methodName&#62;&#38;&#60;Arg1&#62;;&#60;Arg2&#62;;...</code>.
 * The units for the request type, method name and arguments are separated by
 * <code>&#38;</code>. Arguments for the callable method
 * <code>&#60;methodName&#62;</code> are separated by <code>;</code>. Generally, they are interpreted as String. However, some methods need arrays as method arguments. 
 * The array elements can be separated using <code>|</code>.
 */
public class Requests {
    private Requests() {
    }

    /**
     * For testing purposes, echo an incomming message.
     * 
     * @param mess
     * @return
     */
    public static String echo(String mess) {
        System.out.println(mess);
        return mess;
    }

    /**
     * Receive the `Books` table from the database.
     * The <code>columns</code> member determines which columns of the Books table
     * are returned.
     * Within the String, the column names are supposed to be comma-separated in the
     * order in which they should be displayed.
     * The following column names are allowed
     * <ul>
     * <li>isbn</li>
     * <li>author</li>
     * <li>title</li>
     * <li>quantity</li>
     * <li>borrowed</li>
     * <li>borrowed_by</li>
     * </ul>
     * 
     * @param columns formatted string of column names
     * @return json of books' informations
     * @throws SQLException if something went wrong in the execution of the SQL
     *                      query
     */
    public static String recBooks(String columns) throws SQLException {
        return Queries.recBooks(columns);
    }

    /**
     * Add a book to the library from a provided ISBN. Further book informations
     * will by automatically added.
     * 
     * @param isbn the isbn of the book to be added
     * @return status code '0' if the execution was successful
     * @throws SQLException if something went wrong in the execution of the SQL
     *                      query
     */
    public static int addBookFromIsbn(String isbn) throws SQLException, IOException, URISyntaxException, FaultyIsbnException {
        OpenLibraryAPI olapi = new OpenLibraryAPI(new Isbn(isbn));
        Book book = olapi.createBook();
        Queries.addBook(book);

        return 0;
    }

    /**
     * Add a book to the library by providing all informations manually.
     * 
     * @param title  title of the book to be added
     * @param authorsStr authors of the book to be added, separated by char '|'
     * @param isbn   isbn (isbn-10 or isbn-13) of the book to be added
     * @return status code '0' if the execution was successful
     * @throws SQLException if something went wrong in the execution of the SQL
     *                      query
     */
    public static int addBookManually(String title, String authorsStr, String isbn) throws SQLException, FaultyIsbnException {
        String[] authorsStrArray = Interpreter.interpetArrays(authorsStr);
        Person[] authors = new Person[authorsStrArray.length]; 
        for (int i = 0; i < authorsStrArray.length; i++) {
            authors[i] = new Person(authorsStrArray[i]);
        }
        Book book = new Book(
                title, authors, new Isbn(isbn));
        Queries.addBook(book);
        return 0;
    }

    /**
     * Edit a book entry.
     * 
     * @return status code '0' if the execution was successful
     * @throws SQLException if something went wrong in the execution of the SQL
     *                      query
     */
    public static int editBook() throws SQLException {
        // not yet implemented
        return 0;
    }

}
