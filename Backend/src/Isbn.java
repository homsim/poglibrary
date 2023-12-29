public class Isbn {
    String isbn;

    public Isbn(String isbn) {
        if (isbn_checks(isbn)) {
            this.isbn = isbn;
        }
    }

    private static boolean isbn_checks(String isbn) {
        // check if the IBSN is valid
        return true;
    }
}
