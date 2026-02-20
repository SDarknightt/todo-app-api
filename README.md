# Todo App API REST - Spring Boot

Projeto desenvolvido com o objetivo de aplicar conhecimentos em desenvolvimento de APIs REST seguindo as boas práticas da engenharia de software.

A API oferece suporte para:
- Registro e autenticação de usuários; 
- Gerenciamento de tarefas por usuário; 
- Controle de acesso com níveis de permissão.

## Funcionalidades

- Registro de usuário;
- Login com autenticação via JWT;
- Criação de múltiplas tarefas por usuário;
- Alteração de status da tarefa;
- Controle de acesso com dois níveis: USER e ADMIN.

## Conceitos aplicados

- Spring Boot
    - Inicialização simplificada do projeto e configuração automática.
- Spring Security
    - Autenticação via usuário e senha armazenados no banco de dados.
    - Senhas criptografadas com BCrypt.
    - Autorização baseada em token JWT.
- Tratamento de Exceções (Em andamento)
    - Implementação de exceções personalizadas. 
    - Mensagens claras para facilitar a identificação de erros de negócio.
- Banco de Dados em Memória
    - Utilização do banco relacional H2 para simplificar a execução e testes da API.
- DTO (Data Transfer Object)
    - Padronização da entrada e saída de dados. 
    - Separação entre camada de domínio e exposição da API.
- Arquitetura em Camadas
    - Controller – Responsável por lidar com as requisições HTTP.
    - Service – Contém as regras de negócio. 
    - Repository – Responsável pela comunicação com o banco de dados.
- Containerização com Docker
  - Garante que a aplicação rode de forma consistente em qualquer ambiente.

## Como executar o projeto (localmente)

### Pré-requisitos
- Java JDK 21+
- Maven **ou** Maven Wrapper (`mvnw`)

### Executando com Maven Wrapper
Na raiz do projeto, execute:

```bash
./mvnw spring-boot:run
```

### Executando com Docker

Na raiz do projeto, execute para gerar o **jar** na pasta **target**:
```bash
./mvnw clean package 
```

Gere uma imagem docker:
```bash
docker build -t todo-api:1.0 . 
```

Rode o container a partir da imagem gerada:
```bash
docker run -d --name todo-api -p 8080:8080 todo-api:1.0
```

A aplicação será iniciada em:
```bash
 http://localhost:8080
```

### Autenticação
Após realizar registro e o login, o sistema retornará um token JWT.
Esse token deve ser enviado no header das requisições protegidas:
```bash
 Authorization: Bearer {token_aqui}
```

### Melhorias Futuras
- Implementar tratamento global de exceções;
- Documentação da API com Swagger;
- Implementação de testes unitários e de integração;
- Integração com banco de dados persistente (PostgreSQL).