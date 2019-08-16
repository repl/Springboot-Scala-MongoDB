# POC-Springboot-Scala-MongoDB

### MongoDB
#### Insert sample records in Book collection
```
mongo -u root -p qwerty123 --authenticationDatabase admin

use lms_db

db.getCollection('Book').insert({
    "Book-Author" : "REPL",
    "Book-Title" : "Book added using commandline1",
    "ISBN" : "0520226991",
    "Image-URL-L" : "http://myflickr.com/images/P/0520226991.01.LZZZZZZZ.jpg",
    "Image-URL-M" : "http://myflickr.com/images/P/0520226991.01.MZZZZZZZ.jpg",
    "Image-URL-S" : "http://myflickr.com/images/P/0520226991.01.THUMBZZZ.jpg",
    "Publisher" : "REPL Press",
    "Year-Of-Publication" : NumberLong(2019)
})

db.getCollection('Book').find({"ISBN" : "0520226991"})
```

### REST APIs

```
curl -XGET 'http://localhost:9090/api/v1/books'

curl -i -X POST 'http://localhost:9090/api/v1/books' \
   -H "Content-Type:application/json" \
   -d \
'{
  "title": "First Book"
}'

curl -X GET "http://localhost:9090/api/v1/books?searchKey=Book-Title&searchValue=Mythology"
```

### How to run the service
#### Gradle
For development, use the following options:
1. IDE run configuration for gradle. Set gradle task as 'bootRun'. 
2. Command line: gradlew bootRun
3. Command line: gradlew compile; java -jar build/libs/springbootscalamongo-0.0.1-SNAPSHOT.jar

#### SBT
sbt compile run

### MongoDb queries
db.getCollection('Book').find({"ISBN" : {$regex: "0520226151"}})

