#include<string>
#include<vector>

struct Isbn
{   
    // re-work this object
    std::string isbn;
    Isbn() :isbn("000-0-00-000000-0") {};
    Isbn(std::string isbn);

    bool isbn_checks(const std::string& isbn);
    bool chk_ten(const std::string& isbn);
    bool chksum_ten(const std::vector<int>& digits);
    bool chk_thirteen(const std::string& isbn);
    bool chksum_thirteen(const std::vector<int>& digits);
};
