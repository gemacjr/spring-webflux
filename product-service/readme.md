# product-service

```
curl --location --request GET 'localhost:8091/product/all'
```

Response

```
[
    {
        "id": "60adde9cf3220930c86a240d",
        "desc": "M1 Chip Laptop",
        "price": 80000
    },
    {
        "id": "60ade028f3220930c86a240e",
        "desc": "Mobile Phones",
        "price": 15000
    },
    {
        "id": "60ade2b6f3220930c86a240f",
        "desc": "Jents Shoes",
        "price": 5000
    }
]
```

```
curl --location --request GET 'localhost:8091/product/60ade028f3220930c86a240e'
```

```curl
{
    "id": "60ade028f3220930c86a240e",
    "desc": "Mobile Phones",
    "price": 15000
}
```

```
curl --location --request GET 'localhost:8091/product/price-range?min=5000&max=80000'
```

Responsw

```
[
    {
        "id": "60adde9cf3220930c86a240d",
        "desc": "M1 Chip Laptop",
        "price": 80000
    },
    {
        "id": "60ade028f3220930c86a240e",
        "desc": "Mobile Phones",
        "price": 15000
    },
    {
        "id": "60ade2b6f3220930c86a240f",
        "desc": "Jents Shoes",
        "price": 5000
    }
]
```

Simillarly we can do create, update and delete.

