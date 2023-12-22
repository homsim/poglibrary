#include<string>
#include<vector>

struct Isbn
{
    std::string isbn;
    Isbn() :isbn("") {};
    Isbn(std::string isbn);

    bool isbn_checks(const std::string& isbn);
    bool chk_ten(const std::string& isbn);
    bool chksum_ten(const std::vector<int>& digits);
    bool chk_thirteen(const std::string& isbn);
    bool chksum_thirteen(const std::vector<int>& digits);
};
