package com.poglibrary.backend.model;

import java.util.Arrays;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String firstname;
    
    private String lastname;

    public Person(String name) {
        if (name.indexOf(",") == -1) {
            if (name.indexOf(" ") == -1) {
                // given String: "Mustermann"
                this.firstname = "";
                this.lastname = name;
            } else {
                // given String: "Max M. Mustermann"
                String[] splitted = name.split(" ");
                this.firstname = String.join(" ", Arrays.asList(Arrays.copyOfRange(splitted, 0, splitted.length - 1)));
                this.lastname = splitted[splitted.length - 1];
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

    public String getFormal() {
        // get formal writing of a persons name,
        // i.e. "Mustermann, Max"
        return this.lastname + ", " + this.firstname;
    }

    @Override
    public String toString() {
        return this.firstname + " " + this.lastname;
    }
}