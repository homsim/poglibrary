package db_conn;

public class Book {
    private String title;
    private Person[] authors;
    private Isbn isbn;

    private boolean borrowed;
    private Person borrowed_by;

    public Book() {
        this.title = "";
        this.authors = new Person[0];
        this.isbn = new Isbn();
        borrowed = false;
        borrowed_by = new Person();
    }

    public Book(String title, Person[] authors, Isbn isbn) {
        this.title = title;
        this.authors = authors;
        this.isbn = isbn;
        borrowed = false;
        borrowed_by = new Person();
    }

    public String getTitle() {
        return this.title;
    }

    public Person[] getAuthors() {
        return this.authors;
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
        return this.title + "; " + this.authors.toString() + "; " + this.isbn.getIsbn();
    }
}
