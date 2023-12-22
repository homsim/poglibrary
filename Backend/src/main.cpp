#include "Book.h"
#include<iostream>


int main()
{
    try
    {
        /* code */

        return 0;
    }
    catch(const std::exception& e)
    {
        std::cerr << e.what() << '\n';

        return 1;
    }
}