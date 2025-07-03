# Projeto ED
## English
### 📌 Introduction
This repository contains the assignment statement and all the repositories for the development of my final project in the Data Structures (ED) course. This work consists of a turn‑based game in which the objective is for our main character, To Cruz, to reach the room with his goal (hostages, required items, etc.) and exit the building with as many hit points as possible while taking the shortest path.  
The game has three modes:
- **Manual**, where the player can control To Cruz.  
- **Automatic**, where the game runs by itself, and at the end of the simulation you can review the entire path that To Cruz took.  
- **Shortest‑Path Automatic**, similar to Automatic mode, but it shows the shortest path assuming To Cruz’s enemies never change rooms.  

---
### 📦 Project JARs
The project is split into three JARs:
- **Menu**: the main JAR that allows the user to play our game.  
- **GameResources**: contains all the classes needed for the game (items, characters, map import, etc.).  
- **Api_DataStructures**: contains all the data structures used in the game and those taught in the course.  

---
### 📝 Notes
In the graphs folder, “GraphVertexWeights” is a new structure I’m developing to optimize the game, because currently the graph’s weight—which represents the damage To Cruz receives when entering a room—is stored on edges. It would make more sense to store the weight on vertices, since that’s where the enemies are. This would optimize the game: when moving an enemy, I’d only need to set the weight of the source edge to 0 and update the target edge to the enemy’s damage (2 operations, O(2)), whereas now I must traverse all edges connected to the old and new vertex (O(n)). It also simplifies Dijkstra’s calculations.

All code in this project is fully commented, and each repository includes JavaDoc to facilitate documentation reading.

---
### 🛠️ Languages and Tools
#### 💻 Languages
- **Java**: Object‑oriented programming language widely used for enterprise applications, mobile (Android), and distributed systems.

#### 🧰 Tools
- **IntelliJ IDEA**: Powerful IDE for Java (and other languages) with advanced refactoring, debugging, and build/version‑control integration.  
- **JavaDoc**: JDK‑native tool to generate HTML documentation from special comments in Java source code, easing API maintenance and usage.  

---
### 📑 References
- For the Dijkstra diagram we referenced the following sites:  
  - https://www.devglan.com/datastructure/dijkstra-algorithm-java  
  - https://www.w3schools.com/dsa/dsa_algo_graphs_dijkstra.php  
  - Lecture notes:  
    - https://moodle2.estg.ipp.pt/pluginfile.php/18311/mod_resource/content/0/Apontamentos_ED_CAP15.pdf  
    - https://moodle2.estg.ipp.pt/pluginfile.php/17922/mod_resource/content/0/Apontamentos_ED_CAP14.pdf  
- For import/export functionality we used the json‑simple JAR from:  
  - https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple  
- To randomly select the enemy’s new room when it moves, we used `java.util.Random`.
  
## Português
### 📌 Introdução
Neste repostiorio está conitdo o enunciado e todos os repositorios para o desenvolvimento do meu trabalho final da UC de Estruturas de Dados (ED). Este trabalho consiste num jogo de turnos, em que o objeitvo do meu é o nosso personagem principal o To Cruz, consiga chegar até há sala com o seu objetivo (refens, objetos precisos, etc) e sair do edificio com o maior pontos de vida possível e no menor caminho possível. 
O jogo possui três modos de jogo:
- **Manual**, no qual o jogador tem a possibilidade de poder jogar com o ToCruz.
- **Automatico**, no qual o jogo é executado automaticamente, sendo possivel no final da simulação observar todo o percurso que o ToCruz fez.
- **Jogo Automatico**, que é parecido ao modo automatico, mas nele será mostrado o caminho mais curto tendo em consideração que os inimigos do ToCruz, nunca mudam de sala.

---
### 📦 Jars do Projeto
O projeto está dividido em 3 JARs, que são:
- **Menu**, onde este é o jar principal que permite ao utilizador jogar o nosso jogo.
- **RecursoJogo**, este jar possui todas as classes necessárias para que o utilizador consiga jogar o jogo, como todos os itens e personagens do jogo, importação do mapa do jogo, etc.
- **Api_EstruturasDados**, este jar possui todas as estruturas de dados utilizadas no jogo e também todas as lecionadas na UC.

---
### 📝 Notas
Nos grafos a pasta "GraphPesosNosVertices", é uma estrutura nova que estou a desenvolver com o intuito de otimizar o jogo, pois atualmente o peso do grafo, que representa o dano que o ToCruz vai receber ao entrar na sala está armazenada nas arestas sendo que teria mais logica o peso ficar nos vertices visto que é onde os inimigos estão. Isso tornaria o jogo mais otimizado uma vez que uma sala está pode estar ligada a mais de 1 sala e caso queira mover o inimigo eu tenho de passar o valor de todas essas arestas para 0 e atualizar o valor das arestas da sala que o inimigo se dirigiu para o valor do dano do mesmo, sendo que caso o valor estivesse na aresta eu só teria de mudar para zero o valor da aresta que ele se encontra e atualizar a aresta que ele foi para o dano dele, coisa que dá para fazer em 2 operações, O(2), enquanto que atualmente esse processo é um O(n), pois depende de quantas arestas o vertice atual e novo estão ligados, sendo que isso também facilitará o calculo realizado pelo Dijkstra.

Neste projeto todo o codigo está devidamente comentado possuindo em cada repositório um JavaDoc, de maneira a facilitar a leitura da documentação.

---
### 🛠️ Lingaugens e Ferramentas
#### 💻 Linguagens
- **Java**: Linguagem de programação orientada a objetos, amplamente usada para desenvolver aplicações corporativas, mobile (Android) e sistemas distribuídos.

#### 🧰 Ferramentas
- **IntelliJ IDEA**: IDE poderosa para Java (e outras linguagens), com recursos avançados de refatoração, depuração e integração com sistemas de build e controle de versão.  
- **JavaDoc**: Ferramenta nativa do JDK para gerar documentação HTML a partir de comentários especiais no código-fonte Java, facilitando a manutenção e o uso de APIs.  

---
### 📑 Referencias
- Para o diagrama de Dijkstra fomos buscar o algoritmo ao seguintes sites:
 - https://www.devglan.com/datastructure/dijkstra-algorithm-java https://www.w3schools.com/dsa/dsa_algo_graphs_dijkstra.php
 - Apontamentos do professor:
  - https://moodle2.estg.ipp.pt/pluginfile.php/18311/mod_resource/content/0/Apontamentos_ED_CAP15.pdf
  - https://moodle2.estg.ipp.pt/pluginfile.php/17922/mod_resource/content/0/Apontamentos_ED_CAP14.pdf
- Para a realização do importar e do exportar utilizamos o jar do json-simple, que está disponível, no seguinte site: https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple
- Para fazer a seleção de forma aleatoria da nova divisão do inimigo, quando o mesmo se movimentar, utilizamos o `java.util.Random`
