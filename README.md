# Reading Is Good
ReadingIsGood is an online books retail firm which operates only on the Internet. Main 
target of ReadingIsGood is to deliver books from its one centralized warehouse to their 
customers within the same day. <br>

## Tech Stack
- Spring Boot
- Spring Security
- Spring Data
- Spring Validation
- Postgresql
- Redis
- Lombok
- Swagger
- JWT

## How to run?

1) RUN TYPE MAVEN

If you want to run project as a mvn, you need to clone project.
Then, you need postgresql and redis. To obtain these you can run docker-compose.yml which stored in `${PROJECT_DIR}\devops\locale\docker-compose.yml`
Finally, prompt `mvn clean install` then run ReadingIsGoodApplication.

2) RUN TYPE DOCKER

If you want to run project as a fully dockerize, you need to clone project.
Then, you can run docker-compose.yml which stored in `${PROJECT_DIR}\devops\prod\docker-compose.yml`
You don't need mvn because I already deploy project image to `dockerhub`.

**SWAGGER - POSTMAN** <br>
SWAGGER URL:`http://localhost:8090/api/swagger-ui.html` <br> 
POSTMAN COLLECTION: `${PROJECT_DIR}\Reading Is Good REST API.postman_collection.json`

## How to use?
There are two user type in this project. First one is `ADMIN` and the other one is `CUSTOMER`. When the project runs, `ADMIN` user created automaticly. <br> <br>
**Admin Account Informations** <br>
Username = admin <br>
Password = admin <br>

You can login to system as a ADMIN with using these informations. <br>

If you want to login as a `CUSTOMER`, you need to create new account with using API `POST - ${HOST}/api/customer`. Then you can login with new account.

**ADMIN PERMISSIONS**
1) Create New Book
2) Update Book Stock
3) Get All Orders
4) Get All Orders with Date Interval

**CUSTOMER PERMISSIONS**
1) Get Customer Orders
2) Create New Order
3) Cancel Order
4) Get Monthly Statistics
