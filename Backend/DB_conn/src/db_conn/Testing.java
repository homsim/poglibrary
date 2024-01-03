package db_conn;

public class Testing {
    public static void main(String[] args) {
        try {
            Isbn isbn = new Isbn("978-3-453-31717-8");
            OpenLibraryAPI new_book = new OpenLibraryAPI(isbn);
            Book ret_book = new Book(new_book.title,
                    new_book.author, new_book.isbn_13);
            System.out.println(ret_book.isbn);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}