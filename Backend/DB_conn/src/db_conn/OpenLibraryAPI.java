package db_conn;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

// Class that gets books informations via ISBN

public class OpenLibraryAPI {
    /*
     * ToDo:
     * - generally, I need to add functionality to handle books with mulitple
     * authors
     * - (get covers, that can be added in the DB:
     * https://covers.openlibrary.org/b/isbn/<SOME_ISBN>-M.jpg)
     */

    String title;
    Person[] authors;
    Isbn isbn_13;

    public OpenLibraryAPI(Isbn isbn) throws IOException, URISyntaxException {
        // get the book entry via isbn
        JSONObject object = getJsonSearch(isbn.toString());
        this.title = getTitleJson(object);
        /*
         * the title still has the problem that there may or may not
         * be a subtitle (see example: 9783423760898). 
         * need to add functionality to get this into this.title, too
         */
        this.authors = getAuthorJson(object);
        this.isbn_13 = getIsbnJson(object);

    }

    public Book createBook() {
        Book book = new Book(
            this.title,
            this.authors,
            this.isbn_13
        );
        return book;
    }

    private static JSONObject getJsonSearch(String key) throws IOException, URISyntaxException  {
        StringBuilder uri_str = new StringBuilder("https://openlibrary.org/search.json?q=");
        uri_str.append(key);
        uri_str.append("&fields=key,title,author_name,editions");

        URI uri = new URI(uri_str.toString());
        return getJson(uri);
    }

    private static JSONObject getJson(URI uri) throws IOException, URISyntaxException {
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
    }

    private static JSONObject getJson(String key) throws IOException, URISyntaxException {
        StringBuilder uri_str = new StringBuilder("https://openlibrary.org/");
        uri_str.append(key);
        uri_str.append(".json");
        URI uri = new URI(uri_str.toString());
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
    }

    private static String getTitleJson(JSONObject object) {
        JSONArray docs = object.getJSONArray("docs");
        JSONObject docsObj = docs.getJSONObject(0);
        JSONObject editions = docsObj.getJSONObject("editions");
        JSONArray editionsDocs = editions.getJSONArray("docs");
        JSONObject editionDocsObj = editionsDocs.getJSONObject(0);
        String title = editionDocsObj.getString("title");

        return title;
    }

    private static Person[] getAuthorJson(JSONObject object) {
        JSONArray docs = object.getJSONArray("docs");
        JSONObject docsObj = docs.getJSONObject(0);
        JSONArray authorsArray = docsObj.getJSONArray("author_name");
        int nAuthors = authorsArray.length();
        Person[] authors = new Person[nAuthors];
        for (int i = 0; i < nAuthors; i++) {
            //authors[i] = new Person(authorsObj.getJSONArray(i).toString());
            authors[i] = new Person(authorsArray.getString(i));
        }
        return authors;       
    }

    private static Isbn getIsbnJson(JSONObject object) throws IOException, URISyntaxException {
        JSONArray docs = object.getJSONArray("docs");
        JSONObject docsObj = docs.getJSONObject(0);
        JSONObject editions = docsObj.getJSONObject("editions");
        JSONArray editionsDocs = editions.getJSONArray("docs");
        JSONObject editionDocsObj = editionsDocs.getJSONObject(0);
        String key = editionDocsObj.getString("key");
        
        // getting the 
        JSONObject books = getJson(key);
        JSONArray isbnArray = books.getJSONArray("isbn_13");
        Isbn isbn_13 = new Isbn(isbnArray.getString(0), false);

        return isbn_13;
    }

    /* old
    public OpenLibraryAPI(Isbn isbn) throws IOException, URISyntaxException {
        // get the book entry via isbn
        JSONObject object = jsonAccess("isbn/" + isbn);
        this.title = object.getString("title");
        this.isbn_13 = new Isbn(object.getJSONArray("isbn_13").getString(0), false);

        // get author's name... unfortunately, this requires a second API request in
        // openlibrary
        this.author = new Person(
            jsonAccess(object.getJSONArray("authors").getJSONObject(0).getString("key")).getString("name"));
    }

    public Book createBook() {
        Book book = new Book(
            this.title,
            this.author,
            this.isbn_13
        );
        return book;
    }


    private static JSONObject jsonAccess(String key) throws IOException, URISyntaxException {
        StringBuilder uri_str = new StringBuilder("https://openlibrary.org/");
        uri_str.append(key);
        uri_str.append(".json");

        URI uri = new URI(uri_str.toString());
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
    }
    */
}
