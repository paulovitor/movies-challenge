# Challenge

## Feature: Listar filmes lançamentos

Para visualizar todos os últimos filmes lançados no cinema mundial

Como usuário guest

Eu quero ter uma feature para listar os filmes que são lançamentos

* Cenário: Listar últimos lançamentos de filmes ordenados

Dado que usuário selecionou o filtro lançamentos

Quando aciono ação de buscar

Então devo receber uma lista dos 10 últimos filmes que são lançamentos

E estejem ordenados por (nome e data de lançamento)

---

## Feature: Detalhes de um filme

Para visualizar todos os detalhes do filme escolhido

Como usuário guest

Eu Quero ter uma feature para recuperar informações de um filme

* Cenário: Recuperar informações de filme

Dado que existe um filme de id: 1

Quando aciono ação de buscar por id

Então devo receber todas as informações do filme

* Cenário: Filme não existe

Dado que não existe um filme de id: 2

Quando aciono ação de buscar por id

Então devo receber um erro informando que filme não existe
