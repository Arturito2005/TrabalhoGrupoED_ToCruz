package Menu.Opcoes;

import Cenarios.Divisao.CenariosDivisao;
import Cenarios.Personagens.CenariosInimigos;
import Cenarios.Personagens.CenariosToCruz;
import Interfaces.Opcoes.OpMissoesInterface;
import Jogo.Simulacao;
import Missoes.Missoes;
import Modos.ModoAutomatico;
import Modos.ModoCaminhoAutomatico;
import Modos.ModoManual;
import Modos.ModosJogo;
import Turnos.TurnoInimigo;
import Turnos.TurnoToCruz;
import Missoes.Missao;
import Missoes.Versao;

import java.util.InputMismatchException;

/*
 * Ver se é adicionado
 * */
public class OpcoesMissoes extends Opcoes implements OpMissoesInterface {

    public OpcoesMissoes() {
        super();
    }

    /**
     * Método que gere a execução das missões. Caso existam várias missões, o utilizador pode escolher
     * jogar várias missões em sequência ou uma única missão.
     *
     * @param missoes Objeto que contém as missões a serem realizadas.
     */
    @Override
    public void RealizarMissoes(Missoes missoes) {
        if (missoes.getContMissoes() > 1) {
            int recomecar = -1;

            do {
                executarVariasMissoes(missoes);

                do {
                    System.out.print("Deseja jogar outra missao? (Nao: 0/ Sim: 1) -->");

                    try {
                        recomecar = sc.nextInt();
                    } catch (InputMismatchException ex) {
                        System.out.println("Numero invalido!");
                        sc.next();
                    }
                } while (recomecar < 0 || recomecar > 1);
            } while (recomecar != 0);
        } else {
            SelecionarModoJogo(missoes.getListaMissao().first());
        }

        OpcoesFicheiros opcoes = new OpcoesFicheiros();
        opcoes.exportarMissoes(missoes);
    }

    /**
     * Método que permite ao jogador selecionar o modo de jogo para uma missão específica.
     * O jogador pode escolher entre modo manual, automático ou jogo automático.
     *
     * @param missao A missão que será jogada no modo selecionado.
     */
    private void SelecionarModoJogo(Missao missao) {
        int op_modo;
        Versao versao_missao = missao.getVersao();
        //Ver se funciona ao instanciar modosJogo em vez de instanciar o modo no case
        TurnoToCruz turnoTo;
        TurnoInimigo turnoInimigo;

        do {
            op_modo = -1;
            Simulacao simulacao = new Simulacao(versao_missao.getEdificio(), versao_missao.getNum_versao());

            CenariosDivisao cenariosDivisao = new CenariosDivisao(simulacao);
            CenariosToCruz cenariosTo = new CenariosToCruz(simulacao);
            CenariosInimigos cenariosInimigo = new CenariosInimigos(simulacao);
            turnoTo = new TurnoToCruz(cenariosTo, cenariosDivisao);
            turnoInimigo = new TurnoInimigo(cenariosInimigo, cenariosDivisao);
            do {
                System.out.println("0 - Voltar");
                System.out.println("1 - Modo Manual");
                System.out.println("2 - Modo Automatico");
                System.out.println("3 - Jogo Automatico");
                System.out.print("Selecione o modo de jogo que pretende jogar -->");

                try {
                    op_modo = sc.nextInt();
                } catch (InputMismatchException ex) {
                    System.out.println("Numero invalido!");
                    sc.next();
                }

            } while (op_modo < 0 || op_modo > 3);

            ModosJogo modosJogo;
            Simulacao resultado_simulacao = null;
            switch (op_modo) {
                case 1: {
                    modosJogo = new ModoManual(turnoTo, turnoInimigo);
                    resultado_simulacao = modosJogo.jogar();
                    break;
                }
                case 2: {
                    modosJogo = new ModoAutomatico(turnoTo, turnoInimigo);
                    resultado_simulacao = modosJogo.jogar();
                    break;
                }
                case 3: {
                    modosJogo = new ModoCaminhoAutomatico(turnoTo, turnoInimigo);
                    modosJogo.jogar();
                    break;
                }
                default: {
                    break;
                }
            }

            if (resultado_simulacao == null && op_modo != 3) {
                throw new NullPointerException("O resultado da simulacao não pode ser nulo! Ocorreu algum problema!");
            }

            versao_missao.addSimulacao(resultado_simulacao);
        } while (op_modo != 0);
    }

    /**
     * Método responsável por gerir a execução de várias missões. O jogador pode escolher
     * a missão que deseja jogar a partir de uma lista de missões disponíveis.
     *
     * @param missoes Objeto que contém todas as missões disponíveis.
     */
    private void executarVariasMissoes(Missoes missoes) {
        int op_missao;
        Missao[] arrayMissao = new Missao[missoes.getContMissoes()];

        do {
            int i = 0;
            System.out.println("0 - Sair");
            for (Missao mis : missoes.getListaMissao()) {
                arrayMissao[i++] = mis;
                System.out.println(i + " - Missao " + mis.getcod_missao() + " versao nº " + mis.getVersao());
            }

            op_missao = -1;
            do {
                System.out.print("Selecione a opção da missao que quer jogar -->");

                try {
                    op_missao = sc.nextInt();
                } catch (InputMismatchException ex) {
                    System.out.println("Numero invalido!");
                    sc.next();
                }
            } while (op_missao < 0 || op_missao > i);

            if (op_missao > 0) {
                SelecionarModoJogo(arrayMissao[op_missao - 1]);
            }
        } while (op_missao != 0);
    }
}
