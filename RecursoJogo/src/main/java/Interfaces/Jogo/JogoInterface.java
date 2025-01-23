package Interfaces.Jogo;

import Items.Item;
import Mapa.Divisao;
import Personagens.Inimigo;

public interface JogoInterface extends ToCruzInteracoes{

    public void inimigoDead(Inimigo inimigo, Divisao divisao);

    public void updatePercursoToCruz(Divisao divisao);

    public void addCollectedItem(Item item);

    public void relatorioMissao();

    public void sugestaoCaminhoToCruzKitEAlvo(Divisao div_to);
}
