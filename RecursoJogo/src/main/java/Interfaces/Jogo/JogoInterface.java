package Interfaces.Jogo;

import Items.Item;
import Personagens.Inimigo;

public interface JogoInterface extends InimigoIteracoes, ToCruzInteracoes{

    public void getAlvo();

    public void relatorioMissao();

    public void inimigoDead(Inimigo inimigo);

    public void collectedItem(Item item);
}
