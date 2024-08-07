﻿# Catálogo de Livros

Este projeto é uma API para gerenciar um catálogo de livros e seus autores. A API permite buscar livros pela API do Gutendex, salvar livros e autores no banco de dados PostgreSQL, e fornecer estatísticas sobre os livros e autores.

## Funcionalidades

- Buscar livros pelo título usando a API do Gutendex
- Listar todos os livros armazenados
- Adicionar novos livros e autores
- Remover livros do catálogo
- Atualizar informações de livros
- Exibir estatísticas sobre a quantidade de livros em determinados idiomas
- Listar autores que estavam vivos em um determinado ano

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Apache HttpClient
- Jackson

## Configuração do Ambiente

### Pré-requisitos

- JDK 
- Maven
- PostgreSQL

### Configuração do Banco de Dados

1. Instale o PostgreSQL e crie um banco de dados chamado `bookcatalog`.
2. Configure as seguintes variáveis de ambiente no seu sistema:

```bash
export DATABASE_URL=jdbc:postgresql://localhost:5432/bookcatalog
export DATABASE_USERNAME=your_username
export DATABASE_PASSWORD=your_password
