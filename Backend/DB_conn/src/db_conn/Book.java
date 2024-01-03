package db_conn;

public class Book {
    String title;
    Person author;
    Isbn isbn;

    boolean borrowed;
    Person borrowed_by;

    public Book() {
        this.title = "";
        this.author = new Person();
        this.isbn = new Isbn();
        borrowed = false;
        borrowed_by = new Person();
    }

    public Book(String title, Person author, Isbn isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        borrowed = false;
        borrowed_by = new Person();
    }

    @Override
    public String toString() {
        // arbitrary
        return this.title + "; " + this.author;
    }
}
