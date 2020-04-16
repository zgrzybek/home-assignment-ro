# home-assignment-ro
Home Assignment for Ro.... (hidden company name)

## Swagger
Swagger: ``http://localhost:8080/swagger-ui.html``

See also [Example requests](#example-requests)

## Requirements
- JAVA 8
- Docker
- Gradle

## How to run the app
### Build
```
./gradlew clean build
```

### Run
Build before running

#### Run with docker
```
docker-compose up --build --force-recreate
```

#### Run without docker
```
docker run -d -p 27017:27017 -v ~/data:/data/mongodb mongo
```
```
./gradlew bootRun --args='--spring.profiles.active=local'
```

## Things missing or decision needed
Things missing because of time constraints:
- No error handling, missing controller advices, etc., contains only simplified validation on DTOs
- Description of Api and Model in Swagger
- Database indexes, schema, constraints
- SoftDeletableMongoRepository is the simplest Soft Delete implementation, there are other and better ways
- Using UUID for SKU for simplicity, a String might be needed instead
- Proper unit test coverage, only example tests are provided
- Using LocalDateTime for simplicity, time strategy needs to be defined
- Currency format needs some better definition


## Example requests
This section contains example requests.

### Product

#### Create a new product
Command:
```
curl -X POST "http://localhost:8080/api/v1/products" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"product name\", \"price\": 3.13}"
```

Request:
```json
{
  "name": "product name",
  "price": 3.13
}
```
Response:
```json
{
  "sku": "faeda43a-c1e6-4b16-98f6-e74b3d61b621",
  "name": "product name",
  "price": 3.13,
  "creationTime": "2020-04-16T17:03:28.868"
}
```

#### Retrieve a list of all products

Command:
```
curl -X GET "http://localhost:8080/api/v1/products?page=0&size=5" -H "accept: */*"
```

Response:
```json
{
  "content": [
    {
      "sku": "faeda43a-c1e6-4b16-98f6-e74b3d61b621",
      "name": "product name",
      "price": 3.13,
      "creationTime": "2020-04-16T17:03:28.868"
    }
  ],
  "pageable": {
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 5,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "first": true,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "size": 5,
  "number": 0,
  "numberOfElements": 1,
  "empty": false
}
```

#### Update a product

Command:
```
curl -X PUT "http://localhost:8080/api/v1/products/faeda43a-c1e6-4b16-98f6-e74b3d61b621" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"updated name\", \"price\": 4.07}"
```

Request:
```json
{
  "name": "updated name",
  "price": 4.07
}
```

No response, only 200 code

#### GET a product

Command:
```
curl -X GET "http://localhost:8080/api/v1/products/faeda43a-c1e6-4b16-98f6-e74b3d61b621" -H "accept: */*"
```

Response:
```json
{
  "sku": "faeda43a-c1e6-4b16-98f6-e74b3d61b621",
  "name": "updated name",
  "price": 4.07,
  "creationTime": "2020-04-16T17:03:28.868"
}
```

#### Delete a product (soft deletion)

Command:
```
http://localhost:8080/api/v1/products/faeda43a-c1e6-4b16-98f6-e74b3d61b621

```

No response, only 200 code

### Order

#### Placing an order

Command:
```
curl -X POST "http://localhost:8080/api/v1/orders" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"buyer\": { \"email\": \"email@fake.com\" }, \"products\": [ { \"productSku\": \"9f222302-06b7-4c93-bc40-26c93bf2d0de\", \"quantity\": 2 }, { \"productSku\": \"88acffad-2b05-44e7-a072-da49cf22f4fb\", \"quantity\": 3 } ]}"
```

Request:
```json
{
  "buyer": {
    "email": "email@fake.com"
  },
  "products": [
    {
      "productSku": "9f222302-06b7-4c93-bc40-26c93bf2d0de",
      "quantity": 2
    },
    {
      "productSku": "88acffad-2b05-44e7-a072-da49cf22f4fb",
      "quantity": 3
    }
  ]
}
```
Response:
```json
{
  "id": "f5fe3cb6-9972-433e-83da-29efeea0e482",
  "products": [
    {
      "productSku": "9f222302-06b7-4c93-bc40-26c93bf2d0de",
      "quantity": 2,
      "price": 3.11
    },
    {
      "productSku": "88acffad-2b05-44e7-a072-da49cf22f4fb",
      "quantity": 3,
      "price": 2.13
    }
  ],
  "totalPrice": 12.61,
  "creationTime": "2020-04-16T17:10:13.302",
  "buyer": {
    "email": "email@fake.com"
  }
}
```

#### Retrieving all orders within a given time period,

Command:
```
curl -X GET "http://localhost:8080/api/v1/orders?from=2020-04-16T17%3A10%3A12.000&page=0&size=5&to=2020-04-16T17%3A10%3A16.000" -H "accept: */*"
```

Response:
```json
{
  "content": [
    {
      "id": "f5fe3cb6-9972-433e-83da-29efeea0e482",
      "products": [
        {
          "productSku": "9f222302-06b7-4c93-bc40-26c93bf2d0de",
          "quantity": 2,
          "price": 3.11
        },
        {
          "productSku": "88acffad-2b05-44e7-a072-da49cf22f4fb",
          "quantity": 3,
          "price": 2.13
        }
      ],
      "totalPrice": 12.61,
      "creationTime": "2020-04-16T17:10:13.302",
      "buyer": {
        "email": "email@fake.com"
      }
    }
  ],
  "pageable": {
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 5,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "first": true,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "size": 5,
  "number": 0,
  "numberOfElements": 1,
  "empty": false
}
```
