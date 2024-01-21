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
import org.json.JSONException;

// Class that gets books informations via ISBN

public class OpenLibraryAPI {
    /*
     * ToDo:
     * - (get covers, that can be added in the DB:
     * https://covers.openlibrary.org/b/isbn/<SOME_ISBN>-M.jpg)
     */

    public String title;
    public Person[] authors;
    public Isbn isbn_13;

    private JSONObject books;
    private JSONObject search;

    public OpenLibraryAPI(Isbn isbn) throws IOException, URISyntaxException {
        // get the book entry via isbn
        this.search = getJsonSearch(isbn.toString());

        JSONArray docs = this.search.getJSONArray("docs");
        JSONObject docsObj = docs.getJSONObject(0);
        JSONObject editions = docsObj.getJSONObject("editions");
        JSONArray editionsDocs = editions.getJSONArray("docs");
        JSONObject editionDocsObj = editionsDocs.getJSONObject(0);
        String key = editionDocsObj.getString("key");
        this.books = getJson(key);

        this.title = getTitleJson(this.books);
        this.authors = getAuthorJson(this.search);
        this.isbn_13 = getIsbnJson(this.books);

    }

    public Book createBook() {
        Book book = new Book(
                this.title,
                this.authors,
                this.isbn_13);

        return book;
    }

    private static JSONObject getJsonSearch(String key) throws IOException, URISyntaxException {
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

    private String getTitleJson(JSONObject books) throws IOException, URISyntaxException {
        String fullTitle = "";
        String title = books.getString("title");
        try {
            String subtitle = books.getString("subtitle");
            fullTitle = title + " - " + subtitle;
        } catch (JSONException ex) {
            // if there is no subtitle, use only the title
            fullTitle = title;
        }

        return fullTitle;
    }

    private static Person[] getAuthorJson(JSONObject search) {
        JSONArray docs = search.getJSONArray("docs");
        JSONObject docsObj = docs.getJSONObject(0);
        JSONArray authorsArray = docsObj.getJSONArray("author_name");
        int nAuthors = authorsArray.length();
        Person[] authors = new Person[nAuthors];
        for (int i = 0; i < nAuthors; i++) {
            authors[i] = new Person(authorsArray.getString(i));
        }

        return authors;
    }

    private static Isbn getIsbnJson(JSONObject books) throws IOException, URISyntaxException {
        JSONArray isbnArray = books.getJSONArray("isbn_13");
        Isbn isbn_13 = new Isbn(isbnArray.getString(0), false);

        return isbn_13;
    }
}
