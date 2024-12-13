package Interfaces;

/**
 * Interface que representa uma missão
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public interface MissaoInt {

    /**
     * O metodo permite visualizar as simulações disponíveis para a missão.
     * Exibe informações ou resultados de simulações realizadas pelo ToCruz
     * ordenadas pelos pontos de vida restantes.
     */
    public void viewSimulacoes();

    /**
     * Inicia o modo manual da missão.
     * No modo manual, o jogador controla diretamente as ações ou decisões durante a missão.
     */
    public void modoManual();

    /**
     * Inicia o modo automático da missão.
     * No modo automático, é indicado ao To Cruz qual é o melhor caminho que ele deve realizar
     * para chegar ao Alvo e se possível o caminho para sair do edifício com vida.
     */
    public void modoAutomatico();

    /**
     * Executa a missão automaticamente até ao final.
     * Este metodo realiza todas as etapas da missão de forma automatizada, simulando o jogo completo.
     */
    public void jogoAutomatico();
}
