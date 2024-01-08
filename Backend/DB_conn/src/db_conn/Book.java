package db_conn;

public class Book {
    private String title;
    private Person author;
    private Isbn isbn;

    private boolean borrowed;
    private Person borrowed_by;

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

    public String getTitle() {
        return this.title;
    }

    public Person getAuthor() {
        return this.author;
    }

    public Isbn getIsbn() {
        return this.isbn;
    }

    public Boolean getBorrowed() {
        return this.borrowed;
    }

    public Person getBorrower() {
        return this.borrowed_by;
    }

    @Override
    public String toString() {
        // arbitrary
        return this.title + "; " + this.author + "; " + this.isbn.getIsbn();
    }
}
