package db_conn;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * A utility class (non-instantiable) which provides the methods that are callable from the frontend. 
 * <p>
 * All request string have to be of the format <code>&#60;&#34;r&#34;/&#34;w&#34;&#62;&#38;&#60;methodName&#62;&#38;&#60;Arg1&#62;;&#60;Arg2&#62;;...</code>.
 * The units for the request type, method name and arguments are separated by <code>&#38;</code>. Arguments for the callable method <code>&#60;methodName&#62;</code> are separated by ;.
 */
public class Requests {
    private Requests() {
    }
    
    /**
     * Add a book to the library from a provided ISBN. Further book informations
     * will by automatically added.
     * @param   isbn    the isbn of the book to be added
     * @return          status code '0' if the execution was successful
     * @throws  SQLException    if something went wrong in the execution of the SQL query
     */
    public static int addBookFromIsbn(String isbn) throws SQLException, IOException, URISyntaxException {
        OpenLibraryAPI olapi = new OpenLibraryAPI(new Isbn(isbn));
        Book book = olapi.createBook();
        Queries.addBook(book);

        return 0;
    }

    /**
     * Add a book to the library by providing all informations manually.
     * @param   title   title of the book to be added
     * @param   author  author of the book to be added
     * @param   isbn    isbn (isbn-10 or isbn-13) of the book to be added
     * @return          status code '0' if the execution was successful
     * @throws  SQLException    if something went wrong in the execution of the SQL query
     */
    public static int addBookManually(String title, String author, String isbn) throws SQLException {
        // not yet implemented
        return 0;
    }

    /**
     * Edit a book entry.
     * 
     * @return          status code '0' if the execution was successful
     * @throws  SQLException    if something went wrong in the execution of the SQL query
     */
    public static int editBook() throws SQLException {
        // not yet implemented
        return 0;
    }
}
