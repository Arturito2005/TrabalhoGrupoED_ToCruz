package Interfaces.Jogo;

import Items.Item;
import Mapa.Divisao;
import Personagens.Inimigo;

public interface JogoInterface extends InimigoIteracoes, ToCruzInteracoes{

    public void getAlvo();

    public void relatorioMissao();

    public void inimigoDead(Inimigo inimigo);

    public void usarItemDivisao(Item item, Divisao divisao);

    public void guardarItemMochila(Item item, Divisao divisao);
}
