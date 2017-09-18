# Simulador para Mapa de Coleta de Resíduos aplicado na cidade de Maringá.

Simulador criado com objetivo de aplicar o uso de grafos para geração de rotas para coleta de resíduos hospitares aplicado no mapa da cidade Maringá.

Para modelagem do problema foi considerado um grafo completo com arestas ponderadas. Cada vértice do grafo corresponde a um posto de saúde e o caminho a ser percorrido entre cada vértice, uma aresta. Desta forma, selecionando dois postos de saúde de forma aleatória e denominando estes de vértice A e B, o caminho a ser percorrido entre eles será a aresta e a distância em metros, a ponderação.
 
Para criação das arestas e suas ponderações, foi utilizada uma API de geolocalização disponibilizada pelo Google, com isso, foi possível a geração do grafo de forma dinâmica além da possibilidade de trabalhar com dados mais próximos de um cenário real. A geração do grafo ocorre a cada novo vértice incluído acionado a API que irá buscar informações sobre cada vértice já existente e o novo vértice. Neste cenário, o vértice que já se encontra no grafo é tido como vértice origem e o novo como vértice destino. Sendo assim, para cada vértice origem é criada uma aresta que o liga ao destino com sua ponderação, sendo obtida através da API. Cabe salientar que o grafo nunca estará vazio, pois, ao menos, um vértice origem que representa o ponto de partida do caminhão de coleta e um vértice destino que representa o aterro devem existir.
 
A implementação do simulador considera dois algoritmos onde cara um gera um tipo de resultado:
 
Algoritmo Prim: Árvore geradora mínima que representa a melhor rota de visita para todos postos de saúde.
Algoritmo Dijkstra: Caminho mínimo que representa o menor caminho entre o ponto de saída do caminhão de coleta e o aterro.

Para algoritmo Dijkstra foi inserida uma adaptação que removerá do grafo os vértices selecionados em sua ultima execução, desta forma, cada execução deverá obter resultados diferentes até que restem apenas o dois vértices fixos.
