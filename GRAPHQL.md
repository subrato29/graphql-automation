Parameterization
--------------------------------------------
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
