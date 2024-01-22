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
