package Interfaces;

import Exceptions.EmptyCollectionException;
import Personagens.Inimigo;

/**
 * Interface para definir as condições que podem acontecer dentro de uma divisao do jogo.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveria
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
     * @throws EmptyCollectionException Se não houver inimigos na divisão.
     * @throws NullPointerException Se o inimigo fornecido for nulo.
     */
    public Inimigo removeInimigo(Inimigo inimigo);
    /**
     * Verifica se há um confronto ativo na divisão.
     *
     * @return {@code true} se houver confronto, caso contrário {@code false}.
     */
    public boolean haveConfronto();

    /**
     * Verifica se há inimigos presentes na divisão.
     *
     * @return {@code true} se houver inimigos, caso contrário {@code false}.
     */
    public boolean haveInimigo();

    /**
     * Verifica se o ToCruz está na mesma divisão do alvo.
     *
     * @return {@code true} se o To Cruz está na mesma divisão que o alvo, caso contrário {@code false}.
     */
    public boolean isToCruzInDivisaoAlvo();

    /**
     * Verifica se o ToCruz está em alguma entrada ou saida.
     *
     * @return {@code true} se o To Cruz está numa saida ou entrada, caso contrário {@code false}.
     */
    public boolean isToCruzInExit();

    /**
     * Faz o desenho na consola da divisão
     * */
    public void drawnDivisao();
}
