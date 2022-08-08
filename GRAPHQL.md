Parameterization
----------------------------------------------------
```
query($limit: Int!){
  users(limit: $limit) {
    id
    name
  }
}
```

```
query ($limit: Int!, $title: String!) {
  todos(where: {title: {_eq: $title}}, limit: $limit) {
    title
    id
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
