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

## Troubleshooting

### Erro "-Dmaven.multiModuleProjectDirectory system property is not set"

Este erro geralmente ocorre quando um comando Maven é executado fora do diretório raiz do projeto que contém o arquivo pom.xml, ou quando o ambiente de execução (como IDE ou terminal) está mal configurado para projetos multimódulo. Isso indica que o Maven não consegue identificar corretamente o diretório do projeto principal, comprometendo a resolução de módulos e dependências.

#### Causas Comuns
- Executar comandos Maven em subpastas do projeto (fora do diretório onde está o pom.xml principal).
- Má configuração do Maven na IDE (como Eclipse ou IntelliJ), especialmente em projetos importados.

#### Como Corrigir
- Execute os comandos Maven sempre no diretório que contém o pom.xml raiz do projeto multimódulo.
- Em IDEs, reimporte o projeto como um projeto Maven, utilizando as opções para atualizar/recarregar configurações Maven, como “Maven > Atualizar Projeto” no Eclipse.
- Caso execute via linha de comando, use:
  ```
  cd /caminho/para/seu/projeto-principal
  mvn clean install
  ```
- Em alguns casos, definir explicitamente a propriedade pode ajudar, mas normalmente a raiz correta resolve o problema:
  ```
  mvn clean install -Dmaven.multiModuleProjectDirectory=$PWD
  ```
  (No Windows, tente usar %cd% em vez de $PWD.)
- Verifique o arquivo settings.xml do Maven (na pasta .m2) esteja corretamente configurado, especialmente se houver proxies, repositórios ou perfis customizados.
- Em Ambientes de Desenvolvimento: No Eclipse ou IntelliJ, revise se o projeto está corretamente importado como projeto Maven, e se a configuração do JDK corresponde ao projeto. Se a mensagem aparece em um terminal da IDE, feche e abra o terminal dentro do contexto do projeto principal.

Assim, para a maioria dos casos, refaça o comando sempre do diretório raiz do projeto e verifique as configurações do projeto na IDE.

## Versionamento

- Cada atualização do projeto será registrada com um commit claro no GitHub.

## Contato

Para dúvidas ou sugestões, entre em contato com Paulo Lisboa.
