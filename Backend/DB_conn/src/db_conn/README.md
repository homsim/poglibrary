# ToDo

- [ ] Add backup API in case Openlibrary.org fails
- [ ] Clean up the exception handling: which methods should be terminated in case of an exception in which not...
- [ ] -> in `Queries.java` replace the `try-catch` blocks by `throws SQLException` statements in the method declaration
- [ ] Write proper queries -> how to properly stack statements ???
    - [ ] A kind of wrapper function that handles the connection
    - [ ] Respective functions for reading and writing to the DB
- [ ] Think of and implement a method that interprets the socket clients' bytestrings to execeture certain functions



# Socket string interpreter:

The form of the incoming (to the server) request string should enable the following:
- request either `read` or `write` from/to the DB
- give reference to the backend method to execute on the DB
- give function arguments -> this has to be some simple data form, e.g. int/float/char/string send as a bytestring, which is then converted accordingly
    - on the `write` side of things, this will probably come down to passing either Person names or ISBN's

## Draft

### overall

```
<r/w>_<func-name>_[<arg-string>]
```

### arg-string

The commas should separate multiple function arguments from each others, where the string is surrounded by qoutes to 

```
<"<arg-string1>","<arg-string2>",...>
```
