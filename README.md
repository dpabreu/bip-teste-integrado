# bip-teste-integrado
Desafio Técnico - BIP Brasil – API de Benefícios
# Visão Geral

Este projeto foi desenvolvido como parte de um desafio técnico com o objetivo de demonstrar conhecimentos em backend Java, frontend, banco de dados, boas práticas, testes automatizados e documentação de APIs.

A solução contempla:

Criação de um banco de dados no PostgreSQL

Desenvolvimento de uma API REST com Spring Boot e Java 17

Implementação de operações CRUD para a entidade Beneficio

Conversão de um EJB Stateless legado para um Service Spring Boot

Correção de bugs e problemas de concorrência

Criação de um frontend em AngularJS

Implementação de testes unitários com JUnit e Mockito

Documentação da API com Swagger (OpenAPI)

# Banco de Dados

SGBD: PostgreSQL

Nome do banco: desafio-bip

Tabela principal: beneficio

A API utiliza JPA/Hibernate para o mapeamento objeto-relacional da entidade Beneficio.

# Backend – Spring Boot
# Tecnologias utilizadas

Java 17

Spring Boot

Spring Data JPA

PostgreSQL Driver

Spring Validation

Swagger / OpenAPI

JUnit 5

Mockito

Funcionalidades

CRUD completo da entidade Beneficio

Transferência de valores entre benefícios

Validações de regra de negócio:

Benefício de origem/destino inexistente

Valor inválido

Saldo insuficiente

Uso de @Transactional para garantir atomicidade

Correção de problemas do EJB legado:

Falta de validações

Possibilidade de saldo negativo

Risco de lost update

Falta de controle transacional

# Conversão do EJB Legado

O desafio incluía um EJB Stateless legado, contendo falhas de negócio e concorrência.
Esse EJB foi convertido para um Service Spring Boot, respeitando as boas práticas atuais:

Problemas identificados no EJB:

Ausência de validações

Risco de saldo negativo

Possibilidade de lost update

Falta de tratamento adequado de exceções

Solução aplicada:

Uso de @Transactional

Validações explícitas

Uso de Repository (Spring Data JPA)

Testes unitários garantindo o comportamento correto

Testes Automatizados

Foram implementados testes unitários utilizando:

JUnit 5

Mockito

O que foi testado:

Transferência com sucesso

Saldo insuficiente

Benefício inexistente

Garantia de persistência correta

Os testes são isolados, sem subir o contexto Spring, focando exclusivamente na regra de negócio.

# Documentação da API – Swagger

A API foi documentada utilizando Swagger/OpenAPI.

Após subir o backend, a documentação pode ser acessada em:

http://localhost:8080/swagger-ui.html


ou

http://localhost:8080/swagger-ui/index.html

# Frontend – AngularJS

O frontend foi desenvolvido em AngularJS por simplicidade e rapidez na entrega do desafio, mantendo comunicação desacoplada via API REST. Em um cenário produtivo, a escolha recomendada seria Angular (2+) ou outro framework moderno.

# Características

Consome a API REST do Spring Boot

Executado de forma independente

Comunicação via HTTP

# Execução do frontend

Foi utilizado o http-server para servir a aplicação AngularJS.

Instalação do servidor HTTP:
npm install -g http-server

# Subir o frontend:
cd ../bip-teste-integrado/frontend
http-server -p 4200


A aplicação ficará disponível em:

http://localhost:4200

# Como Executar a Aplicação
# Banco de Dados

Criar o banco desafio-bip no PostgreSQL

Configurar usuário e senha em application.properties.

# Backend
cd ../bip-teste-integrado/backend-module
mvn clean install -DskipTests

mvn spring-boot:run


API disponível em:

http://localhost:8080


# Considerações Finais

# Este projeto demonstra:

Domínio de Java moderno (Java 17)

Capacidade de modernização de sistemas legados

Conhecimento sólido de Spring Boot

Preocupação com qualidade, testes e documentação

Separação clara entre frontend e backend

Boas práticas de desenvolvimento profissional
