# BDS 3.0 Events API - Task 3

Simple REST Events API made as the third task of the DevSuperior Spring React Bootcamp 3.0, using Spring Boot and persiting in h2 database using Spring Data JPA.

## Disclaimer

The repo was forked from [devsuperior/bds04](https://github.com/devsuperior/bds04) with integration tests already made. In order to complete the task, I needed to create **User and Role entites**, **repositories**, **services**, **controllers**, **validations** and **[oauth2](https://oauth.net/2/) configurations** to pass the tests.

## How to install

Clone this repo


```bash
git clone https://github.com/oiagorodrigues/dsbootcamp-java-task-3.git

or 

git clone git@github.com:oiagorodrigues/dsbootcamp-java-task-3.git
```

Open it in your favorite IDE. 
Recommended using [Intellij](https://www.jetbrains.com/pt-br/idea/), [STS](https://spring.io/tools) or [VSCode](https://code.visualstudio.com/).

## Learning topics

- Creating a Data Layer with Entity|Model, Repository and Service
- Creating a API layer with Controller and DTO
- Creating custom Exceptions, custom Errors and an ExceptionHandler
- Seeding the database
- Integration Tests
- OAuth2 Authorization Server configuration
- OAuth2 Resource Server configuration
- JWT
- Spring @Bean and @Configuration

## Conceptual model

<img src="https://user-images.githubusercontent.com/19571060/122941512-d4bb8880-d34b-11eb-86c2-6c0efb97239a.png" width="800">

## Technologies

- Java 11
- Spring Boot 2.4.5
- Spring Data JPA
- H2 Database
- JUnit
- Mockito
- MockMvc

## Tests

### CityControllerIT

- **[insertShouldReturn401WhenNoUserLogged](https://github.com/oiagorodrigues/bds04/blob/57d7a9648e31dd381989498761bec18c05ca831c/src/test/java/com/devsuperior/bds04/controllers/CityControllerIT.java#L51-L63)**: ✅
- **[insertShouldReturn403WhenClientLogged](https://github.com/oiagorodrigues/bds04/blob/57d7a9648e31dd381989498761bec18c05ca831c/src/test/java/com/devsuperior/bds04/controllers/CityControllerIT.java#L66-L81)**: ✅
- **[insertShouldInsertResourceWhenAdminLoggedAndCorrectData](https://github.com/oiagorodrigues/bds04/blob/57d7a9648e31dd381989498761bec18c05ca831c/src/test/java/com/devsuperior/bds04/controllers/CityControllerIT.java#L84-L101)** : ✅
- **[insertShouldReturn422WhenAdminLoggedAndBlankName](https://github.com/oiagorodrigues/bds04/blob/57d7a9648e31dd381989498761bec18c05ca831c/src/test/java/com/devsuperior/bds04/controllers/CityControllerIT.java#L104-L121)** : ✅
- **[findAllShouldReturnAllResourcesSortedByName](https://github.com/oiagorodrigues/bds04/blob/57d7a9648e31dd381989498761bec18c05ca831c/src/test/java/com/devsuperior/bds04/controllers/CityControllerIT.java#L124-L134)** : ✅

### EventControllerIT

- **[insertShouldReturn401WhenNoUserLogged](https://github.com/oiagorodrigues/bds04/blob/57d7a9648e31dd381989498761bec18c05ca831c/src/test/java/com/devsuperior/bds04/controllers/EventControllerIT.java#L53-L65)** : ✅
- **[insertShouldInsertResourceWhenClientLoggedAndCorrectData](https://github.com/oiagorodrigues/bds04/blob/57d7a9648e31dd381989498761bec18c05ca831c/src/test/java/com/devsuperior/bds04/controllers/EventControllerIT.java#L68-L89)** : ✅
- **[insertShouldInsertResourceWhenAdminLoggedAndCorrectData](https://github.com/oiagorodrigues/bds04/blob/57d7a9648e31dd381989498761bec18c05ca831c/src/test/java/com/devsuperior/bds04/controllers/EventControllerIT.java#L92-L113)** : ✅
- **[insertShouldReturn422WhenAdminLoggedAndBlankName](https://github.com/oiagorodrigues/bds04/blob/57d7a9648e31dd381989498761bec18c05ca831c/src/test/java/com/devsuperior/bds04/controllers/EventControllerIT.java#L116-L134)** : ✅
- **[insertShouldReturn422WhenAdminLoggedAndPastDate](https://github.com/oiagorodrigues/bds04/blob/57d7a9648e31dd381989498761bec18c05ca831c/src/test/java/com/devsuperior/bds04/controllers/EventControllerIT.java#L137-L155)** : ✅
- **[insertShouldReturn422WhenAdminLoggedAndNullCity](https://github.com/oiagorodrigues/bds04/blob/57d7a9648e31dd381989498761bec18c05ca831c/src/test/java/com/devsuperior/bds04/controllers/EventControllerIT.java#L158-L176)** : ✅
- **[findAllShouldReturnPagedResources](https://github.com/oiagorodrigues/bds04/blob/57d7a9648e31dd381989498761bec18c05ca831c/src/test/java/com/devsuperior/bds04/controllers/EventControllerIT.java#L179-L187)** : ✅

## Endpoints

### Forbidden and Unauthorized

Only **Login**, **Fetch Events** and **Fetch Cities** resources have no restriction to access.

The rest need to have a special role to be accessed and to perform an action, being the roles **ADMIN** and **CLIENT**.

When accessing a not allowed resource, the API will return the status code **401** for **Unauthorized** permissions, and **403** for **Forbidden** permissions.

These are the respectives responses:

```json
// 401 UNAUTHORIZED
{
    "error": "unauthorized",
    "error_description": "Full authentication is required to access this resource"
}
```

```json
// 403 FORBIDDEN
{
    "error": "access_denied",
    "error_description": "Access is denied"
}
```


### Configuration

To access the authenticated resources, you can log in with the following users:

User with **CLIENT** role:

- User: ana@gmail.com

- Password: 123456

User with **ADMIN** role:

- User: bob@gmail.com

- Password: 123456

### Postman

This is the [postman collection](https://www.getpostman.com/collections/b8f9ce211d8e31cf2b97) with the following routes.

Copy the link and import it in your postman.
### City

#### Fetch Cities

> GET - /cities - Returns a list of cities sorted by name

Example Response

```json
// 200 OK

 [
  {
    "id": 11,
    "name": "Belo Horizonte"
  },
  {
    "id": 8,
    "name": "Belém"
  },
  {
    "id": 2,
    "name": "Brasília"
  },
  {
    "id": 6,
    "name": "Curitiba"
  },
  {
    "id": 3,
    "name": "Fortaleza"
  },
  {
    "id": 7,
    "name": "Goiânia"
  },
  {
    "id": 5,
    "name": "Manaus"
  },
  {
    "id": 9,
    "name": "Porto Alegre"
  },
  {
    "id": 10,
    "name": "Rio de Janeiro"
  },
  {
    "id": 4,
    "name": "Salvador"
  },
  {
    "id": 1,
    "name": "São Paulo"
  }
]
```

#### Create City

> POST - /cities/{id}

Example payload

```json
{
    "name": "New city"
}
```

Example Success Response

```json
// 201 CREATED
// LOCATION: http://localhost:8080/cities/13
{
    "id": 13,
    "name": "New city"
}
```

### Event

#### Fetch events

> GET - /events - Returns a list of paged cities

Example Success Response

```json
// 200
// Content-Type: application/json

{
    "content": [
        {
            "id": 1,
            "name": "Feira do Software",
            "date": "2021-05-16",
            "url": "https://feiradosoftware.com",
            "cityId": 1
        },
        {
            "id": 2,
            "name": "CCXP",
            "date": "2021-04-13",
            "url": "https://ccxp.com.br",
            "cityId": 1
        },
        {
            "id": 3,
            "name": "Congresso Linux",
            "date": "2021-05-23",
            "url": "https://congressolinux.com.br",
            "cityId": 2
        },
        {
            "id": 4,
            "name": "Semana Spring React",
            "date": "2021-05-03",
            "url": "https://devsuperior.com.br",
            "cityId": 3
        },
        {
            "id": 5,
            "name": "Novo evento",
            "date": "2023-07-15",
            "url": "https://novoevento.com.br",
            "cityId": 1
        },
        {
            "id": 6,
            "name": "Novo evento",
            "date": "2023-07-15",
            "url": "https://novoevento.com.br",
            "cityId": 1
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 20,
        "unpaged": false,
        "paged": true
    },
    "last": true,
    "totalElements": 6,
    "totalPages": 1,
    "size": 20,
    "number": 0,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "first": true,
    "numberOfElements": 6,
    "empty": false
}
```

### Create event

> GET /events/{id}

Example request

```json
// Content-Type: application/json

{
    "name": "Novo evento",
    "url": "https://novoevento.com.br",
    "date": "2023-07-15",
    "cityId": 1
}
```

Example Success Response

```json
// 200
// Content-Type: application/json

{
    "id": 7,
    "name": "Novo evento",
    "date": "2023-07-15",
    "url": "https://novoevento.com.br",
    "cityId": 1
}
```
