package com.poglibrary.backend.control.FetchBookInfo.OpenLibraryTypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    // for endpoint /authors via AuthorId
    String key;
    String name;
}
