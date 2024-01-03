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
| borrowed       | BOOLEAN      | Is the book currently borrowed?     |
| borrowed_by    | VARCHAR      | By whom was this book borrowed?     |
| cover          | custom...    | Cover image of the book             |

### Persons/Borrowers

| Name           | SQL datatype | Description                         |
| -------------- | ------------ | ----------------------------------- |
| firstname      | VARCHAR      |                                     |
| lastname       | VARCHAR      |                                     |

## Relations

