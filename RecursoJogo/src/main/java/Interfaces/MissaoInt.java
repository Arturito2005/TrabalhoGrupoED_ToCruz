package Interfaces;

/**
 * Classe abstrata que representa cada personagem no jogo
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public interface MissaoInt {

    /**
     * Visualiza as simulações disponíveis para a missão.
     * Este método exibe informações ou resultados de simulações realizadas pelo o ToCruz
     * ordenadas pelo os pontos de vida que o mesmo terminou a missão.
     */
    public void viewSimulacoes();

    /**
     * Inicia o modo manual da missão.
     * No modo manual, o jogador controla diretamente as ações ou decisões durante a missão.
     */
    public void modoManual();

    /**
     * Inicia o modo automático da missão.
     * No modo automático, é indicado ao To Cruz qual o melhor caminho que ele deve realizar
     * para chegar ao Alvo e se possível o caminho para voltar com vida.
     */
    public void modoAutomatico();

    /**
     * Executa a missão automaticamente até o final.
     * Este método realiza todas as etapas da missão de forma automatizada, simulando o jogo completo.
     */
    public void jogoAutomatico();
}
