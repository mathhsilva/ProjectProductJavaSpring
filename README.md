# <span style="color:red">Product App.</span>

## Descrição

Product App é uma aplicação de gerenciamento de produtos desenvolvida em Spring Boot. Ela fornece uma API RESTful para realizar operações CRUD (Create, Read, Update, Delete) em produtos, bem como operações de venda e atualização de estoque.

## Funcionalidades

- Criar, listar, atualizar e excluir produtos.
- Vender produtos e atualizar a quantidade em estoque.
- APIs RESTful bem definidas para fácil integração.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven

## Pré-requisitos

- Java 8 ou superior instalado
- Maven para construir o projeto

## Como Configurar e Executar

1. Clone o repositório:
   git clone https://github.com/mathhsilva/ProjectProductJavaSpring.git

2. Navegue até o diretório do projeto:
   cd product-app
     
3. Compile o projeto com Maven:
   mvn clean package
   
4. Execute o aplicativo:
   java -jar target/product-app.jar

O aplicativo estará acessível em http://localhost:8080

## Como Usar
Para utilizar este projeto, você pode testar os endpoints utilizando ferramentas como Insomnia ou Postman.

## Exemplos de Endpoints
- <span style="color:red">Criação de Produto</span>

POST /products
Body:
[
    {
        "name": "Produto 1",
        "price": 10.5,
        "quantity": 100
    },
    {
        "name": "Produto 2",
        "price": 20.0,
        "quantity": 50
    }
]

- <span style="color:red">Venda de Produto</span>

POST /products/sell
Body:

{
    "productId": 1,
    "quantitySold": 10
}
- <span style="color:red">Listagem de Produto</span>

GET /products

- <span style="color:red">Atualização de Produto</span>

PUT /products
Body:
{
    "id": 1,
    "name": "Produto Atualizado",
    "price": 15.0,
    "quantity": 80
}

- <span style="color:red">Adição de Quantidade</span>

PUT /products/add
Body:
{
    "productId": 1,
    "quantity": 20
}

- <span style="color:red">Remoção de Produto</span>

DELETE /products/1


## Autor

- **Matheus Silva**
  - Instagram: [![Instagram](https://img.shields.io/badge/Instagram-%40mathhsilva-orange)](https://www.instagram.com/mathhsilva)
  - LinkedIn: [![LinkedIn](https://img.shields.io/badge/LinkedIn-Matheus%20C.%20Silva-blue)](https://www.linkedin.com/in/matheus-csilva/)
