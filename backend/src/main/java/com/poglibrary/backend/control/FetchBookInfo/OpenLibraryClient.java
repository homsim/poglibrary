package com.poglibrary.backend.control.FetchBookInfo;

import com.poglibrary.backend.control.FetchBookInfo.OpenLibraryTypes.Author;
import com.poglibrary.backend.control.FetchBookInfo.OpenLibraryTypes.Edition;
import com.poglibrary.backend.control.FetchBookInfo.OpenLibraryTypes.Cover;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;

import lombok.Getter;

public class OpenLibraryClient {
    /*
     * ToDo:
     *  - resolve the covers -> need to create a new type
     *  - IS THERE A BETTER ARCHITECTURE TO USE HERE? IT FEELS WRONG TO CREATE AN OBJECT OF THIS CLASS...
     */
    private final String root = "https://openlibrary.org";
    @Getter
    private final Edition edition;
    private final Cover[] covers;

    public OpenLibraryClient(String isbn) throws IOException, URISyntaxException {
        this.edition = createEdition(isbn);
        this.covers = createCover(this.edition);
        this.edition.setCoverImages(this.covers);
    }

    private enum HttpFormat {
        JSON,
        JPG
    }

    private Edition createEdition(String isbn) throws IOException, URISyntaxException {
        String json = ".json";
        Edition edition = fetchAndDeserializeJSON(
                Edition.class,
                this.root + "/isbn/" + isbn + json
        );
        // resolve the authors
        for (Author author : edition.getAuthors()) {
            author.setName(fetchAndDeserializeJSON(
                    Author.class,
                    this.root + author.getKey() + json
            ).getName());
        }
        return edition;
    }

    private Cover[] createCover(Edition edition) {
        String size = "M"; // maybe make this a parameter at some point
        return Arrays.stream(edition.getCovers())
                .map(coverKey -> {
                    try {
                        return new Cover(coverKey, getImageAsByteArray(coverKey, size));
                    } catch (IOException | URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(Cover[]::new);
    }

    private <T> T fetchAndDeserializeJSON(Class<T> clazz, String uriStr) throws IOException, URISyntaxException {
        URL url = (new URI(uriStr)).toURL();
        HttpURLConnection connection = connectOpenLibrary(url, HttpFormat.JSON);
        InputStream inStream = connection.getInputStream();
        return new ObjectMapper().readValue(inStream, clazz);
    }

    private byte[] getImageAsByteArray(Integer key, String size) throws IOException, URISyntaxException {
        URL url = (new URI(
                "https://covers.openlibrary.org/b/id/" +
                        key + "-" + size + ".jpg"
        )).toURL();

        InputStream inputStream = new BufferedInputStream(connectOpenLibrary(
                url,
                HttpFormat.JPG
        ).getInputStream());
        BufferedImage image = ImageIO.read(inputStream);
        inputStream.close();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", bos);

        return bos.toByteArray();
    }

    private HttpURLConnection connectOpenLibrary(URL url, HttpFormat httpFormat) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("GET");
        switch (httpFormat) {
            case JSON:
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("charset", "UTF-8");
            case JPG:
                connection.setRequestProperty("Content-Type", "image/jpeg");
                connection.setRequestProperty("charset", "UTF-8");
        }
        connection.connect();
        return connection;
    }

}
