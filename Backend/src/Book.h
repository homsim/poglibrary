#include "Isbn.h"
#include "Person.h"

class Book
{
    // holds informations about the book
private:
    std::string title;
    std::string author;
    Isbn isbn;
    bool borrowed;
    Person borrowed_to;   // who borrowed the book
    Person borrowed_from; // from whom was the book borrowed from
public:
    Book();
    Book(std::string title,
         std::string author,
         Isbn isbn);
};
