#include "Isbn.h"

Isbn::Isbn(std::string isbn)
{
    if (Isbn::isbn_checks())
    {
        this->isbn = isbn;
    }
}

bool Isbn::isbn_checks()
{
    // run checks of validity of the isbn
    if (chk_ten() && chk_thirteen())
    {
        return true;
    }
    else
    {
        return false;
    }
}

bool Isbn::chk_ten()
{
    // check isbn-10 validity
    /*
    ... to be implemented
    */
    return true;
}

bool Isbn::chk_thirteen()
{
    // check isbn-13 validity
    /*
    ... to be implemented
    */
    return true;
}