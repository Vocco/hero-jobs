# REST API Documentation
There is a REST API exposed to manipulate monsters.


## Methods

### List all monsters
```
curl --request GET --url http://localhost:8080/rest/monsters/
```

### Get a monster by id
```
curl --request GET --url http://localhost:8080/rest/monsters/{id}
```

### Create a new monster
```
curl --request POST --url http://localhost:8080/rest/monsters/create \
  --header 'content-type: application/json' \
  --data '{
    "hitpoints":30,
    "damage":4,
    "name":"Unicorn",
    "size":"medium",
    "strengths":[{"name":"rainbow","level":9000}],
    "weaknesses":[{"name":"fire","level":30}]
  }'
```

##### Delete a monster
```
curl --request DELETE --url http://localhost:8080/rest/monsters/{id}
```

##### Update a monster
```
curl --request POST --url http://localhost:8080/rest/monsters/update \
  --header 'content-type: application/json' \
  --data '{
    "id": {id}
    "hitpoints":30,
    "damage":4,
    "name":"Unicorn",
    "size":"medium",
    "strengths":[{"name":"rainbow","level":9000}],
    "weaknesses":[{"name":"fire","level":30}]
  }'
```

