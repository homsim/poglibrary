package com.poglibrary.backend.control.FetchBookInfo.OpenLibraryTypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Edition {
    // for endpoint /books via BookId or /isbn via isbn
    String title;
    Author[] authors;
    String[] publishers;
    String[] isbn_10;
    String[] isbn_13;
    String full_title;
    String subtitle;
    Integer[] covers; // in this context only as intermediate storage
    Cover[] coverImages;
    String key;

    public String[] getAuthorNames() {
        return Arrays.stream(this.authors)
                .map(Author::getName)
                .toArray(String[]::new);
    }

    public byte[][] getCoverImagesImages() {
        return Arrays.stream(this.coverImages)
                .map(Cover::getImage)
                .toArray(byte[][]::new);
    }
}

