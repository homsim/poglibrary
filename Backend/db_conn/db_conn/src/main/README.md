# ToDo

- [ ] Add backup API in case Openlibrary.org fails
- [X] Write proper queries -> how to properly stack statements
- [X] Think of and implement a method that interprets the socket clients' bytestrings to execeture certain functions


# Socket string interpreter:

I need a way for the client to communicate with the backend server. Somehow requests about the execution of methods need to be made. The idea is to interprate the sent bytestring on the server, i.e. to execute some function or whatever. The form of the incoming (to the server) request string should enable the following:
- request either `read` or `write` from/to the DB
- give reference to the backend method to execute on the DB
- give function arguments -> this has to be some simple data form, e.g. int/float/char/string send as a bytestring, which is then converted accordingly
    - on the `write` side of things, this will probably come down to passing either Person names or ISBN's

## Solution

Use Reflection (`java.lang.reflect`)
