# Desafio-Quality
## BootCamp Backend Java Desafio Testing

### - É necessário criar uma Propriedade e Comodos em endpoints diferentes ! -

#### - Request para criar uma Propriedade -
POST - localhost:8080/properties
```json
{
    "prop_name": "Casa",
    "prop_district": "Moema"
}
```

#### - Lista de Bairros válidos -
```json
{ Mandaqui=5400.0, Moema=8100.0, Tatuapé=4960.0, Pinheiros=9000.0, Santana=5190.0}
```

#### - Request para criar um Cômodo -
POST - localhost:8080/rooms
```json
{
    "room_name": "Sala",
    "room_width": 2,
    "room_length": 3,
    "property": "casa"
}
```

#### - EndPoint para listar as propriedades e respostas dos exercícios -
GET - localhost:8080/rooms
**Retorno**
```json
[
    {
        "prop_name": "Casa",
        "prop_district": "Mandaqui",
        "totalMeters": 50.0,
        "valueProperty": 270000.0,
        "biggestRoom": {
            "room_name": "Sala",
            "room_width": 10.0,
            "room_length": 5.0,
            "squareMetersRoom": 50.0
        },
        "rooms": [
            {
                "room_name": "Sala",
                "room_width": 10.0,
                "room_length": 5.0,
                "squareMetersRoom": 50.0
            }
        ]
    }
]
```




