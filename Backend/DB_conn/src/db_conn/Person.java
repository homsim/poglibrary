package db_conn;

public class Person {
    String firstname;
    String lastname;

    public Person() {
        this.firstname = "";
        this.lastname = "";
    }

    public Person(String name) {
        if (name.indexOf(",") == -1) {
            this.firstname = "";
            this.lastname = name;
        } else {
            String[] splitted = name.split(",");
            this.firstname = splitted[1];
            this.lastname = splitted[0];
        }
    }

    public Person(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return this.lastname + ", " + this.lastname;
    }
}
