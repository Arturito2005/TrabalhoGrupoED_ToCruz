Neste repostiorio está conitdo o enunciado e todos os repositorios para o desenvolvimento do meu trabalho final da UC de Estruturas de Dados (ED). Este trabalho consiste num jogo de turnos, em que o objeitvo do meu é o nosso personagem principal o To Cruz, consiga chegar até há sala com o seu objetivo (refens, objetos precisos, etc) e sair do edificio com o maior pontos de vida possível e no menor caminho possível. 
O jogo possui três modos de jogo:
- Manual, no qual o jogador tem a possibilidade de poder jogar com o ToCruz.
- Automatico, no qual o jogo é executado automaticamente, sendo possivel no final da simulação observar todo o percurso que o ToCruz fez.
- Jogo Automatico, que é parecido ao modo automatico, mas nele será mostrado o caminho mais curto tendo em consideração que os inimigos do ToCruz, nunca mudam de sala.

O projeto está dividido em 3 JARs, que são:
- Menu, onde este é o jar principal que permite ao utilizador jogar o nosso jogo.
- RecursoJogo, este jar possui todas as classes necessárias para que o utilizador consiga jogar o jogo, como todos os itens e personagens do jogo, importação do mapa do jogo, etc.
- Api_EstruturasDados, este jar possui todas as estruturas de dados utilizadas no jogo e também todas as lecionadas na UC.

Nota: nos grafos a pasta "GraphPesosNosVertices", é uma estrutura nova que estou a desenvolver com o intuito de otimizar o jogo, pois atualmente o peso do grafo, que representa o dano que o ToCruz vai receber ao entrar na sala está armazenada nas arestas sendo que teria mais logica o peso ficar nos vertices visto que é onde os inimigos estão. Isso tornaria o jogo mais otimizado uma vez que uma sala está pode estar ligada a mais de 1 sala e caso queira mover o inimigo eu tenho de passar o valor de todas essas arestas para 0 e atualizar o valor das arestas da sala que o inimigo se dirigiu para o valor do dano do mesmo, sendo que caso o valor estivesse na aresta eu só teria de mudar para zero o valor da aresta que ele se encontra e atualizar a aresta que ele foi para o dano dele, coisa que dá para fazer em 2 operações, O(2), enquanto que atualmente esse processo é um O(n), pois depende de quantas arestas o vertice atual e novo estão ligados, sendo que isso também facilitará o calculo realizado pelo Dijkstra.

Neste projeto todo o codigo está devidamente comentado possuindo em cada repositório um JavaDoc, de maneira a facilitar a leitura da documentação.

Para a realização do importar e do exportar utilizamos o jar do json-simple, que está disponível, no seguinte site: https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple

Para fazer a seleção de forma aleatoria da nova divisão do inimigo, quando o mesmo se movimentar, utilizamos o "java.util.random"

Para o diagrama de Dijkstra fomos buscar o algoritmo ao seguintes sites: https://www.devglan.com/datastructure/dijkstra-algorithm-java e https://www.w3schools.com/dsa/dsa_algo_graphs_dijkstra.php 
e dos seguintes apontamentos fornecidos pelo professor: https://moodle2.estg.ipp.pt/pluginfile.php/18311/mod_resource/content/0/Apontamentos_ED_CAP15.pdf e https://moodle2.estg.ipp.pt/pluginfile.php/17922/mod_resource/content/0/Apontamentos_ED_CAP14.pdf

Linguagem: Java
IDE: InteliJ IDEA
