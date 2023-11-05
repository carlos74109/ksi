#Documentação 

Models:

Entidade Filme com 5 atributos:

Id: ID do filme
Title: Título do filme
Director: Nome do diretor do filme
Released: Data de lançamento do filme
Poster: Link para o pôster retornado pela API

Controller:

FilmeController:

Atributos:

chave: Esta é a chave de acesso para a API.

capaInterrogacao: Link para uma imagem com um ponto de interrogação, que é inserida caso o valor de pôster seja "N/A".

gson: Biblioteca utilizada para converter o formato JSON em objeto.

Métodos:

GetMapping: http://localhost:8080/  
paginaHome: Exibe a tela inicial do sistema.

GetMapping: http://localhost:8080/tituloFilme  
exibirDadosFilmes: Este método faz uma consulta na API, utilizando o título do filme passado pelo usuário como parâmetro de pesquisa. Os valores dessa pesquisa são exibidos na página "FilmePesquisado".

PostMapping: http://localhost:8080/favoritar
favoritarFilme: Salva os filmes favoritados pelo usuário no banco de dados. Uma condição foi implementada para que filmes com títulos iguais não possam ser favoritados mais de uma vez.

GetMapping: http://localhost:8080/favorito
filmesFavoritos: Exibe todos os filmes que foram favoritados pelo usuário.

GetMapping: http://localhost:8080/filme/editar/{id}
paginaEditarFilme: Este método leva o usuário para uma página onde ele pode editar livremente as informações do filme.

@PostMapping: http://localhost:8080/filme/editar/{id} 
editarFilme: Salva as alterações feitas pelo usuário.

GetMapping:  http://localhost:8080/filme/delete/{id}  
removerFilme: Remove o filme da lista de favoritos do usuário.

consultarFilme: Consulta o filme via a API OMDBAPI. Neste método, existem algumas condições onde, caso alguns atributos do filme venham como "N/A", eles são substituídos por textos mais legíveis.

receberFilme: Este método serve para atualizar os valores dos dados atuais com novos valores inseridos pelo usuário. É chamado por outro método, "editarFilme".


