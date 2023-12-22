#include "Isbn.h"

Isbn::Isbn(std::string isbn)
{
    if (Isbn::isbn_checks(isbn))
    {
        this->isbn = isbn;
    }
}

bool Isbn::isbn_checks(const std::string& isbn)
{
    // run checks for validity of the isbn:
    // https://en.wikipedia.org/wiki/ISBN#Check_digits
    return (chk_ten(isbn) || chk_thirteen(isbn)) ? true : false;
}

bool Isbn::chk_ten(const std::string& isbn)
{
    // check isbn-10 validity
    /*
    ... to be implemented
    */
    return true;
}

bool Isbn::chksum_ten(const std::vector<int>& digits)
{
    return true;
}

bool Isbn::chk_thirteen(const std::string& isbn)
{
    // check isbn-13 validity
    int n_digits {0};
    int n_hyph {0};
    std::vector<int> digits;

    for (char c : isbn)
    {
        if (c != '-' && !isdigit(c))
        {
            return false;
        }
        else if (c != '-')
        {
            ++n_digits;
            digits.push_back(c - '0'); // convert char to int
        }
        else if (c == '-')
        {
            ++n_hyph;
        }
    }

    if (n_digits != 13)
    {
        return false;
    }

    if (n_hyph != 4)
    {
        return false;
    }

    return chksum_thirteen(digits);
}

bool Isbn::chksum_thirteen(const std::vector<int>& digits)
{
    int chksum {0};

    for (int i = 0; i < digits.size() -1; ++i)
    {
        chksum += i%2 == 0 ? digits[i] : 3*digits[i];
    }

    if (10 - chksum%10 != digits[12])
    {
        return false;
    }

    return true;
}