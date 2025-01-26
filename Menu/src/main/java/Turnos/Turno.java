package Turnos;

import Cenarios.Divisao.CenariosDivisao;
import Cenarios.Personagens.CenariosPersonagem;
import Interfaces.Turno.TurnoInt;
import Mapa.Divisao;

/**
 * Classe abstrata que representa um turno genérico no jogo.
 * As subclasses desta classe devem implementar o comportamento específico para os turnos
 * das personagens ou inimigos, incluindo a execução das ações durante o turno, como
 * movimentação, ataques e interações com itens.
 *
 * A classe gere as instâncias de cenários de personagens e cenários de divisões,
 * fornecendo uma estrutura comum para os turnos no jogo.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public abstract class Turno implements TurnoInt {

    /** A instância que representa o cenário dos personagens (como o jogador ou inimigos) */
    private CenariosPersonagem cenarioPersonagens;

    /** A instância que gere o cenário das divisões do mapa do jogo */
    private CenariosDivisao cenariosDivisao;

    /**
     * Construtor que inicializa o turno com os cenários de personagens e divisões.
     *
     * @param cenarioPersonagens O cenário que representa as personagens no jogo (personagem do jogador ou inimigos).
     * @param cenariosDivisao O cenário que gere as divisões do mapa do jogo.
     */
    public Turno(CenariosPersonagem cenarioPersonagens, CenariosDivisao cenariosDivisao) {
        this.cenarioPersonagens = cenarioPersonagens;
        this.cenariosDivisao = cenariosDivisao;
    }

    /**
     * Retorna o cenário de personagens associado ao turno.
     *
     * @return O cenário de personagens.
     */
    public CenariosPersonagem getCenarioPersonagens() {
        return cenarioPersonagens;
    }

    /**
     * Retorna o cenário de divisões associado ao turno.
     *
     * @return O cenário de divisões.
     */
    public CenariosDivisao getCenariosDivisao() {
        return cenariosDivisao;
    }

    /**
     * Método abstrato que representa a execução do turno de uma personagem.
     * Cada subclasse deve implementar este método, definindo o comportamento específico
     * para o turno, como movimentação, interações com o ambiente e ataques.
     *
     * @param divisao_atual A divisão atual da personagem.
     */
    public abstract void turno(Divisao divisao_atual);
}
