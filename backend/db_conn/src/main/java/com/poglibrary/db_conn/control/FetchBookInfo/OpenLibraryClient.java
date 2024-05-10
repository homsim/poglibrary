package com.poglibrary.db_conn.control.FetchBookInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import lombok.Getter;

@Getter
public class OpenLibraryClient {

    private String title;

    private Set<String> authors;

    private String isbn_13;

    private JSONObject books;
    private JSONObject search;

    public OpenLibraryClient(String isbn) throws IOException, URISyntaxException {
        this.search = getJsonSearch(isbn);

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

    private static JSONObject getJsonSearch(String key) throws IOException, URISyntaxException {
        String uriStr = "https://openlibrary.org/search.json?q=" +
                key +
                "&fields=key,title,author_name,editions";

        URI uri = new URI(uriStr);
        return getJson(uri);
    }

    private static JSONObject getJson(String key) throws IOException, URISyntaxException {
        String uriStr = "https://openlibrary.org/" +
                key +
                ".json";

        URI uri = new URI(uriStr);
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

    private String getTitleJson(JSONObject books) throws IOException, URISyntaxException {
        String fullTitle = "";
        String title = books.getString("title");
        try {
            String subtitle = books.getString("subtitle");
            fullTitle = title + " - " + subtitle;
        } catch (JSONException ex) {
            // if there is no subtitle, use only the title
            // -> somehow log this as a WARNING
            fullTitle = title;
        }

        return fullTitle;
    }

    private static Set<String> getAuthorJson(JSONObject search) {
        JSONArray docs = search.getJSONArray("docs");
        JSONObject docsObj = docs.getJSONObject(0);
        JSONArray authorsArray = docsObj.getJSONArray("author_name");
        
        Set<String> authors = new HashSet<String>();
        for (int i = 0; i < authorsArray.length(); i++) {
            authors.add(authorsArray.getString(i));
        }

        return authors;
    }

    private static String getIsbnJson(JSONObject books) throws IOException, URISyntaxException {
        JSONArray isbnArray = books.getJSONArray("isbn_13");
        String isbn_13 = isbnArray.getString(0);

        return isbn_13;
    }
}
