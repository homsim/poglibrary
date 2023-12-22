#include "Library.h"

Library::Library(std::string name, std::vector<Book> books)
{
    this->name = name;
    try
    {   
        this->books = load_from_db();
    }
    catch(const std::exception& e)
    {
        // if db cannot be found: create empty books object
        this->books;
    }
}