# POC-Springboot-Scala-MongoDB

### REST APIs

curl -XGET 'http://localhost:9090/api/v1/books'

curl -i -X POST 'http://localhost:9090/api/v1/books' \
   -H "Content-Type:application/json" \
   -d \
'{
  "title": "First Book"
}'