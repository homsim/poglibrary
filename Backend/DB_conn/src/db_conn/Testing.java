package db_conn;

public class Testing {
    public static void main(String[] args) {
        Person borrower = new Person("Ralf", "Witz");
        Book book = new Book("Das Kind", new Person("Sebastian", "Fitzek"), new Isbn("9783785749166"));

        Queries.addBorrower(borrower, book);
    }
}