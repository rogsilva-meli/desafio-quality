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

#### - Request para criar um Bairro -
POST - localhost:8080/rooms
```json
{
    "room_name": "Sala",
    "room_width": 2,
    "room_length": 3,
    "property": "casa"
}
```

#### - Lista de Bairros válidos -
```json
{ Mandaqui=5400.0, Moema=8100.0, Tatuapé=4960.0, Pinheiros=9000.0, Santana=5190.0}
```

#### - EndPoint para listar as propriedades e respostas dos exercícios -
GET - localhost:8080/rooms
```json
{
    "room_name": "Sala",
    "room_width": 2,
    "room_length": 3,
    "property": "casa"
}
```




