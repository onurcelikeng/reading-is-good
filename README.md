## Reading Is Good
ReadingIsGood is an online books retail firm which operates only on the Internet. Main target of ReadingIsGood is to
deliver books from its one centralized warehouse to their customers within the same day. That is why stock consistency
is the first priority for their vision operations.

## FEATURES
    • Registering New Customer
    • Placing a new order
    • Tracking the stock of books
    • List all orders of the customer with Pagination
    • Viewing the order details
    • Query Monthly Statistics

## TECH STACK
    • Java 15
    • Spring Boot 2.6.1
        - Spring Security
        - Bearer Token Auth (JWT)
        - Spring Data MongoDB
    • JUnit 5
    • Lombok
    • MongoDB
    • Swagger3
    • Docker
    • Docker Compose

## COMPILE
Requires JDK15, Maven and Docker.

Run **build.sh** file in directory root. This will compile application and build a docker image.
   
    > chmod +x build.sh 
    
    > ./build.sh

## RUN

    > docker-compose -f docker-compose.yaml up

## USAGE

    > http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/