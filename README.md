# General idea

The general idea is to build a library system that archives all owned books, the affiliation and/or who has borrowed a book.

# Desired features

- Scaning books saves them in a library
- Checking books in and and out adds a borrower
    - should be as convenient as possible. Best case would be an Android App that handles the Frontend
        - Either: Scan books' barcode; Scan books' ISBN; Recognise book via image recognition (ML)
        - At minimum: Enter ISBN manually
- Deleting books from the library
- Access informations about the books and borrower

# Project structure

## First draft

### Frontend: Android-App (PogLibrary) in Kotlin

- Access the library archive
- See who borrowed the book and when
- Use camera in some way (see above) to access books

### Backend: Java-Script managing a database

- JDBC (Java DataBase Connectivity) communicates with local database (local to the JVM)
- Manages data integrity, i.e. checks ISBN
- Finds book informations from some internet database and fills the database automatically from only the ISBN

### Datebase: MySQL/mariaDB

**Tables:**
(The equivalent dataclasses in Java will be similar)

- *Libaries* (in case the user wants more structure)
- *Books*
- *Persons* (to store borrowers)

### Hardware:

- Raspberry Pi with necessary network connection
- Some Android Phone/Tablet

## Open questions:

- Does the structure make sense? 
    - I would like to use Java from a learning aspect, but does it even make sense to use it with SQL being the main database?
- How should the phone communicate with the Raspi?
    - Could use a scrap Android phone and permanently connect it via USB-C
    - Network communcation in local network -> could be a pain in the ass...

# Development plan

- [X] Conceptualize the basics
- [X] Set up the basic framework of the DB
- [ ] Sketch out the Java-DB-interface
- [ ] Implement a socket using `java.net.Socket` to enable external access to the interface -> BeanShell (`bsh.Interpreter`) can be used to interprate strings as code in-place. This can be used to interprate the request strings sent via the Socket connection
- [ ] Build the mainframe, which automatically installs, initializes and sets up a new DB on a new device
- [ ] Set it up on a Raspi
- [ ] Research network stuff, set up the Raspi for network access and make it secure
- [ ] Write the App on my old Pixel 4a or Lenovo tablet
- [ ] Think about deployment: Automatic deployment and build via git vs Docker
