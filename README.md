# NODE TREE APPLICATION

## DESIGN
### Two "separate" modules
* Core "api" module.
* Database access module.

```
           .----------------------.
           | DEPENDENCY INJECTION |
           '----------------------'
                       ^
                       |
    .------------.     |      .-------------.
    | API MODULE | ----^--->  | DATA MODULE |
    '------------'            '-------------'
```

## TECHNOLOGIES
### USED
* Java 8
* Hibernate
* Spring

### WANTED TO USE
* Docker for Infrastructure.
* Redis for Strategy Based Data Layer.
* Hystrix for Circuit Breaker data access wrapping.
* Native SQL (Recursive query in MySQL proved to be too hard).

## TODO
* More Unit Tests.

## API

### BASE PATH
> https://hidden-sea-49567.herokuapp.com/v1/tree or https://hidden-sea-49567.herokuapp.com

### GET ALL
> /nodes or /node

### GET WITH IDENTIFIER
> /nodes/{id}

### GET AS A 'LINEAR VIEW' (ONLY THE CHILDREN IN A NON NESTED VIEW)
> /nodes/{id}/linear or /node/{id}

### POST A NEW NODE
> /nodes or /node

**example:**
```
{
  "parentId": null,
  "code": "FATHER",
  "description": "The First One",
  "detail": "The First One Details",
}
```

### PUT AN EXISTING NODE
> /nodes/{id} or /node

**example:**
```
{
  "id": 1,
  "parentId": null,
  "code": "FATHER",
  "description": "The First One",
  "detail": "The First One Details",
}
```
