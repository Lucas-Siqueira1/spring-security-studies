# Spring Security Studies

Repositório dedicado ao aprofundamento técnico no ecossistema de segurança do **Spring Boot 3**. O objetivo aqui não é apenas implementar autenticação, mas entender a arquitetura interna (under the hood) e os fluxos de processamento do framework.

## Sobre o Projeto

Este projeto consiste em uma API de estudos que simula o backend de um sistema financeiro, onde implemento:
- Autenticação Stateless com **JWT (JSON Web Token)**.
- Controle de acesso baseado em **Roles** (ADMIN, CLIENT).
- Banco de dados em memória **H2** para testes rápidos.
- Integração com **Spring Data JPA**.

---

## Principal Aprendizado

Um dos maiores aprendizados registrados neste repositório surgiu de um bug persistente durante o desenvolvimento.

### O Problema
Ao tentar acessar um endpoint público (`/auth/register`), configurado com `.permitAll()`, a API retornava inesperadamente um erro **403 Forbidden**.

### A Causa Raiz
Descobri que a causa não era a permissão da rota, mas sim a **ordem dos filtros na SecurityFilterChain**:
1. O `SecurityFilter` (meu filtro customizado) rodava antes dos filtros nativos de autorização do Spring.
2. Ao enviar um token antigo/inválido após resetar o banco H2, o filtro tentava buscar o usuário e recebia `null`.
3. Sem tratamento de exceção no filtro, o sistema lançava um `NullPointerException`.
4. O Spring Security interceptava o erro e, por não encontrar um tratamento adequado na cadeia de filtros naquele ponto, mascarava a falha como um **403 Forbidden**.

### A Solução
Implementei um tratamento de exceções robusto dentro do `doFilterInternal`, garantindo que falhas de busca ou tokens malformados não interrompam a cadeia de forma silenciosa, mas sim permitam que o fluxo siga para as regras de segurança definidas na configuração.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security**
- **Spring Data JPA**
- **H2 Database**
- **JWT (Auth0 library)**
- **Maven**

---

## 📂 Estrutura Principal

- `src/main/java/.../security/SecurityFilter.java`: Filtro customizado onde apliquei a lógica de interceptação de tokens e tratamento de exceções.
- `src/main/java/.../security/SecurityConfig.java`: Configuração da `SecurityFilterChain`, definições de rotas públicas e privadas.
- `src/main/java/.../services/TokenService.java`: Lógica de geração e validação de tokens JWT.

---

## 🔧 Como Executar

1. Clone o repositório:
   ```bash
   git clone [https://github.com/Lucas-Siqueira1/spring-security-studies.git](https://github.com/Lucas-Siqueira1/spring-security-studies.git)

2. Certifique-se de ter o Maven e o JDK 17+ instalados.

3. Execute a aplicação:
    ```bash
    ./mvnw spring-boot:run

4. A API estará disponível em http://localhost:8080. O console do H2 pode ser acessado em /h2-console.

Feito com Java por Lucas Siqueira