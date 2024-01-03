package db_conn;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONObject;
import org.json.JSONTokener;

// I could instead make all the methods of this class methods of the Book class

public class OpenLibraryAPI {
    /*
     * ToDo:
     * - generally, I need to add functionality to handle books with mulitple
     * authors
     * - (get covers, that can be added in the DB:
     * https://covers.openlibrary.org/b/isbn/<SOME_ISBN>-M.jpg)
     */

    String title;
    Person author;
    Isbn isbn_13;

    public OpenLibraryAPI(Isbn isbn) {
        // get the book entry via isbn
        JSONObject object = jsonAccess("isbn/" + isbn);
        this.title = object.getString("title");
        this.isbn_13 = new Isbn(object.getJSONArray("isbn_13").getString(0), false);

        // get author's name... unfortunately, this requires a second API request in openlibrary
        this.author = new Person(
                jsonAccess(object.getJSONArray("authors").getJSONObject(0).getString("key")).getString("name"));
    }

    private static JSONObject jsonAccess(String key) {
        try {
            URI uri = new URI("https://openlibrary.org/" + key + ".json");
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream inStream = connection.getInputStream();
            JSONTokener tokener = new JSONTokener(inStream);
            JSONObject object = new JSONObject(tokener);
            return object;
        } catch (IOException ex) {
            ex.printStackTrace();
            return new JSONObject();
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
            return new JSONObject();
        }
    }
}
