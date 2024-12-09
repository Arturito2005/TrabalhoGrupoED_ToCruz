package Interfaces;

/**
 * Interface contém os 2 modos de jogo da missão.
 *
 * @author Artur
 * @version 1.0
 */
public interface MissaoInt {

    /**
     * Ativa o modo manual da missão.
     */
    public void modoManual();

    /**
     * Sugere a melhor entrada ao To Cruz e o caminho que deve realizar para chegar ao alvo e se
     * for possível sair do edificio com vida também mostra o caminho mais curto para sair do edificio.
     * */
    public void modoAutomatico();

    /**
     * O jogo é jogado de forma automatico.
     */
    public void jogoAutomatico();
}
