#include<vector>
#include<string>
#include "Book.h"

class Library
{
    /*
    Holds informations about the library. Should be created upon startup
    either from scratch (empty library) or from existing database.
    */
private:
    std::string name;                   // name of the library
    std::vector<Book> books;

    std::vector<Book> load_from_db();   // get the library from a database 
public:
    Library(std::string name, std::vector<Book> books);
};
