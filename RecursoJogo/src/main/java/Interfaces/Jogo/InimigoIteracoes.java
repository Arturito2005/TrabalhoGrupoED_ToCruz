package Interfaces.Jogo;

import Mapa.Divisao;
import Personagens.Inimigo;

public interface InimigoIteracoes {

    public void attackInimigo(Inimigo inimigo);

    public Divisao moverInimigo(Divisao divisao_atual, Inimigo inimigo);
}
