package Interfaces.Simulacao;

import Items.Item;
import Mapa.Divisao;
import Personagens.Inimigo;

public interface SimulacaoInterface {

    public void inimigoDead(Inimigo inimigo, Divisao divisao);

    public void updatePercursoToCruz(Divisao divisao);

    public void addCollectedItem(Item item, Divisao divisao);

    public void relatorioSimulacao();
}
