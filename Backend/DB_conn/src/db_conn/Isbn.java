package db_conn;

import java.util.ArrayList;

public class Isbn {
    int type; // type: either 10 or 13
    String isbn;

    public Isbn() {
        this.type = 13;
        this.isbn = "0000000000000";
    }

    public Isbn(String isbn) {
        String raw = formatIsbn(isbn);
        checkIsbn(raw);
        this.isbn = raw;
    }

    public Isbn(String isbn, boolean format) {
        // format determines if the isbn string is raw-formatted or not
        String raw;
        if (format) {
            raw = formatIsbn(isbn);
        } else {
            raw = isbn;
        }
        checkIsbn(raw);
        this.isbn = raw;
    }

    @Override
    public String toString() {
        return this.isbn;
    }

    /*
     * ##################################################################
     * formating
     * ##################################################################
     */

    private String formatIsbn(String isbn) {
        String raw = isbn.replaceAll("-", "");
        int type = raw.length();
        try {
            if (type == 10 || type == 13) {
                this.type = type;
            } else {
                throw new FaultyIsbnException("ISBN has invalid length. ");
            }
        } catch (FaultyIsbnException ex) {
            ex.printStackTrace();
        }

        return raw;
    }

    /*
     * ##################################################################
     * validity checks
     * -> can be reworked to all be void instead of boolean
     * ##################################################################
     */

    private void checkIsbn(String isbn) {
        ArrayList<Integer> digits = new ArrayList<Integer>();
        try {

            for (char c : isbn.toCharArray()) {
                if (c != '-' && !Character.isDigit(c)) {
                    // some faulty char in the ISBN
                    throw new FaultyIsbnException("Invalid character in ISBN-13");
                } else if (c != '-') {
                    digits.add(c - '0');
                }
            }

        } catch (FaultyIsbnException ex) {
            ex.printStackTrace();
        }

        if (this.type == 10) {
            checksumTen(digits);
        } else if (this.type == 13) {
            checksumThirteen(digits);
        }
    }

    private static void checksumThirteen(ArrayList<Integer> digits) {
        int chksum = 0;

        try {
            for (int i = 0; i < digits.size() - 1; ++i) {
                chksum += i % 2 == 0 ? digits.get(i) : 3 * digits.get(i);
            }
            int chkdigit = 10 - chksum % 10;
            if (chkdigit != digits.get(12)) {
                String err_mess = "Given ISBN-13 check digit " + digits.get(12).toString()
                        + " does not add up with calculated one (" + chkdigit + ").";
                throw new FaultyIsbnException(err_mess);
            }
        } catch (FaultyIsbnException ex) {
            ex.printStackTrace();
        }
    }

    private static void checksumTen(ArrayList<Integer> digits) {
        // isbn-10 is a pain in the ass. implementation of the check will be done later...

        /*
        int chksum = 0;
        
        try {
            for (int i = 0; i < digits.size() - 1; ++i) {
                chksum += digits.get(i) * (digits.size() - i);
            }
            int chkdigit = 11 - chksum % 11;
            if (chkdigit != digits.get(9)) {
                String err_mess = "Given ISBN-10 check digit " + digits.get(9).toString()
                        + " does not add up with calculated one (" + chkdigit + ").";
                throw new FaultyIsbnException(err_mess);
            }
        } catch (FaultyIsbnException ex) {
            ex.printStackTrace();
        }
        */
    }

}

class FaultyIsbnException extends Exception {
    public FaultyIsbnException() {
    }

    public FaultyIsbnException(String message) {
        super(message);
    }
}