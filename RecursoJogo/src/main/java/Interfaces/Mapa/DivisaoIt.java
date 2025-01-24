package Interfaces.Mapa;

import Exceptions.ContainElementException;
import Items.Item;
import Personagens.Inimigo;

/**
 * Interface para definir as condições que podem acontecer dentro de uma divisão do jogo.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 *
 * @version 1.0
 */
public interface DivisaoIt {

    /**
     * Adiciona um inimigo à divisão.
     *
     * @param inimigo O inimigo a ser adicionado.
     * @throws NullPointerException Se o inimigo fornecido for nulo.
     */
    public void addInimigo(Inimigo inimigo) throws NullPointerException;

    /**
     * Remove um inimigo da divisão.
     *
     * @param inimigo O inimigo a ser removido.
     * @return O inimigo removido.
     */
    public Inimigo removeInimigo(Inimigo inimigo);

    /**
     * Faz o desenho da divisão na consola
     *
     * @return Uma string com o desenho da divisão
     * */
    public String drawnDivisao();

    public boolean containInimigo(Inimigo inimigo);

    public void addItem(Item item) throws NullPointerException, ContainElementException;

    public boolean containItem(Item item);

    public Item removeItem(Item item);
}
