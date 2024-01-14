package db_conn;

import java.util.ArrayList;

public class Isbn {
    private IsbnForm form; // format: either TEN or THIRTEEN
    private String isbn;

    public Isbn() {
        this.form = IsbnForm.THIRTEEN;
        this.isbn = "0000000000000";
    }

    public Isbn(String isbn) {
        String raw = formatIsbn(isbn);
        setIsbnType(raw);
        checkIsbn(raw);
        this.isbn = raw;
    }

    public Isbn(String isbn, boolean format) {
        // format determines if the isbn string has '-' removed or not
        String raw;
        if (format) {
            raw = formatIsbn(isbn);
        } else {
            raw = isbn;
        }
        setIsbnType(raw);
        checkIsbn(raw);
        this.isbn = raw;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public IsbnForm getForm() {
        return this.form;
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
        return raw;
    }

    private void setIsbnType(String isbn) {
        String raw = isbn.replaceAll("-", "");
        try {
            switch (raw.length()) {
                case 10:
                    this.form = IsbnForm.TEN;
                    break;
                case 13:
                    this.form = IsbnForm.THIRTEEN;
                    break;
                default:
                    throw new FaultyIsbnException("ISBN has invalid length. ");
            }
        } catch (FaultyIsbnException ex) {
            ex.printStackTrace();
        }
    }

    /*
     * ##################################################################
     * validity checks
     * ##################################################################
     */

    private void checkIsbn(String isbn) {
        ArrayList<Integer> digits = new ArrayList<Integer>();
        try {

            for (char c : isbn.toCharArray()) {
                if (c != '-' && c != 'X' && !Character.isDigit(c)) {
                    // some faulty char in the ISBN
                    throw new FaultyIsbnException("Invalid character in ISBN");
                } else if (c != '-' && c != 'X' && Character.isDigit(c)) {
                    digits.add(c - '0');
                } else if (c == 'X') {
                    digits.add(10); // in isbn-10 the last digit can be an 'X', which represents a roman 10
                }
            }

        } catch (FaultyIsbnException ex) {
            ex.printStackTrace();
        }

        if (this.form == IsbnForm.TEN) {
            checksumTen(digits);
        } else if (this.form == IsbnForm.THIRTEEN) {
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
            if (chkdigit == 10) {
                chkdigit = 0;   // a calculated 10 is represented by a 0
            }
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
        // isbn-10 is a pain in the ass. implementation of the check will be done
        // later...

        /*
         * int chksum = 0;
         * 
         * try {
         * for (int i = 0; i < digits.size() - 1; ++i) {
         * chksum += digits.get(i) * (digits.size() - i);
         * }
         * int chkdigit = 11 - chksum % 11;
         * if (chkdigit != digits.get(9)) {
         * String err_mess = "Given ISBN-10 check digit " + digits.get(9).toString()
         * + " does not add up with calculated one (" + chkdigit + ").";
         * throw new FaultyIsbnException(err_mess);
         * }
         * } catch (FaultyIsbnException ex) {
         * ex.printStackTrace();
         * }
         */
    }

    private enum IsbnForm {
        TEN,
        THIRTEEN
    }

}

class FaultyIsbnException extends Exception {
    public FaultyIsbnException() {
    }

    public FaultyIsbnException(String message) {
        super(message);
    }
}