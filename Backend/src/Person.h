#include<string>

struct Person
{
    std::string firstname;
    std::string lastname;
    Person() : firstname(""), lastname("") {};
    Person(std::string l) : firstname(""), lastname(l) {};
    Person(std::string f, std::string l) : firstname(f), lastname(l) {};
    std::string get_firstname() {return this->firstname;}
    std::string get_lastname() {return this->lastname;}
    std::string get_fullname()  {return this->firstname + " " + this->lastname;}
};