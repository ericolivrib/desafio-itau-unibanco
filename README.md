# Desafio Itaú Unibanco - API de Transações

## 📌 Objetivo

Este projeto consiste no desenvolvimento de uma API RESTful para gerenciamento de transações financeiras, permitindo o registro de transações e a obtenção de estatísticas em tempo real. A aplicação foi inspirada no desafio proposto por Rafael Lins no repositório [desafio-itau-vaga-99-junior](https://github.com/rafaellins-itau/desafio-itau-vaga-99-junior).

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Web**
- **Swagger/OpenAPI**
- **Docker**

## 📂 Estrutura do Projeto

- `src/main/java`: Contém o código-fonte da aplicação.
  - `com.erico.desafio.itau`: Pacote principal
    - `configuration`: Configurações da aplicação, incluindo Swagger.
    - `global`: Contém o manipulador de exceções
    - `interceptor`: Contém os logs de requisições e respostas.
    - `transaction`: Fluxo MVC das transações (Controller, Service, Model, DTOs)
      - `controller`: Camada responsável por receber as requisições HTTP.
      - `dto`: Objetos de transferência de dados.
- `src/main/resources`: Recursos da aplicação, como `application.properties`.
- `Dockerfile`: Arquivo para criação da imagem Docker da aplicação.
- `pom.xml`: Gerenciador de dependências Maven.

## 🔧 Como Executar a Aplicação

### Pré-requisitos

- Java 21
- Maven 3.9.9
- Docker (opcional, para execução via container)

### Executando com Maven

```bash
./mvnw clean package
java -jar target/desafio-itau-unibanco-1.0-SNAPSHOT.jar
```

A aplicação estará disponível em: `http://localhost:8080`

### Executando com Docker

```bash
docker build -t desafio-itau-api .
docker run -d -p 8080:8080 --name desafio-itau-api desafio-itau-api
```

## 📘 Documentação da API

A documentação interativa da API está disponível via Swagger (localmente):

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 📌 Endpoints Principais

- `POST /transacao`: Cadastra uma nova transação.
- `GET /estatistica`: Retorna estatísticas das transações realizadas nos últimos 60 segundos.
- `DELETE /transacao`: Remove todas as transações registradas.

## 🧪 Testes

Os testes unitários e de integração estão localizados no diretório `src/test/java`. Para executá-los:

```bash
./mvnw test
```

## 📝 Considerações Finais

Este projeto foi desenvolvido com o intuito de aprimorar conhecimentos em desenvolvimento de APIs RESTful utilizando o ecossistema Spring Boot, além de práticas como versionamento de código, documentação com Swagger e conteinerização com Docker.
