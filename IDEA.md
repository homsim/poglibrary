# General idea

The general idea is to build a library system that archives all owned books, the affiliation and/or who has borrowed a book.

# Desired features

- Scaning, checking books in and and out should be as convenient as possible. Best case would be an Android App that handles the Frontend
    - Either: Scan books' barcode; Scan books' ISBN; Recognise book via image recognition (ML)
    - At minimum: Enter ISBN manually
- Keep track of all the books in the library
- Keep track of who owns the book, who borrowed it, when was it borrowed

# Project structure

## First draft

### Frontend: Android-App in Kotlin
### Backend: C++-Script managing a database
### Datebase: MySQL
### Hardware:

- Raspberry Pi
- Some Android Phone

## Questions:

- How should the phone communicate with the Raspi?
    - Could use a scrap Android phone and permanently connect it via USB-C
    - Network communcation in local network -> could be a pain in the ass...
