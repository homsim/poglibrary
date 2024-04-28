# General idea

The general idea is to build a library system that archives all owned books, the affiliation and/or who has borrowed a book.

# Install/run the backend

The build process is handle by `maven`. From within the projects root directory, run:

```
mvn -f backend/db_conn/pom.xml compile
```

To run without re-compiling, run:

```
mvn -f backend/db_conn/pom.xml spring-boot:run
```

# Development plan

- [X] Conceptualize the basics
- [X] Set up the basic framework of the DB
- [X] Sketch out the Java-DB-interface
- [X] Implement a socket using `java.net.Socket` to enable external access to the interface -> Interpretation of requested methods is done using `java.lang.reflect`
- [ ] Build the mainframe, which automatically installs, initializes and sets up a new DB on a new device
- [ ] Set it up on a Raspi
- [ ] Research network stuff, set up the Raspi for network access and make it secure
- [ ] Write the App on my old Pixel 4a or Lenovo tablet
- [ ] Think about deployment: Automatic deployment and build via git vs Docker
