# user-service

-# Get All Users

```
curl --location --request GET 'http://localhost:8092/user/all' --data-raw ''
```

Response

```
[
    {
        "id": 1,
        "name": "sam",
        "balance": 1000
    },
    {
        "id": 2,
        "name": "mike",
        "balance": 600
    },
    {
        "id": 3,
        "name": "jake",
        "balance": 800
    },
    {
        "id": 4,
        "name": "marshal",
        "balance": 2000
    }
]
```

# Get User By Id

http://localhost:8092/user/2

Response

```
{
    "id": 2,
    "name": "mike",
    "balance": 600
}
```

# Create User

```
curl --location --request POST 'http://localhost:8092/user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Ravi",
    "balance": 1800
}'
```

Response

```
{
    "id": 6,
    "name": "Ravi",
    "balance": 1800
}
```

# Update User

```
curl --location --request PUT 'http://localhost:8092/user/5' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Ram",
    "balance": 1200
}'
```

Response

```
{
    "id": 5,
    "name": "Ram",
    "balance": 1200
}
```

# Create Transaction

```
curl --location --request POST 'http://localhost:8092/user/transaction' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userId" : 1,
    "amount" : 333
}'
```
Execute above Request few times

Response

```
{
    "userId": 1,
    "amount": 333,
    "status": "APPROVED"
}
```

# Get Transaction Details

```
http://localhost:8092/user/transaction?userId=2
```

Response

```
[
    {
        "id": 1,
        "userId": 2,
        "amount": 200,
        "transactionDate": "2021-05-28T17:18:29.332632"
    },
    {
        "id": 2,
        "userId": 2,
        "amount": 200,
        "transactionDate": "2021-05-28T17:18:56.657563"
    },
    {
        "id": 3,
        "userId": 2,
        "amount": 200,
        "transactionDate": "2021-05-28T17:18:57.052621"
    },
    {
        "id": 4,
        "userId": 2,
        "amount": 200,
        "transactionDate": "2021-05-28T17:28:41.174436"
    },
    {
        "id": 5,
        "userId": 2,
        "amount": 200,
        "transactionDate": "2021-05-28T17:28:42.462841"
    }
]
```
