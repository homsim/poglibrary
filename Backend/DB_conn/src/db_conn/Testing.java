package db_conn;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Testing {
    public static void main(String[] args) {
        ArrayList<Isbn> isbns = new ArrayList<Isbn>();
        try {
            Person[] authors = {new Person("Max Mustermann"),
                                new Person("Maximilia musterfrau")};
            Book book = new Book("Das Test Buch",
                authors,
                new Isbn("3423760893")
            );
            Queries.addBook(book);

            /*
            // read in some isbn's
            Scanner scanner = new Scanner(new File("tests/test-isbn.txt"));
            while (scanner.hasNextLine()) {
                isbns.add(new Isbn(scanner.nextLine()));
            }

            System.out.println("-------------------------------------------------");
            for (Isbn isbn : isbns) {
                System.out.println("Running check for ISBN: " + isbn.getIsbn());
                OpenLibraryAPI olasi = new OpenLibraryAPI(isbn);
                System.out.print(olasi.title + ", ");
                for (Person author : olasi.authors) {
                    System.out.print(author.toString() + ", ");
                }
                System.out.println(olasi.isbn_13);
                System.out.println("-------------------------------------------------");
            }
            */
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}