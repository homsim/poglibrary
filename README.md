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

### Backend: C++-Script managing a database
### Datebase: MySQL (subject to change... may be overkill)
### Hardware:

- Raspberry Pi with necessary network features
- Some Android Phone

## Open questions:

- Does the structure make sense? 
    - I would like to use C++ from a learning aspect, but does it even make sense to use it with SQL being the main database?
- How should the phone communicate with the Raspi?
    - Could use a scrap Android phone and permanently connect it via USB-C
    - Network communcation in local network -> could be a pain in the ass...
