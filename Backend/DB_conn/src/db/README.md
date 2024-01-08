# MariaDB: PogLibrary
## Tables

The PogLibrary has the following tables with respective members:

### Library

| Name           | SQL datatype | Description                         |
| -------------- | ------------ | ----------------------------------- |
| name           | VARCHAR      | Name of the Library                 |


### Books

| Name           | SQL datatype | Description                         |
| -------------- | ------------ | ----------------------------------- |
| id             | INTEGER      |                                     |
| isbn           | VARCHAR      |                                     |
| author         | VARCHAR      |                                     |
| title          | VARCHAR      |                                     |
| quantity       | INTEGER      | Number of copies in the library     |
| borrowed       | BOOLEAN      | Is the book currently borrowed?     |
| borrower_id    | INTEGER      | By whom was this book borrowed?     |
| cover          | custom...    | Cover image of the book             |

### Borrowers

| Name           | SQL datatype | Description                         |
| -------------- | ------------ | ----------------------------------- |
| borrower_id    | INTEGER      |                                     |
| firstname      | VARCHAR      |                                     |
| lastname       | VARCHAR      |                                     |

## Relations

Borrowers(borrower_id) -> Books(borrower_id) 