# Sistema de Gerenciamento de Biblioteca Universitária

Este projeto é um sistema simples para gerenciar o cadastro de livros, usuários e empréstimos em uma biblioteca universitária.

## Tecnologias Utilizadas

- Backend: Java com Spring Boot (POO, camadas controlador, serviço, repositório, modelo)
- Banco de Dados: PostgreSQL
- Frontend: HTML único com Tailwind CSS e JavaScript (fetch API)
- Versionamento: GitHub

## Configuração do Banco de Dados

1. Instale e configure o PostgreSQL (ex: pgAdmin).
2. Crie um banco de dados chamado `biblioteca`.
3. Atualize o arquivo `src/main/resources/application.properties` com seu usuário e senha do PostgreSQL.

## Como Rodar o Projeto

1. Certifique-se de ter o Java 17 e Maven instalados.
2. No terminal, execute:
   ```
   ./mvnw spring-boot:run
   ```
3. O backend estará disponível em `http://localhost:8080`.
4. Abra o arquivo `src/main/resources/static/index.html` no navegador para acessar o frontend.

## Funcionalidades

- CRUD de Livros (título, autor, ano, ISBN, disponibilidade)
- CRUD de Usuários (nome, matrícula, curso, e-mail)
- Registro de Empréstimos e Devoluções (associar usuário ao livro emprestado)
- Visualização dos livros cadastrados com botões para adicionar, editar e remover

## Testes

- Testes unitários para os controladores estão disponíveis na pasta `src/test/java/com/biblioteca/controller`.
- Para rodar os testes:
  ```
  ./mvnw test
  ```

## Versionamento

- Cada atualização do projeto será registrada com um commit claro no GitHub.

## Contato

Para dúvidas ou sugestões, entre em contato com Paulo Lisboa.
