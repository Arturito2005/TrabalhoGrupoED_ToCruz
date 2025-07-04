package Missoes;

import Exceptions.InvalidOptionException;
import Exceptions.InvalidTypeItemException;
import Interfaces.*;
import Interfaces.Jogo.SimulacoesInt;
import Items.Item;
import Items.ItemCura;
import Items.TypeItemCura;
import LinkedList.LinearLinkedUnorderedList;
import Mapa.Divisao;
import Mapa.Edificio;
import Personagens.Inimigo;
import Personagens.ToCruz;
import Queue.LinkedQueue;
import Stacks.LinkedStack;
import ArrayList.ArrayUnordered;

import java.util.*;

/**
 * Classe responsável pela gestão das simulações do jogo, implementando o comportamento do ToCruz
 * enquanto interage com o edifício, inimigos e outros objetos do jogo.
 * A classe é capaz de realizar simulações manuais, automáticas e jogo automático, além de
 * gerar relatórios ao final de cada simulação.
 * <p>
 * A classe implementa a interface {@link SimulacoesInt} para fornecer os métodos necessários
 * para o controlo do jogo e a interface {@link Comparable} para permitir comparações entre
 * diferentes simulações.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class Simulacoes implements SimulacoesInt, Comparable<Simulacoes> {

    /**
     * Instância do scanner para a leitura de entradas do jogador.
     */
    private static Scanner sc = new Scanner(System.in);

    /**
     * Versão da simulação atual, identificando a versão de cada execução do jogo.
     */
    private long versao_simulacao;

    /**
     * Queue que armazena o trajeto percorrido pelo ToCruz dentro do edifício.
     * Representa a sequência de divisões visitadas durante a simulação.
     */
    private QueueADT<Divisao> trajeto_to;

    /**
     * Lista não ordenada de inimigos mortos durante a simulação.
     * Usada para armazenar os inimigos que foram abatidos.
     */
    private UnorderedListADT<Inimigo> inimigos_dead;

    /**
     * Vida restante da personagem ToCruz durante a simulação.
     * Representa a quantidade de vida restante para o ToCruz.
     */
    private long vida_to;

    /**
     * Representa o edifício onde a simulação ocorre.
     * Contém as divisões, inimigos e outros elementos do jogo.
     */
    private Edificio edificio;

    /**
     * Construtor que inicializa os atributos da simulação com os valores fornecidos.
     *
     * @param versao_simulacao a versão da simulação.
     * @param edificio o edifício onde a simulação ocorre.
     */
    public Simulacoes(long versao_simulacao, Edificio edificio) {
        this.edificio = edificio;
        this.versao_simulacao = versao_simulacao;
        this.vida_to = 0;
        this.trajeto_to = new LinkedQueue<>();
        this.inimigos_dead = new LinearLinkedUnorderedList<Inimigo>();
    }

    /**
     * Retorna a versão da simulação.
     *
     * @return a versão da simulação.
     */
    public long getVersao_simulacao() {
        return versao_simulacao;
    }

    /**
     * Retorna a queue de trajetos do ToCruz.
     *
     * @return a queue de trajetos do ToCruz.
     */
    public QueueADT<Divisao> getTrajeto_to() {
        return trajeto_to;
    }

    /**
     * Retorna a lista dos inimigos mortos.
     *
     * @return a lista de inimigos mortos.
     */
    public UnorderedListADT<Inimigo> getInimigos_dead() {
        return inimigos_dead;
    }

    /**
     * Retorna a quantidade de vida restante do ToCruz.
     *
     * @return a quantidade de vida do ToCruz.
     */
    public double getVida_to() {
        return vida_to;
    }


    /**
     * Adiciona uma divisão ao trajeto do ToCruz.
     *
     * @param divisao a divisão a ser adicionada ao trajeto.
     */
    public void addDivisaoTrajetoToCruz(Divisao divisao) {
        this.trajeto_to.enqueue(divisao);
    }

    /**
     * Realiza a verificação e uso de itens automáticos de cura para o ToCruz.
     *
     * @param divisao a divisão onde o ToCruz se encontra.
     * @throws InvalidTypeItemException se o tipo de item for inválido.
     */
    private void ConditionGetUseItemAutomatico(Divisao divisao) throws InvalidTypeItemException {
        if (!divisao.getItem().isCollected() && divisao.getItem() instanceof ItemCura) {
            ItemCura item = (ItemCura) divisao.getItem();
            ToCruz toCruz = divisao.getToCruz();

            if (item != null) {
                switch (item.getType()) {
                    case COLETE: {
                        toCruz.usarItem(item);
                        System.out.println("O To Cruz apanhou um colete de vida e ficou com " + toCruz.getVida() + " HP");
                        break;
                    }
                    case KIT_VIDA: {
                        if (!toCruz.mochilaIsFull() && (toCruz.getVida() == 100 || toCruz.getVida() + item.getVida_recuperada() >= 100)) {
                            toCruz.guardarKit(item);
                            System.out.println("O To Cruz guardou um kit de vida com " + item.getVida_recuperada() + " HP");
                        } else if (toCruz.getVida() + item.getVida_recuperada() <= 100) {
                            toCruz.usarItem(item);
                            System.out.println("O To Cruz usou um kit de vida com " + item.getVida_recuperada() + " HP");
                        }
                        break;
                    }
                    default: {
                        throw new InvalidTypeItemException("Tipo de item de cura invalido");
                    }
                }
            } else {
                if (toCruz.getVida() <= 30 && toCruz.mochilaTemKit()) {
                    toCruz.usarKit();
                }
            }
        }
    }

    /**
     * Verifica se a personagem ToCruz deve usar um kit de primeiros socorros da mochila,
     * com base na condição de vida da personagem e na disponibilidade do kit na mochila.
     * Se a vida de ToCruz for menor ou igual a 30 e se a mochila contiver um kit, o kit é usado.
     *
     * @param toCruz O objeto da personagem ToCruz, que será analisado para decidir se o
     * kit será utilizado.
     * @return Retorna verdadeiro se o kit foi usado, caso contrário retorna falso.
     */
    private boolean ConditionUseKitMochila(ToCruz toCruz) {
        boolean usedKit = false;

        if (toCruz.getVida() <= 30 && toCruz.mochilaTemKit()) {
            toCruz.usarKit();
            usedKit = true;
        }

        return usedKit;
    }

    /**
     * Determina a melhor divisão inicial para o ToCruz começar a sua trajetória dentro do edifício.
     *
     * @param toCruz a personagem ToCruz que realizará a simulação.
     * @return a divisão onde o ToCruz deve começar o seu percurso.
     */
    private Divisao BestStartToCruz(ToCruz toCruz, UnorderedListADT<Divisao> list_entradas, Divisao div_alvo) {
        Divisao best_div = null;
        double best_distance = Double.MAX_VALUE;
        double num_arestas_com = Double.MAX_VALUE;

        for (Divisao div_entr : list_entradas) {
            double distance = edificio.getShortestPath(div_entr, div_alvo);

            if (distance == 0 || distance == best_distance) {
                best_distance = distance;
                double num_arestas = edificio.getShortestPathNumArestas(div_entr, div_alvo);

                if (num_arestas < num_arestas_com) {
                    if (distance == 0) {
                        num_arestas_com = num_arestas;
                    }
                    best_div = div_entr;

                }
            } else if (distance < best_distance) {
                best_div = div_entr;
                best_distance = distance;
            }
        }

        if (best_distance < toCruz.getVida()) {
            System.out.println("A melhor entrada que o To Cruz deve escolher e esta: " + best_div.getName());
            System.out.println("Melhor caminho que o To Cruz pode fazer ate ao alvo");

            shortesPathTwopoints(best_div, div_alvo);
        }

        return best_div;
    }

    /**
     * Metodo que sugere o melhor caminho para o To Cruz alcançar o alvo ou sair do edifício
     * automaticamente.
     * Avalia as divisões do edifício para determinar o destino ideal com base na distância
     * e no número de arestas.
     *
     * Se o alvo já tiver sido recolhido, sugere o caminho para a saída mais próxima.
     * Caso contrário, sugere o caminho para a divisão onde o alvo se encontra.
     *
     * @param div_to A divisão de partida do To Cruz.
     * @return O melhor caminho calculado entre dois pontos (partida e destino).
     */
    private Divisao sugestaoCaminhoToCruzAutomatico(Divisao div_to) {
        ToCruz toCruz = div_to.getToCruz();
        Iterator<Divisao> itr = edificio.IteratorMapa();
        Divisao best_destino = null;
        double best_distance = Double.MAX_VALUE;
        double num_arestas_com = Double.MAX_VALUE;
        boolean find_alvo = false;

        while (itr.hasNext() && !find_alvo) {
            Divisao div = itr.next();

            if (div.isEntrada_saida() && toCruz.isColectedAlvo()) {
                double distance = edificio.getShortestPath(div_to, div);

                if (distance == 0 || distance == best_distance) {
                    best_distance = distance;
                    double num_arestas = edificio.getShortestPathNumArestas(div_to, div);

                    if (num_arestas < num_arestas_com) {
                        if (distance == 0) {
                            num_arestas_com = num_arestas;
                        }
                        best_destino = div;
                    }
                } else if (distance < best_distance) {
                    best_distance = distance;
                    best_destino = div;
                }
            } else if (div.getAlvo() != null && !toCruz.isColectedAlvo()) {
                best_destino = div;
                find_alvo = true;
            }
        }

        String temp;
        if (best_destino.getAlvo() != null) {
            temp = "Sugestao de melhor caminho para o To Cruz chegar ao alvo: " + best_destino.getAlvo().getNome();
        } else {
            temp = "Sugestao de melhor caminho para o ToCruz sair do edificio";
        }

        System.out.println(temp);
        return shortesPathTwoPointsAutomatico(div_to, best_destino);
    }

    /**
     * Realiza o turno automático do ToCruz, considerando a presença de inimigos
     * e a busca por itens ou alvos.
     *
     * @param divisao_atual a divisão onde o ToCruz se encontra.
     */
    private void turnoAutomaticoToCruz(Divisao divisao_atual) {
        ToCruz toCruz = divisao_atual.getToCruz();
        Divisao divisao = divisao_atual;

        if (divisao_atual.haveConfronto()) {
            if (!ConditionUseKitMochila(toCruz)) {
                divisao_atual.attackToCruz(inimigos_dead);
            }
        } else {
            ConditionUseKitMochila(toCruz);
            divisao = sugestaoCaminhoToCruzAutomatico(divisao_atual);

            divisao.addToCruz(toCruz);
            this.addDivisaoTrajetoToCruz(divisao);
            divisao_atual.removeToCruz();

            if (divisao.haveConfronto()) {
                divisao.attackToCruz(inimigos_dead);
            }

            if (divisao.getItem() != null) {
                ConditionGetUseItemAutomatico(divisao);
            }
        }

        if (!divisao.haveConfronto() && divisao.isToCruzInDivisaoAlvo() && !divisao.getAlvo().isAtinigido()) {
            divisao.ToCruzGetAlvo();
        }
    }

    /**
     * Calcula a melhor entrada para o ToCruz, baseado na distância e nas arestas do
     * caminho até o alvo.
     *
     * @param toCruz a personagem ToCruz.
     * @param div_alvo a divisão alvo.
     * @param list_entradas as divisões de entrada para o edifício.
     * @return a melhor distância para o ToCruz atingir o alvo.
     */
    private double calculateBesteEntradaToCruz(ToCruz toCruz, Divisao div_alvo, UnorderedListADT<Divisao> list_entradas) {
        return edificio.getShortestPath(BestStartToCruz(toCruz, list_entradas, div_alvo), div_alvo);
    }

    /**
     * Calcula o melhor caminho para o ToCruz sair do edifício, considerando as entradas fornecidas.
     *
     * @param div_alvo A divisão alvo que o ToCruz precisa alcançar.
     * @return A distância do melhor caminho encontrado.
     */
    public double calculateBestExitAutomatico(Divisao div_alvo) {
        return edificio.getShortestPath(div_alvo, sugestaoCaminhoToCruzAutomatico(div_alvo));
    }

    /**
     * Realiza a simulação do jogo no modo automático, onde a personagem ToCruz é controlado
     * pelo sistema para tentar alcançar um alvo e, em seguida, encontrar uma saída do edifício
     * sem morrer. O metodo verifica as entradas e saídas do edifício, os inimigos presentes
     * e tenta calcular se é possível concluir a missão com vida.
     * <p>
     * O ToCruz segue automaticamente pelo edifício, enfrenta inimigos, até tentar atingir
     * o alvo e sair do edifício. Se a vida de ToCruz for insuficiente para completar o trajeto
     * ou escapar do edifício, o jogador é notificado.
     */
    @Override
    public void modojogoAutomatico() {
        Iterator<Divisao> itr = edificio.getPlantaEdificio().iterator();
        UnorderedListADT<Divisao> list_entradas = new LinearLinkedUnorderedList<>();
        UnorderedListADT<Divisao> divisao_inimigo = new LinearLinkedUnorderedList<>();
        Divisao div_alvo = null;
        ToCruz to = new ToCruz();

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.isEntrada_saida()) {
                list_entradas.addToRear(div);
            }

            if (div.getAlvo() != null) {
                div_alvo = div;
            }

            if (div.haveInimigo()) {
                divisao_inimigo.addToRear(div);
            }
        }

        if (calculateBesteEntradaToCruz(to, div_alvo, list_entradas) > to.getVida()) {
            System.out.println("O To Cruz nao consegue chegar ao alvo sem morrer!");
        } else {
            for (Divisao div_inimi : divisao_inimigo) {
                turnoInimigo(div_inimi);
            }

            div_alvo.addToCruz(to);
            div_alvo.getAlvo().setAtinigido(true);
            to.setColectedAlvo(true);
            if (calculateBestExitAutomatico(div_alvo) > to.getVida()) {
                System.out.println("E impossivel o To Cruz sair do edificio com vida!");
            }
        }
    }

    /**
     * Simula o jogo automaticamente, fazendo o ToCruz se , enfrentar inimigos e
     * atingir objetivos.
     *
     * @return A instância atual da simulação após a execução do jogo automático.
     */
    @Override
    public Simulacoes jogoAutomatico() {
        ToCruz toCruz = new ToCruz();
        Iterator<Divisao> itrMapa = this.edificio.IteratorMapa();
        UnorderedListADT<Divisao> list_entradas = new LinearLinkedUnorderedList<>();
        Divisao div_alvo = null;
        boolean finishgame = false;
        while (itrMapa.hasNext()) {
            Divisao div = itrMapa.next();

            if (div.isEntrada_saida()) {
                list_entradas.addToRear(div);
            }

            if (div.getAlvo() != null) {
                div_alvo = div;
            }
        }

        Divisao div_start = BestStartToCruz(toCruz, list_entradas, div_alvo);
        addDivisaoTrajetoToCruz(div_start);
        div_start.addToCruz(toCruz);
        while (!finishgame) {
            itrMapa = this.edificio.IteratorMapa();
            UnorderedListADT<Divisao> endTurno = new LinearLinkedUnorderedList<>();
            while (itrMapa.hasNext()) {
                Divisao div = itrMapa.next();

                if (div.haveInimigo()) {
                    endTurno.addToRear(div);
                }
            }

            while (!endTurno.isEmpty()) {
                turnoInimigo(endTurno.removeFirst());
            }

            itrMapa = this.edificio.IteratorMapa();
            boolean findToCruz = false;
            while (itrMapa.hasNext() && !findToCruz) {
                Divisao div = itrMapa.next();

                if (div.getToCruz() != null) {
                    if (div.getToCruz().isDead() || (div.isToCruzInExit() && div.getToCruz().isColectedAlvo())) {
                        finishgame = true;
                    } else {
                        turnoAutomaticoToCruz(div);
                    }

                    findToCruz = true;
                }
            }
        }

        long vida = toCruz.getVida();
        if (vida < 0) {
            vida = 0;
        }
        this.vida_to = vida;
        relatoriosMissao(toCruz);
        return this;
    }

    /**
     * Imprime o caminho mais curto entre duas divisões no mapa.
     *
     * @param div_start A divisão de início do caminho.
     * @param Div_final A divisão final do caminho.
     */
    private void shortesPathTwopoints(Divisao div_start, Divisao Div_final) {
        Iterator<Divisao> shortestPath = edificio.shortesPathIt(div_start, Div_final);
        String temp = "";

        while (shortestPath.hasNext()) {
            Divisao div = shortestPath.next();

            if (shortestPath.hasNext()) {
                temp += div.getName() + " -->";
            } else {
                temp += div.getName();
            }
        }
        System.out.println(temp);
    }

    /**
     * Calcula e imprime o menor caminho entre duas divisões,
     * retornando a próxima divisão no caminho.
     * <p>
     * Este metodo utiliza o iterador do menor caminho entre duas divisões fornecido pelo edifício.
     * Ele constrói uma string que representa o caminho completo e identifica a próxima divisão
     * a ser visitada após a divisão inicial.
     *
     * @param div_start a divisão inicial no caminho.
     * @param div_final a divisão final no caminho.
     * @return a próxima divisão no menor caminho a partir da divisão inicial.
     */
    private Divisao shortesPathTwoPointsAutomatico(Divisao div_start, Divisao div_final) {
        Iterator<Divisao> shortestPath = edificio.shortesPathIt(div_start, div_final);
        String temp = "";
        Divisao div_to = null;

        int i = 0;
        while (shortestPath.hasNext()) {
            Divisao div = shortestPath.next();

            if (i == 1) {
                div_to = div;
            }

            if (shortestPath.hasNext()) {
                temp = temp + div.getName() + " -->";
            } else {
                temp = temp + div.getName();
            }

            i++;
        }

        System.out.println(temp);
        return div_to;
    }

    /**
     * Sugere o melhor caminho para o ToCruz chegar a um item ou a um alvo.
     * O ToCruz deve coletar o item e, em seguida, atingir o alvo.
     *
     * @param div_to A divisão onde o ToCruz está localizado.
     */
    private void sugestaoCaminhoToCruzKitEAlvo(Divisao div_to) {
        ToCruz toCruz = div_to.getToCruz();
        Iterator<Divisao> itr = edificio.IteratorMapa();

        Divisao item_div = null;
        Divisao div_alvo = null;
        double best_distance = Double.MAX_VALUE;
        double distance;
        double num_arestas_com = Double.MAX_VALUE;
        double num_arestas;

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if ((div.getItem() != null && !div.getItem().isCollected()) || (div.isEntrada_saida() && div.getAlvo() != null && toCruz.isColectedAlvo())) {
                distance = edificio.getShortestPath(div_to, div);

                if (distance == 0 || distance == best_distance) {
                    best_distance = distance;
                    num_arestas = edificio.getShortestPathNumArestas(div_to, div);

                    if (num_arestas < num_arestas_com) {
                        if (distance == 0) {
                            num_arestas_com = num_arestas;
                        }

                        if (div.getItem() != null) {
                            item_div = div;
                        }
                        if (div.isEntrada_saida()) {
                            div_alvo = div;
                        }
                    }
                } else if (distance < best_distance) {
                    best_distance = distance;

                    if (div.getItem() != null) {
                        item_div = div;
                    }

                    if (div.isEntrada_saida()) {
                        div_alvo = div;
                    }
                }
            } else if (div.getAlvo() != null && !toCruz.isColectedAlvo()) {
                div_alvo = div;
            }
        }


        System.out.println("Sugestao de caminho mais curto para o To Cruz chegar a um item de cura");
        shortesPathTwopoints(div_to, item_div);

        String temp;
        if (div_alvo.getAlvo() != null) {
            temp = "Sugestao de melhor caminho para o To Cruz chegar ao alvo: " + div_alvo.getAlvo().getNome();
        } else {
            temp = "Sugestao de melhor caminho para o ToCruz sair do edificio";
        }

        System.out.println(temp);
        shortesPathTwopoints(div_to, div_alvo);
    }

    /**
     * Este metodo solicita ao utilizador que selecione a próxima divisão para o ToCruz se mover,
     * exibindo informações sobre as divisões disponíveis, como se há inimigos, itens ou se é uma
     * entrada/saída. Também sugere o melhor caminho para o ToCruz apanhar itens e atingir o alvo.
     *
     * @param divisao_atual A divisão onde o ToCruz se encontra atualmente.
     * @return A divisão para onde o ToCruz deve se mover.
     */
    private Divisao getNewDivisaoTo(Divisao divisao_atual) {
        int op = -1;
        Iterator<Divisao> itr = edificio.getNextDivisoes(divisao_atual);
        ArrayUnorderedADT<Divisao> listDiv = new ArrayUnordered<Divisao>();

        int i = 0;
        String temp;

        System.out.println();
        System.out.println("Divisoes que o To Cruz pode entrar: ");
        while (itr.hasNext()) {
            Divisao divisao = itr.next();
            temp = i++ + " - " + divisao.getName();

            if (divisao.isEntrada_saida()) {
                if (divisao.getInimigos().isEmpty()) {
                    temp += " (esta divisao e uma entrada/saida)";
                } else {
                    temp += " (esta divisao e uma entrada/saida e tem inimigos)";
                }
            }

            if (divisao.getAlvo() != null) {
                if (divisao.getInimigos().isEmpty()) {
                    temp += " (divisao onde esta o alvo)";
                } else {
                    temp += "(divisao onde esta o alvo, mas tem inimigos)";
                }
            }

            if (divisao.getItem() != null) {
                if (divisao.getItem() instanceof ItemCura && !divisao.getItem().isCollected()) {
                    if (divisao.getInimigos().isEmpty()) {
                        if (((ItemCura) divisao.getItem()).getType().equals(TypeItemCura.KIT_VIDA)) {
                            temp += " (divisao com kit)";
                        } else if (((ItemCura) divisao.getItem()).getType().equals(TypeItemCura.COLETE)) {
                            temp += " (divisao com colete)";
                        }
                    } else {
                        if (((ItemCura) divisao.getItem()).getType().equals(TypeItemCura.KIT_VIDA)) {
                            temp += " (divisao com kit e com inimigos)";
                        } else if (((ItemCura) divisao.getItem()).getType().equals(TypeItemCura.COLETE)) {
                            temp += " (divisao com colete e com inimigos)";
                        }
                    }
                }
            }

            System.out.println(temp);
            listDiv.addToRear(divisao);
        }

        sugestaoCaminhoToCruzKitEAlvo(divisao_atual);

        System.out.println();
        do {
            System.out.print("Selecione a divisao que o ToCruz vai se mover -->");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }
        } while (op < 0 || op > listDiv.size() - 1);

        Divisao div = null;
        try {
            div = listDiv.find(op);
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }
        return div;
    }


    /**
     * Este metodo trata de um item encontrado em uma divisão e permite que o ToCruz
     * interaja com ele.
     * Dependendo do tipo de item, ele pode ser usado, deixado na sala ou guardado na mochila.
     *
     * @param divisao A divisão onde o item foi encontrado.
     * @param toCruz A personagem que interage com o item.
     * @throws InvalidOptionException Caso a opção fornecida pelo jogador seja inválida.
     * @throws InvalidTypeItemException Caso o tipo de item seja inválido.
     */
    private void DivisaoComItem(Divisao divisao, ToCruz toCruz) throws InvalidOptionException, InvalidTypeItemException {
        if (!divisao.getItem().isCollected() && divisao.getItem() instanceof ItemCura) {
            ItemCura item = (ItemCura) divisao.getItem();

            switch (item.getType()) {
                case COLETE: {
                    toCruz.usarItem(item);
                    System.out.println("O To Cruz apanhou um colete e ficou com " + toCruz.getVida() + " HP");
                    break;
                }
                case KIT_VIDA: {
                    int op = -1;
                    if (toCruz.getVida() < 100 && !toCruz.mochilaIsFull()) {
                        do {
                            System.out.println("Esta numa sala com um kit de vida de " + item.getVida_recuperada());
                            System.out.println("0 - Usar Item");
                            System.out.println("1 - Deixa-lo na sala");
                            System.out.println("2 - Guardar");
                            System.out.print("Selecione uma opcao -->");
                            try {
                                op = sc.nextInt();
                            } catch (InputMismatchException ex) {
                                System.out.println("Numero invalido!");
                                sc.next();
                            }
                        } while (op < 0 || op > 2);
                    } else if (toCruz.mochilaIsFull() && toCruz.getVida() >= 100) {
                        do {
                            System.out.println("Esta numa sala com um kit de vida de " + item.getVida_recuperada());
                            System.out.println("0 - Usar Item");
                            System.out.println("1 - Deixa-lo na sala");
                            System.out.print("Selecione uma opcao -->");
                            try {
                                op = sc.nextInt();
                            } catch (InputMismatchException ex) {
                                System.out.println("Numero invalido!");
                                sc.next();
                            }
                        } while (op < 0 || op > 1);
                    } else if (!toCruz.mochilaIsFull() && toCruz.getVida() >= 100) {
                        System.out.println("Esta numa sala com um kit de vida de " + item.getVida_recuperada());
                        System.out.println("1 - Deixa-lo na sala");
                        System.out.println("2 - Guardar");
                        System.out.print("Selecione uma opcao -->");
                        do {
                            try {
                                op = sc.nextInt();
                            } catch (InputMismatchException ex) {
                                System.out.println("Numero invalido!");
                                sc.next();
                            }
                        } while (op < 1 || op > 2);
                    }

                    switch (op) {
                        case 0: {
                            divisao.usarItemDivisao();
                            break;
                        }
                        case 1: {
                            System.out.println("O To Cruz nao coleta o item");
                            break;
                        }
                        case 2: {
                            toCruz.guardarKit(item);
                            break;
                        }
                        default: {
                            throw new InvalidOptionException("Introduziu uma opcao invalida");
                        }
                    }
                    break;
                }
                default: {
                    throw new InvalidTypeItemException("Tipo de item invalido");
                }
            }
        }
    }

    /**
     * Este metodo executa o turno do ToCruz em uma divisão, lidando com situações de combate
     * e interações com itens. O ToCruz pode atacar inimigos ou usar kits de vida.
     *
     * @param divisao_atual A divisão onde o ToCruz se encontra.
     */
    private void turnoToCruz(Divisao divisao_atual) {
        ToCruz toCruz = divisao_atual.getToCruz();
        Divisao divisao = divisao_atual;
        int op = -1;
        if (divisao_atual.haveConfronto()) {
            if (toCruz.mochilaTemKit()) {
                do {
                    System.out.println("1 - Atacar");
                    System.out.println("2 - Usar kit");
                    System.out.print("Selecione uma opcao -->");
                    try {
                        op = sc.nextInt();
                    } catch (InputMismatchException ex) {
                        System.out.println("Numero invalido!");
                        sc.next();
                    }
                } while (op < 1 || op > 2);
            } else {
                System.out.println("Nao e possível o To Cruz curar-se porque nao tem kits na mochila. Por isso ToCruz ataca!");
                op = 1;
            }

            switch (op) {
                case 1: {
                    divisao_atual.attackToCruz(inimigos_dead);
                    break;
                }
                case 2: {
                    toCruz.usarKit();
                    break;
                }
                default: {
                    throw new InvalidOptionException("Opcao invalida");
                }
            }
        } else {
            try {
                if (toCruz.mochilaTemKit() && toCruz.getVida() < 100) {
                    int op_kit = -1;
                    System.out.println("O To Cruz possui kits na sua mochila");
                    System.out.println("O proximo kit da mochila tem os seguintes pontos recuperados: " + toCruz.getMochila().peek().getVida_recuperada() + " HP");
                    do {
                        System.out.print("Deseja utilizar o kit de vida? (Nao: 0/ Sim: 1) -->");

                        try {
                            op_kit = sc.nextInt();
                        } catch (InputMismatchException ex) {
                            System.out.println("Numero invalido!");
                            sc.next();
                        }
                    } while (op_kit < 0 || op_kit > 1);

                    if (op_kit == 1) {
                        toCruz.usarKit();
                    }
                }

                divisao = this.getNewDivisaoTo(divisao_atual);
                divisao.addToCruz(divisao_atual.getToCruz());
                divisao_atual.removeToCruz();

                addDivisaoTrajetoToCruz(divisao);

                if (divisao.haveConfronto()) {
                    divisao.attackToCruz(inimigos_dead);
                }

                if (divisao.getItem() != null) {
                    DivisaoComItem(divisao, toCruz);
                }
            } catch (NullPointerException ne) {
                System.out.println(ne.getMessage());
                divisao = divisao_atual;
            }
        }

        if (!divisao.haveConfronto() && divisao.isToCruzInDivisaoAlvo()) {
            divisao.ToCruzGetAlvo();
        }
    }

    /**
     * Metodo que calcula o poder total final de uma divisão, somando o poder inicial
     * fornecido com o poder dos inimigos presentes na divisão.
     *
     * Caso a divisão não tenha inimigos ou contenha apenas um, o poder inicial é retornado.
     * Caso existam vários inimigos, os seus poderes são somados ao poder inicial.
     *
     * @param div A divisão cuja força total será calculada.
     * @param poder_inimigo O poder inicial base dos inimigos.
     * @return O poder total calculado da divisão.
     */
    private double poderFinalDivisao(Divisao div, long poder_inimigo) {
        long poder_total = poder_inimigo;
        if (div.getInimigos().isEmpty() && div.getInimigos().size() > 1) {
            for (Inimigo inimigo_div : div.getInimigos()) {
                poder_total += inimigo_div.getPoder();
            }
        }

        return poder_total;
    }

    /**
     * Move um inimigo de uma divisão para outra dentro do edifício.
     * O inimigo move-se de forma aleatória entre as divisões conectadas à divisão atual.
     * A quantidade de movimentos é determinada aleatoriamente, variando entre 0 e 2,
     * e o peso entre as salas no grafo é atualizado de acordo com o poder de ataque do inimigo.
     *
     * @param divisao_atual A sala onde o inimigo está atualmente.
     * @param inimigo O inimigo que será movido para uma nova sala.
     * @return A nova sala onde o inimigo se encontra após o movimento.
     */
    private Divisao moverInimigo(Divisao divisao_atual, Inimigo inimigo) {
        Random randomizer = new Random();
        int numMoves = randomizer.nextInt(3);
        long poder_inimigo = inimigo.getPoder();

        for (int i = 0; i < numMoves; i++) {
            edificio.updateWeight(divisao_atual, poderFinalDivisao(divisao_atual, poder_inimigo) - poder_inimigo);
            StackADT<Divisao> stDiv = new LinkedStack<>();
            Divisao divisaoEscolhida;
            Iterator<Divisao> itrDiv = this.edificio.getNextDivisoes(divisao_atual);

            while (itrDiv.hasNext()) {
                stDiv.push(itrDiv.next());
            }

            int rand = randomizer.nextInt(stDiv.size());

            while (stDiv.size() - 1 > rand && !stDiv.isEmpty()) {
                stDiv.pop();
            }

            divisaoEscolhida = stDiv.peek();
            divisao_atual.removeInimigo(inimigo);

            divisaoEscolhida.addInimigo(inimigo);
            this.edificio.updateWeight(divisaoEscolhida, poderFinalDivisao(divisaoEscolhida, poder_inimigo));
            divisao_atual = divisaoEscolhida;
        }
        System.out.println("O inimigo " + inimigo.getNome() + " moveu-se para a sala: " + divisao_atual.getName());
        return divisao_atual;
    }

    /**
     * Realiza o turno de movimento dos inimigos na divisão especificada.
     * Para cada inimigo na divisão, se não houver confronto, o inimigo move-se para uma nova divisão.
     * Caso contrário, o inimigo ataca.
     *
     * @param divisao A divisão onde os inimigos estão localizados e onde o turno será realizado.
     */
    private void turnoInimigo(Divisao divisao) {
        UnorderedListADT<Inimigo> inimigos_move = new LinearLinkedUnorderedList<>();

        for (Inimigo inimigo : divisao.getInimigos()) {
            if (!divisao.haveConfronto()) {
                System.out.println("O inimigo " + inimigo.getNome() + " esta na sala " + divisao.getName());
                inimigos_move.addToRear(inimigo);
            } else {
                divisao.attackInimigo(inimigo);
            }
        }


        for (Inimigo inimigo : inimigos_move) {
            Divisao div = moverInimigo(divisao, inimigo);
            if (div.haveConfronto()) {
                div.attackInimigo(inimigo);
            }
        }
    }

    /**
     * Permite que a personagem ToCruz entre num edifício, selecionando uma divisão de entrada/saída.
     * O jogador escolhe uma sala de entrada onde o ToCruz começa o jogo, e a divisão escolhida
     * é registada. Caso o ToCruz entre numa divisão com confronto,
     * ele pode lutar contra os inimigos ou interagir com itens do chão.
     *
     * @param toCruz A personagem que irá entrar no edifício.
     * @return A divisão em que o ToCruz entrou após a seleção.
     * @throws NullPointerException Caso ocorra um erro de null pointer.
     * @throws ArrayIndexOutOfBoundsException Caso o índice da sala selecionada esteja
     * fora dos limites do array.
     */
    private Divisao ToCruzEntrarEdificio(ToCruz toCruz) {
        int op = -1;
        int i = 0;
        Iterator<Divisao> itr = this.edificio.getPlantaEdificio().iterator();
        ArrayUnorderedADT<Divisao> listDiv = new ArrayUnordered<>();

        while (itr.hasNext()) {
            Divisao div = itr.next();

            if (div.isEntrada_saida()) {
                String temp = i + " - " + div.getName();

                if (div.haveInimigo()) {
                    temp += " (divisao com inimigos)";
                }

                if (div.getItem() != null) {
                    temp += " (divisao com item)";
                }

                System.out.println(temp);
                listDiv.addToRear(div);
                i++;
            }
        }

        do {
            System.out.print("Introduza onde o ToCruz vai entrar -->");

            try {
                op = sc.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Numero invalido!");
                sc.next();
            }
        } while (op < 0 || op > listDiv.size() - 1);

        Divisao divisao_nova = null;

        try {
            divisao_nova = listDiv.find(op);
            divisao_nova.addToCruz(toCruz);
            addDivisaoTrajetoToCruz(divisao_nova);

            if (divisao_nova.haveConfronto()) {
                divisao_nova.attackToCruz(this.inimigos_dead);
            }

            if (!divisao_nova.haveConfronto()) {
                if (divisao_nova.isToCruzInDivisaoAlvo()) {
                    divisao_nova.ToCruzGetAlvo();
                } else if (divisao_nova.getItem() != null) {
                    DivisaoComItem(divisao_nova, divisao_nova.getToCruz());
                }
            }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }

        return divisao_nova;
    }

    /**
     * Realiza a simulação do jogo no modo manual. O jogador controla a personagem ToCruz,
     * movendo-se pelo edifício, enfrentando inimigos e coletando itens até completar a missão
     * (sair do edifício com o alvo) ou ser derrotado.
     *
     * @return O objeto Simulacoes atualizado com os resultados da simulação.
     */
    @Override
    public Simulacoes modojogoManual() {
        ToCruz toCruz = new ToCruz();
        Iterator<Divisao> itrMapa;
        boolean finishgame = false;

        edificio.drawMapa();
        ToCruzEntrarEdificio(toCruz);
        while (!finishgame) {
            itrMapa = edificio.IteratorMapa();
            UnorderedListADT<Divisao> endTurno = new LinearLinkedUnorderedList<>();
            while (itrMapa.hasNext()) {
                Divisao div = itrMapa.next();

                if (div.haveInimigo()) {
                    endTurno.addToRear(div);
                }
            }

            edificio.drawMapa();
            int num_turnos = endTurno.size();
            for (int i = 0; i < num_turnos; i++) {
                turnoInimigo(endTurno.removeFirst());
            }

            itrMapa = edificio.IteratorMapa();
            boolean findToCruz = false;

            while (itrMapa.hasNext() && !findToCruz) {
                Divisao div = itrMapa.next();

                if (div.getToCruz() != null) {
                    if (div.getToCruz().isDead()) {
                        finishgame = true;
                    } else if (div.isEntrada_saida() && !div.haveConfronto() && trajeto_to.size() > 1) {
                        int op = -1;

                        do {
                            System.out.print("Deseja sair do edificio (Nao:0 / Sim: 1)? -->");

                            try {
                                op = sc.nextInt();
                            } catch (InputMismatchException ex) {
                                System.out.println("Numero invalido!");
                                sc.next();
                            }
                        } while (op < 0 || op > 1);

                        if (op == 1) {
                            finishgame = true;
                        } else {
                            turnoToCruz(div);
                        }
                    } else {
                        turnoToCruz(div);
                    }

                    findToCruz = true;
                    edificio.drawMapa();
                }
            }
        }

        long vida = toCruz.getVida();
        if (vida < 0) {
            vida = 0;
        }
        this.vida_to = vida;
        relatoriosMissao(toCruz);

        return this;
    }

    /**
     * Gera o relatório final do jogo, incluindo informações sobre o progresso da personagem ToCruz,
     * itens coletados, inimigos mortos e o status da missão.
     * O relatório também exibe o percurso feito pelo ToCruz durante a simulação.
     *
     * @param toCruz A personagem cujo relatório será gerado.
     */
    private void relatoriosMissao(ToCruz toCruz) {
        Iterator<Divisao> itrMapa = this.edificio.IteratorMapa();
        UnorderedListADT<Item> item_colected = new LinearLinkedUnorderedList<Item>();

        while (itrMapa.hasNext()) {
            Divisao div = itrMapa.next();

            if (div.getItem() != null && div.getItem().isCollected()) {
                item_colected.addToRear(div.getItem());
            }
        }

        System.out.println(" ");
        System.out.println("!---------------------------------!");
        System.out.println(" ");
        System.out.println("Fim do jogo");
        System.out.println("Relatorio do jogo: ");
        System.out.println("Vida final do ToCruz --> " + this.vida_to);

        if (this.vida_to > 0 && toCruz.isColectedAlvo()) {
            System.out.println("Missao realizada com sucesso! ☆*: .｡. o(≧▽≦)o .｡.:*☆");
        } else {
            System.out.println("Missao falhada ಥ_ಥ");
        }

        System.out.println("Numero de inimigos mortos: " + inimigos_dead.size());
        if (!inimigos_dead.isEmpty()) {
            int i = 1;
            for (Inimigo inimigo : inimigos_dead) {
                System.out.println("Inimigo nº" + i + " : " + inimigo.toString());
                i++;
            }
        }

        System.out.println("Numero de itensColetados: " + item_colected.size());
        if (!item_colected.isEmpty()) {
            System.out.println("Itens coletados pelo ToCruz:");

            int i = 1;
            while (!item_colected.isEmpty()) {
                System.out.println("Item coletado nº " + i + " : " + item_colected.removeFirst().toString());
                i++;
            }
        }

        StackADT<ItemCura> mochila = toCruz.getMochila();
        if (!mochila.isEmpty()) {
            System.out.println("Itens na mochila do ToCruz:");

            int i = 1;
            while (!mochila.isEmpty()) {
                System.out.println("Item nº " + i + " : " + toCruz.getMochila().pop().toString());
                i++;
            }
        } else {
            System.out.println("A mochila do To Cruz esta vazia.");
        }

        System.out.println("Percurso feito pelo ToCruz:");
        String percurso = " ";

        QueueADT<Divisao> trajeto_temp = new LinkedQueue<Divisao>();
        while (!trajeto_to.isEmpty()) {
            Divisao trajeto = trajeto_to.dequeue();
            if (trajeto_to.isEmpty()) {
                percurso += trajeto.getName();
            } else {
                percurso += trajeto.getName() + " --> ";
            }

            trajeto_temp.enqueue(trajeto);
        }

        System.out.println(percurso);
        this.trajeto_to = trajeto_temp;
    }

    /**
     * Retorna uma representação em string da simulação, incluindo a versão da simulação,
     * o trajeto feito por ToCruz, o número de inimigos mortos, a vida restante de ToCruz
     * e o estado atual do edifício.
     *
     * @return Uma string a representar o estado atual da simulação.
     */
    @Override
    public String toString() {
        return "Simulacoes{" +
                "versao_simulacao=" + versao_simulacao +
                ", trajeto_to=" + trajeto_to +
                ", inimigos_dead=" + inimigos_dead +
                ", vida_to=" + vida_to +
                ", edificio=" + edificio +
                '}';
    }

    /**
     * Compara este objeto Simulacoes com outro para verificar se são iguais.
     * A comparação leva em consideração a versão da simulação, o trajeto do ToCruz,
     * a vida restante do ToCruz e o estado do edifício.
     *
     * @param o O objeto a ser comparado com o objeto atual.
     * @return True se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Simulacoes that = (Simulacoes) o;
        return versao_simulacao == that.versao_simulacao && Double.compare(vida_to, that.vida_to) == 0 && Objects.equals(trajeto_to, that.trajeto_to) && Objects.equals(edificio, that.edificio);
    }

    /**
     * Gera um código de hash único para o objeto Simulacoes, baseado na versão da simulação,
     * no trajeto do ToCruz, na vida restante do ToCruz e no estado do edifício.
     *
     * @return Um código de hash gerado para o objeto Simulacoes.
     */
    @Override
    public int hashCode() {
        return Objects.hash(versao_simulacao, trajeto_to, vida_to, edificio);
    }

    /**
     * Compara este objeto Simulacoes com outro para determinar a ordem de classificação.
     * A comparação é feita com base na vida restante do ToCruz e na versão da simulação.
     *
     * @param o O objeto Simulacoes a ser comparado.
     * @return Um número negativo, zero ou positivo dependendo da ordem dos objetos.
     */
    @Override
    public int compareTo(Simulacoes o) {
        if (this.vida_to < o.vida_to) {
            return 1;
        } else if (this.vida_to > o.vida_to) {
            return -1;
        } else {
            if (this.versao_simulacao < o.versao_simulacao) {
                return 1;
            } else if (this.versao_simulacao > o.versao_simulacao) {
                return -1;
            }
        }
        return 0;
    }
}