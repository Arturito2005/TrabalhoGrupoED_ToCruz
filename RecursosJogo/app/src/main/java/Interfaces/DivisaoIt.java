package Interfaces;

/**
 * Interface para definir as condições que podem acontecer dentro de uma divisao do jogo.
 *
 * @author Francisco
 * @version 1.0
 */
public interface DivisaoIt {

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

    public void drawnDivisao();
}
