package com.poglibrary.backend;

import com.poglibrary.backend.control.FetchBookInfo.OpenLibraryClient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;


@SpringBootTest
class DbConnApplicationTests {

	//@Test
	void TryOutStuff() {
		try {
			OpenLibraryClient client = new OpenLibraryClient("9783453317178");
			System.out.println(client.getEdition().toString());
		} catch (IOException | URISyntaxException ex){
			ex.getCause();
		}

    }

}
