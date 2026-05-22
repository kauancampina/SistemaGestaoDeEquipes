# Sistema de Gestao de Equipe

Projeto Java com interface Swing.

## Requisitos

- JDK 17 ou superior
- IntelliJ IDEA ou Maven

## Como abrir no IntelliJ IDEA

1. Clone o repositorio.
2. Abra a pasta do projeto no IntelliJ.
3. Configure o Project SDK como JDK 17 ou superior.
4. Aguarde o IntelliJ importar o Maven pelo arquivo `pom.xml`.
5. No menu de execucao, selecione `Rodar Sistema` e clique em Run.

## Como compilar com Maven

```bash
mvn clean compile
```

## Como executar com Maven

```bash
mvn exec:java
```

O arquivo `pom.xml` fixa a compilacao em Java 17 para evitar incompatibilidade entre maquinas com JDKs diferentes.
