# ToDo

- [ ] Add backup API in case Openlibrary.org fails
- [ ] Clean up the exception handling: which methods should be terminated in case of an exception in which not...
- [ ] -> in `Queries.java` replace the `try-catch` blocks by `throws SQLException` statements in the method declaration
- [ ] Write proper queries -> how to properly stack statements ???
    - [ ] A kind of wrapper function that handles the connection
    - [ ] Respective functions for reading and writing to the DB
