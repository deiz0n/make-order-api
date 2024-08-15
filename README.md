# MakeOrder - API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Postman](https://img.shields.io/badge/Postman-FF6C37.svg?style=for-the-badge&logo=Postman&logoColor=white)
![Git](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![Heroku](https://img.shields.io/badge/heroku-%23430098.svg?style=for-the-badge&logo=heroku&logoColor=white)

## `📑` Introdução
O MakeOrder é sistema de gerenciamento de pedidos para restaurantes, é uma solução tecnológica elaborada para aprimorar a eficiência no processo de pedidos e entrega de refeições em restaurantes e estabelecimentos similares. O principal objetivo é otimizar todo o processo do pedido, agilizar o atendimento e fornecer uma excelente experiência de usuário

## `▶️` Execução do projeto
1. Clone o repositório
```bash 
https://github.com/deiz0n/make-order-api
```
2. Modifique as seguintes variáveis de ambiente no docker-compose.yaml <br>
   2.1. Flyway e PostgreSQL:
   ```bash
    DB_URL: [default:jdbc:postgresql://localhost:5432/makeorder]
    DB_USER: [default:postres]
    DB_PASSWORD: [default:1234567]
   ```
   2.2. Key para geração do JWT:
   ```bash
    KEY: [default:mk-api]
   ```
   2.3. Definições do email responsável pelo envio de emails: <br>
   Importante: Os atributos abaixos são **essenciais** para o funcionamento da API!
   ```bash
    EMAIL:
    PASSWORD:
   ```

3. Execute o docker-compose
```bash 
docker-compose up
```
## `📋` - Documentação

A documentação completa da API, incluindo todos os endpoints, parâmetros e exemplos de uso, 
pode ser encontrada [aqui](https://make-order-api-b43b46b00eb4.herokuapp.com/swagger-ui/index.html#/).

## `📫` - Contato

Para entrar em contato, envie um email para doardo.ns@gmail.com ou visite meu perfil no Linkedln [aqui](https://www.linkedin.com/in/carlos-eduardo-ns/).
