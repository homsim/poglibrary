package com.poglibrary.backend.control.FetchBookInfo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
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

import javax.imageio.ImageIO;

import lombok.Getter;

@Getter
public class OpenLibraryClient {

    /*
     * ToDo: refactor the whole class (possibly into multiple classes) and use Jackson
     */

    private final String title;
    private final Set<String> authors;
    private final String isbn_13;
    private final byte[] cover;

    private final JSONObject books;
    private final JSONObject search;

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
        this.cover = getCover(this.books,"M");

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

        URL url = (new URI(uriStr)).toURL();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("charset", "utf-8");
        connection.connect();

        InputStream inStream = connection.getInputStream();
        JSONTokener tokener = new JSONTokener(inStream);
        return new JSONObject(tokener);
    }

    private static JSONObject getJson(URI uri) throws IOException {
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
        return new JSONObject(tokener);
    }

    private String getTitleJson(JSONObject books) {
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

    private static String getIsbnJson(JSONObject books) {
        JSONArray isbnArray = books.getJSONArray("isbn_13");
        return  isbnArray.getString(0);
    }

    private byte[] getCover(JSONObject books, String size) throws IOException, URISyntaxException {
        String uriStr = "https://covers.openlibrary.org/b/id/" +
                books.getJSONArray("covers").getInt(0) +
                "-" +
                size +
                ".jpg";

        URL url = (new URI(uriStr)).toURL();

        return getImageAsByteArray(url);
    }

    private byte[] getImageAsByteArray(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("GET");

        InputStream inputStream = new BufferedInputStream(connection.getInputStream());
        BufferedImage image = ImageIO.read(inputStream);
        inputStream.close();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", bos);

        return bos.toByteArray();
    }
}
