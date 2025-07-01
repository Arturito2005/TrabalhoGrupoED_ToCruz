package Interfaces.Cenarios;

import Mapa.Divisao;
import Personagens.Inimigo;
import Personagens.Personagem;

public interface CenarioInimigosInterface {

    public Divisao andar(Inimigo inimigo, Divisao divisao_atual);
}
