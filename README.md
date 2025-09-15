# Sistema de Gerenciamento de Biblioteca Universitária

Este é um sistema simples para gerenciar livros, usuários e empréstimos em uma biblioteca universitária.

## Funcionalidades

- CRUD de Livros (título, autor, ano, ISBN, disponibilidade)
- CRUD de Usuários (nome, matrícula, curso, e-mail)
- Registro de Empréstimos/Devoluções

## Tecnologias

- Backend: Java 17, Spring Boot, JPA, PostgreSQL
- Frontend: HTML único com Tailwind CSS e JavaScript
- Testes: JUnit, Mockito

## Como Executar

1. Instale Java 17 e PostgreSQL.
2. Configure o banco de dados PostgreSQL com as credenciais em `application.properties`.
3. Execute `mvnw.cmd spring-boot:run` para iniciar o backend.
4. Abra `http://localhost:8080` no navegador para o frontend.

## Testes

Para executar os testes unitários:
- `mvnw.cmd test` (requer Maven wrapper configurado)

Para testes manuais, consulte `TEST_FRONTEND.md`.

## Estrutura do Projeto

- `src/main/java/com/biblioteca/model`: Modelos de dados
- `src/main/java/com/biblioteca/repository`: Interfaces de repositório
- `src/main/java/com/biblioteca/service`: Lógica de negócio
- `src/main/java/com/biblioteca/controller`: Controladores REST
- `src/main/resources/static`: Frontend
- `src/test`: Testes unitários
