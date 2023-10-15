# Catering
A simple REST API application created on the Spring Boot framework, which enables end users
to order a service in the field of field service.

## Tests
Before using the application, it is recommended to run tests to check its functionality. This can
be done by selecting `exam-> src-> test-> java folder` and pressing `Ctrl + Shift + F10`, or by right-clicking and selecting
Run All tests.

## Data Base
A PostgreSQL database is connected to the project in memory. It contains 6 tables. Clients - stores information about clients.
Dishes - stores menu data. Security_credentials_clients - stores login data, password (encrypted), user data for
identification. Orders - stores data about
orders. Ordered_menu - stores data about the ordered menu. Ordered_menu_dishes - stores the dishes foreign key.

# Registration
To register, you must go to "http://localhost:8080/registration".
We must pass json format ```sh( POST method: {"firstName": "exampleFirstName", "lastName": "exampleLastName", "emailAddress": "exampleEmailAddress", "phoneNumber": "examplePhoneNumber"} ) ```
to "http://localhost:8080/registration" after which the user will be created and placed in the database.

## Authentication
Get access to endpoints we can only pass authentication.
After passing the authentication, the user has the role USER ,ADMIN or MODERATOR.
The database contains 3 users with different roles:
 - ADMIN: (Login: admin, Password: password),
 - USER: (Login: user, Password: password);
 - MODERATOR: (Login: moderator, Password: password),

### User capabilities

|Available endpoints | Description|
|------|------|
|"http://localhost:8080/client/{id}" |GET method: Get info about client with a specific id.|
|"http://localhost:8080/client/lastName/{lastName}" |GET method: Get info about client with by lastname.|
|"http://localhost:8080/client" |POST method: Сreate client.|
|"http://localhost:8080/client" |PUT method: Update info about client.|
|"http://localhost:8080/menu" |GET method: Get menu|
|"http://localhost:8080/orders/{id}" |GET method: Get info about order with a specific id.|
|"http://localhost:8080/orders" |POST method: Сreate order.|
|"http://localhost:8080/orders" |PUT method: Update order.|
|"http://localhost:8080/orders/{id}" |DELETE method: Delete order with a specific id.|
|"http://localhost:8080/orderedMenu/{id}" |GET method: Get info about orderedMenu with a specific id.|
|"http://localhost:8080/orderedMenu/{orderId}" |POST method: Сreate menu by order`s id.|
|"http://localhost:8080/orderedMenu" |PUT method: Update orderedMenu.|
|"http://localhost:8080/orderedMenu/{id}" |DELETE method: Delete orderedMenu with a specific id.|
|"http://localhost:8080/file/{filename}" |GET method: Get image from menu by filename.|
|"http://localhost:8080/file/list" |GET method: Get list of images from menu.|
|"http://localhost:8080/registration" |POST method: Client registration.|
|"http://localhost:8080/authentication" |POST method: Client authentication.|
` Examples: `
- For GET method: "http://localhost:8080/client/{id}":
```sh"http://localhost:8080/client/2"```
- For POST method: "http://localhost:8080/client", you need to pass json format:
```sh{
"firstName": "testFirstName",
"lastName": "testLastName",
"emailAddress": "testEmailAddress",
"phoneNumber": "testPhoneNumber"
}
```
- For DELETE method: "http://localhost:8080/orders/{id}":
```sh"http://localhost:8080/orders/3"```
___
### MODERATOR capabilities 

- grants extended rights over the basic ROLE_USER role.

| Available endpoints | Description |
| ------ | ------ |
|"http://localhost:8080/client/{id}" |GET method: Get info about client with a specific id.|
|"http://localhost:8080/client/lastName/{lastName}" |GET method: Get info about client with by lastname.|
|"http://localhost:8080/client" |POST method: Сreate client.|
|"http://localhost:8080/client" |PUT method: Update info about client.|
|"http://localhost:8080/menu" |GET method: Get menu|
|"http://localhost:8080/orders/{id}" |GET method: Get info about order with a specific id.|
|"http://localhost:8080/orders" |POST method: Сreate order.|
|"http://localhost:8080/orders" |PUT method: Update order.|
|"http://localhost:8080/orderedMenu/{id}" |GET method: Get info about orderedMenu with a specific id.|
|"http://localhost:8080/orderedMenu/{orderId}" |POST method: Сreate menu by order`s id.|
|"http://localhost:8080/orderedMenu" |PUT method: Update orderedMenu.|
|"http://localhost:8080/file/{filename}" |GET method: Get image from menu by filename.|
|"http://localhost:8080/file/list" |GET method: Get list of images from menu.|
| "http://localhost:8080/client"                 | GET method: Get info about client with a specific id. |
| "http://localhost:8080/dishes/list"            | GET method: Get list of dishes.            |
| "http://localhost:8080/orders/list"            | GET method: Get list of orders.            |
| "http://localhost:8080/file/upload/{filename}" | POST method: Upload image to menu.         |
___
### Admin capabilities

| Available endpoints                            | Description                                |
|------------------------------------------------|--------------------------------------------|
| "http://localhost:8080/client"                 | GET method: Get info about client with a specific id. |
| "http://localhost:8080/client"                 | POST method: Сreate client.                |
| "http://localhost:8080/client"                 | PUT method: Update info about client.      |
| "http://localhost:8080/client/{id}"            | DELETE method: Delete client with a specific id. |
| "http://localhost:8080/dishes/list"            | GET method: Get list of dishes.            |
| "http://localhost:8080/dishes"                 | POST method: Сreate dishes.                |
| "http://localhost:8080/dishes"                 | PUT method: Update dishes.                 |
| "http://localhost:8080/dishes/{id}"            | DELETE method: Delete dishes with a specific id. |
| "http://localhost:8080/menu"                   | GET method: Get menu.                      |
| "http://localhost:8080/orders/list"            | GET method: Get list of orders.            |
| "http://localhost:8080/orders"                 | POST method: Сreate order.                 |
| "http://localhost:8080/orders"                 | PUT method: Update order.                  |
| "http://localhost:8080/orders/{id}"            | DELETE method: Delete order with a specific id. |
| "http://localhost:8080/orderedMenu/{id}"       | GET method: Get info about orderedMenu with a specific id. |
| "http://localhost:8080/orderedMenu/{orderId}"  | POST method: Сreate orderedMenu with order`s id. |
| "http://localhost:8080/orderedMenu"            | PUT method: Update orderedMenu.            | 
| "http://localhost:8080/orderedMenu/{id}"       | DELETE method: Delete orderedMenu with a specific id. |
| "http://localhost:8080/file/upload/{filename}" | POST method: Upload image to menu.         |
| "http://localhost:8080/file/{filename}"        | DELETE method: Delete image by filename.   |
| "http://localhost:8080/file/{filename}"        | GET method: Get image by filename.         |
| "http://localhost:8080/file/list"              | GET method: Get list of images.            |
| "http://localhost:8080/registration"           | POST method: Client registration.          |
| "http://localhost:8080/authentication"         | POST method: Client authentication.        |
`Examples: `
- For POST method: "http://localhost:8080/dishes", you need to pass json format:
```sh{
"name": "testName",
"weight": "testWeight",
“cost”:“testCost”,
“typeOfDish”:“testTypeOfDish”
}
```
- For PUT method: "http://localhost:8080/dishes", you need to pass json format:
```sh{
"id": "testId",
"name": "testName",
"weight": "testWeight",
"cost": "testCost",
"typeOfDish": "testTypeOfDish"
}
```
- For DELETE method: "http://localhost:8080/orders/{id}":
```sh"http://localhost:8080/orders/2"```

### Technologies used:
___
- Java SE 17
- Maven
- Spring Boot
- Spring Data JPA
- Test
- PostgreSQL
- Project Lombok (Getter and Setter Annotations)
- JUnit, Mockito
- Spring Security
- JJWT (Java JSON Web Token)
- Swagger (REST Documentation)
- Actuator
- Validation
- Flyway
- Docker
___
### How to install and run a project:
___
Download the Jar file - `Catering-exam-0.0.1-SNAPSHOT.jar`
To open and run the jar file, follow these instructions:
Before opening the jar file, you need to make sure that JRE is installed on your computer. If you don't have JRE installed yet, you can download it from the official Java website.
___
To open a jar file, you will need a command line or terminal, depending on the operating system you are using:
- `For Windows:`Open the command prompt by pressing the `Win + R` key, type `"cmd"` and press `Enter`.
- `For macOS and Linux:` Open the terminal using the search or press the keyboard shortcut `Ctrl+Alt+T`.
___
Using the command line or terminal, navigate to the directory where the jar file is located. To do this, enter the command `"cd" (change directory)`, and then specify the path to the directory. For example:
- `Windows:`cd С:\Путь\To\Directory
- `macOS and Linux:`cd /path/to/directory
___
After going to the directory with the jar file, enter the command to run it:
- `java -jar filename.jar`

The jar file should open and run using the Java Runtime Environment. If you have any problems or errors, make sure that you have the latest version of the JRE installed.
### Using Docker
___
You can open this example in the Docker Desktop Development Environment feature version 4.22.1 or earlier.
To do some operations:
Download Docker image Tomcat in the application or with the command:
- `docker pull tomcat`

Create your own project image with the command:
- `docker build -t name image`

Create a Dockerfile in the root of the project:
- `FROM openjdk:17-alpine`
- `ARG JAR_FILE=target/Catering-exam-0.0.1-SNAPSHOT.jar`
- `RUN mkdir /app`
- `WORKDIR /app`
- `COPY ${JAR_FILE} /app`
- `ENTRYPOINT java -jar /app/Catering-exam-0.0.1-SNAPSHOT.jar`
___
Create a docker-compose file.yaml at the root of the project:

`version: '3.7'`

`services:`
  - catering-app:
  -  container_name: catering-application
  - image: denisliubenkov/catering:v3
  - ports:
  `"8081:8080"`
  - environment:
  `DB_HOST=db`
  depends_on:
`db`
  networks:
- backend
  db:
  - container_name: db
  - ports:
  `"5432:5432"`
  - environment:
`POSTGRES_PASSWORD=root`
`POSTGRES_DB=Catering`
  - image: postgres
  networks:
- backend

  networks:
  backend:
  driver: `bridge`
___
Run with the command:
- `docker compose up`
  Deployment using docker compose:
`[+] Building 0.0s (0/0)                                                                               docker:default
[+] Running 2/2
✔ Container db                   Started                                                                     0.0s
✔ Container catering-application  Started`

___
Expected result:
- The container list should show two running containers and a port mapping as shown below:
`CONTAINER ID   IMAGE                        COMMAND                  CREATED          STATUS         PORTS                    NAMES
9702fd166afc   denisliubenkov/catering:v2   "/bin/sh -c 'java -j…"   55 seconds ago   Up 7 seconds   0.0.0.0:8081->8080/tcp   catering-application
63d4f52c1748   postgres                     "docker-entrypoint.s…"   56 seconds ago   Up 7 seconds   0.0.0.0:5432->5432/tcp   db`

___
-Stop and remove containers:
- `docker compose down`
`[+] Stopping 2/2
✔ Container catering-application  Stopped                                                                                                                                         0.5s
✔ Container db                    Stopped    
[+] Running 3/3
✔ Container catering-application  Removed                                                                                                                                         0.0s
✔ Container db                    Removed                                                                                                                                         0.0s
✔ Network exam_backend            Removed `
