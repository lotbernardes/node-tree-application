# NODE TREE APPLICATION

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

## TODO
..* More Unit Tests
..* Strategy Base Data Layer
..* Redis for Caching
..* Docker integration on Heroku
..* Non Relational Persistence Strategy
..* Circuit Breaker Data Access Commands
