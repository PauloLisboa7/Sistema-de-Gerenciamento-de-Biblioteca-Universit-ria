# Testes do Frontend - Sistema de Gerenciamento de Biblioteca Universitária

## Pré-requisitos
- O backend deve estar rodando em http://localhost:8080
- PostgreSQL deve estar configurado e rodando
- Abra o navegador em http://localhost:8080

## Testes para Livros
1. **Listar Livros**: Verificar se a lista de livros é exibida corretamente.
2. **Adicionar Livro**: Clicar em "Adicionar Livro", preencher formulário (título, autor, ano, ISBN, disponibilidade), salvar. Verificar se aparece na lista.
3. **Editar Livro**: Clicar em "Editar" em um livro, alterar dados, salvar. Verificar atualização.
4. **Remover Livro**: Clicar em "Remover", confirmar. Verificar remoção da lista.
5. **Casos Extremos**:
   - Tentar adicionar livro sem título: Deve mostrar erro ou não salvar.
   - ISBN duplicado: Deve impedir ou alertar.
   - Ano inválido (não numérico): Deve validar.

## Testes para Usuários
1. **Listar Usuários**: Verificar se a lista de usuários é exibida.
2. **Adicionar Usuário**: Clicar em "Adicionar Usuário", preencher (nome, matrícula, curso, email), salvar. Verificar na lista.
3. **Editar Usuário**: Editar dados, salvar, verificar.
4. **Remover Usuário**: Remover, verificar.
5. **Casos Extremos**:
   - Email inválido: Deve validar formato.
   - Matrícula duplicada: Impedir.
   - Campos obrigatórios vazios: Não salvar.

## Testes para Empréstimos
1. **Listar Empréstimos**: Verificar lista com usuário e livro associados.
2. **Registrar Empréstimo**: Selecionar usuário e livro disponíveis, registrar. Verificar na lista e disponibilidade do livro muda.
3. **Devolver Livro**: Em um empréstimo, marcar devolução, verificar data e disponibilidade.
4. **Editar Empréstimo**: Alterar datas, salvar.
5. **Remover Empréstimo**: Remover, verificar.
6. **Casos Extremos**:
   - Empréstimo de livro indisponível: Deve impedir.
   - Usuário inexistente: Selecionar ID inválido, erro.
   - Data devolução antes de empréstimo: Validar.
   - Tentativa de empréstimo sem usuário/livro: Não salvar.

## Testes Gerais
- **Navegação**: Verificar se as seções (Livros, Usuários, Empréstimos) funcionam.
- **Responsividade**: Testar em diferentes tamanhos de tela.
- **Erros de Rede**: Simular desconexão, verificar mensagens de erro.
- **Performance**: Com muitos registros, verificar carregamento rápido.
