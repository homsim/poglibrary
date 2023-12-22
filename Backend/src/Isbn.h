#include<string>

struct Isbn
{
    std::string isbn;
    Isbn() :isbn("") {};
    Isbn(std::string isbn);

    bool isbn_checks();
    bool chk_ten();
    bool chk_thirteen();
};
