package db_conn;

import java.util.Arrays;

public class Person {
    String firstname;
    String lastname;

    public Person() {
        this.firstname = "";
        this.lastname = "";
    }

    public Person(String name) {
        if (name.indexOf(",") == -1) {
            if (name.indexOf(" ") == -1) {
                // given String: "Mustermann"
                this.firstname = "";
                this.lastname = name;
            } else {
                // given String: "Max M. Mustermann"
                String[] splitted = name.split(" ");
                this.firstname = String.join(" ", Arrays.asList(Arrays.copyOfRange(splitted, 0, splitted.length-1)));
                this.lastname = splitted[splitted.length-1];
            }
        } else {
            // given String: Mustermann, Max
            String[] splitted = name.split(",");
            this.firstname = splitted[1];
            this.lastname = splitted[0];
        }
    }

    public Person(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirst() {
        return this.firstname;
    }

    public String getLast() {
        return this.lastname;
    }

    public String getFormal() {
        // get formal writing of a persons name,
        // i.e. "<lastname>, <firstname>"
        String str;
        StringBuilder str_bld = new StringBuilder();
        str_bld.append(this.lastname);
        str_bld.append(", ");
        str_bld.append(this.firstname);
        str = str_bld.toString();

        return str;
    }

    @Override
    public String toString() {
        return this.firstname + " " + this.lastname;
    }
}
