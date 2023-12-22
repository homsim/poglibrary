#include "Book.h"

Book::Book()
    : title{""}, author{""}, isbn{"000-0-00-000000-0"},
      borrowed{false}, borrowed_to{""}, borrowed_from{""}
{
}

Book::Book(std::string title,
           std::string author,
           Isbn isbn)
{
    this->title = title;
    this->author = author;
    this->isbn = isbn;
    this->borrowed = false;
    // how to assign properly?
    this->borrowed_to{""};
    this->borrowed_from{""};
}