package Interfaces;

import Mapa.Edificio;
import Missoes.Simulacoes;

/**
 * Interface contém os 2 modos de jogo da missão.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveria
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public interface SimulacoesInt {

    /**
     * Ativa o modo manual da missão.
     */
    public Simulacoes modoManual();

    /**
     * Sugere a melhor entrada ao To Cruz e o caminho que deve realizar para chegar ao alvo e se
     * for possível sair do edificio com vida também mostra o caminho mais curto para sair do edificio.
     * */
    public void modoAutomatico();

    /**
     * O jogo é jogado de forma automatico.
     */
    public Simulacoes jogoAutomatico();
}
