###################################################################################################
# Assuming a backend is running on localhost:8080, this script generates up-to-data
# http responses used for unit the client API unit tests.
###################################################################################################




CREDS_BASE64="dXNlcjpwYXNzd29yZA=="
CREDS=$(echo "$CREDS_BASE64" | base64 --decode)

BASE_URL="http://localhost:8080"
AUTHOR_URL="$BASE_URL/authors"
BOOK_URL="$BASE_URL/books"

######
# How to make sure that the working directory is correct?
######

#################################### /authors
# POST /authors
POST_BODY_AUTHOR='
{
   "firstname": "Liu",
   "lastname": "Cixin"
}'

curl -X POST "$AUTHOR_URL" \
     -H "Content-Type: application/json" \
     -d "$POST_BODY_AUTHOR" \
     -u "$CREDS"

# GET /authors
curl -X GET "$AUTHOR_URL" \
    -u "$CREDS" > AuthorGetAll.json

# GET /authors/1
curl -X GET "$AUTHOR_URL/1" \
    -u "$CREDS" > AuthorGet.json

# PATCH /authors/1
PATCH_BODY_AUTHOR='
{
    "firstname": "Louis"
}
'

curl -X PATCH "$AUTHOR_URL/1" \
     -H "Content-Type: application/json" \
     -d "$PATCH_BODY_AUTHOR" \
     -u "$CREDS" > AuthorGetPatched.json

#################################### /books
# POST /books
POST_BODY_BOOK='
{
   "isbn": "978-3453317178"
}'

curl -X POST "$BOOK_URL" \
     -H "Content-Type: application/json" \
     -d "$POST_BODY_BOOK" \
     -u "$CREDS"

# GET /books
curl -X GET "$BOOK_URL" \
    -u "$CREDS" > BookGetAll.json

# GET /books/1
curl -X GET "$BOOK_URL/1" \
    -u "$CREDS" > BookGet.json

# PATCH /books/1
PATCH_BODY_BOOK='
{
    "title": "Rune"
}
'

curl -X PATCH "$BOOK_URL/1" \
     -H "Content-Type: application/json" \
     -d "$PATCH_BODY_BOOK" \
     -u "$CREDS" > BookGetPatched.json
