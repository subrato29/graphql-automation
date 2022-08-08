Free public GraphQL API
----------------------------------------------------
```
https://hasura.io/learn/graphql/graphiql
```

Query
----------------------------------------------------
```
{
  users(limit: 5) {
    id
    name
    todos {
      title
    }
  }
}
```

Mutation
----------------------------------------------------
```
mutation {
  insert_todos(objects: {title: "Subratos graphQL"}) {
    affected_rows
    returning {
      id
      title
    }
  }
}
```

Parameterization
----------------------------------------------------
```
GraphQL
-----------------
query($limit: Int!){
  users(limit: $limit) {
    id
    name
  }
}

Query variable
-----------------
{
  "limit": 5
}
```

```
GraphQL
-----------------
query ($limit: Int!, $title: String!) {
  todos(where: {title: {_eq: $title}}, limit: $limit) {
    title
    id
  }
}

Query variable
-----------------
{
  "limit": 5,
  "title": "New"
}
```
