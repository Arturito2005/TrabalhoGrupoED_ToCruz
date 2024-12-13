package Mapa;

import Exceptions.EmptyCollectionException;
import Interfaces.DivisaoIt;
import Interfaces.IteracoesInimigo;
import Interfaces.IteracoesToCruz;
import Interfaces.UnorderedListADT;
import Items.Item;
import Items.ItemCura;
import LinkedList.LinearLinkedUnorderedList;
import Personagens.Inimigo;
import Personagens.ToCruz;

import java.util.Iterator;
import java.util.Objects;

/**
 * Representa uma divisão no mapa do jogo. Cada divisão contém características como nome,
 * inimigos presentes, itens, o personagem "ToCruz" e se é uma entrada/saída.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class Divisao implements Comparable, IteracoesInimigo, IteracoesToCruz, DivisaoIt {

    /**
     * Contador estático para geração de IDs únicos para as divisões.
     */
    private static int ID_DIVISAO_CONT = 0;

    /**
     * Identificador único da divisão.
     */
    private int id_divisao;

    /**
     * Nome da divisão.
     */
    private String name;

    /**
     * Indica se a divisão é uma entrada ou saída do edifício.
     */
    private boolean entrada_saida;

    /**
     * Representa o personagem "ToCruz" presente na divisão.
     */
    private ToCruz toCruz;

    /**
     * Lista de inimigos presentes na divisão.
     */
    private UnorderedListADT<Inimigo> inimigos;

    /**
     * Item presente na divisão.
     */
    private Item item;

    /**
     * Alvo presente na divisão.
     */
    private Alvo alvo;

    /**
     * Construtor da classe Divisao.
     *
     * @param name Nome da divisão.
     */
    public Divisao(String name) {
        this.id_divisao = ID_DIVISAO_CONT++;
        this.name = name;
        this.entrada_saida = false;
        this.alvo = null;
        this.item = null;
        this.inimigos = new LinearLinkedUnorderedList<>();
        this.toCruz = null;
    }

    /**
     * Construtor que realiza uma deep copy de uma instância da classe Divisao.
     * Este construtor cria uma nova instância de Divisao e inicializa os campos com
     * os valores fornecidos.
     * Se os parâmetros dos objetos não forem nulos, o construtor cria novas instâncias
     * desses objetos para garantir que não haja partilha de referências com o
     * objeto original.
     *
     * @param id_divisao    O identificador único da divisão.
     * @param name          O nome da divisão.
     * @param entrada_saida Indica se a divisão é de entrada ou saída (true ou false).
     * @param alvo          O alvo associado à divisão, ou null se não houver alvo.
     * @param item          O item presente na divisão, ou null se não houver item.
     * @param inimigos      Lista de inimigos presentes na divisão, nunca null.
     * @param toCruz        O personagem ToCruz associado à divisão, ou null se não houver ToCruz.
     */
    public Divisao(int id_divisao, String name, boolean entrada_saida, Alvo alvo, Item item, UnorderedListADT<Inimigo> inimigos, ToCruz toCruz) {
        this.id_divisao = id_divisao;
        this.name = name;
        this.entrada_saida = entrada_saida;


        Alvo temAlvo = null;
        if (alvo != null) {
            temAlvo = new Alvo(alvo.getId_alvo(), alvo.getNome(), alvo.isAtinigido());
        }

        this.alvo = temAlvo;

        Item tempItem = null;

        if (item != null) {
            if (item instanceof ItemCura) {
                tempItem = new ItemCura(item.getId_item(), item.isCollected(), ((ItemCura) item).getType(), ((ItemCura) item).getVida_recuperada());
            }
        }

        this.item = tempItem;

        UnorderedListADT<Inimigo> tempListaInimigos = new LinearLinkedUnorderedList<>();
        for (Inimigo inimigo : inimigos) {
            Inimigo tempInimigo = new Inimigo(inimigo.getId_personagem(), inimigo.getNome(), inimigo.getVida(), inimigo.getPoder());
            tempListaInimigos.addToRear(tempInimigo);
        }
        this.inimigos = tempListaInimigos;


        ToCruz tempToCruz = null;
        if (toCruz != null) {
            tempToCruz = new ToCruz(toCruz.getId_personagem(), toCruz.getNome(), toCruz.getVida(), toCruz.getPoder(), toCruz.getMochila());
        }
        this.toCruz = tempToCruz;
    }

    /**
     * Retorna o identificador único da divisão.
     *
     * @return O identificador único da divisão.
     */
    public int getId_divisao() {
        return id_divisao;
    }

    /**
     * Retorna o nome da divisão.
     *
     * @return O nome da divisão.
     */
    public String getName() {
        return name;
    }

    /**
     * Verifica se a divisão é uma entrada/saída.
     *
     * @return {@code true} se for entrada/saída, {@code false} caso contrário.
     */
    public boolean isEntrada_saida() {
        return entrada_saida;
    }

    /**
     * Atualiza o "estado" da divisão.
     *
     * @param entrada_saida {@code true} para definir como entrada/saída.
     */
    public void setEntrada_saida(boolean entrada_saida) {
        this.entrada_saida = entrada_saida;
    }

    /**
     * Obtém a personagem "ToCruz" presente na divisão.
     *
     * @return personagem "ToCruz".
     */
    public ToCruz getToCruz() {
        return toCruz;
    }

    /**
     * Remove a personagem "ToCruz" da divisão.
     */
    public void removeToCruz() {
        this.toCruz = null;
    }

    /**
     * Adiciona a personagem "ToCruz" à divisão.
     *
     * @param toCruz personagem "ToCruz" a ser adicionada.
     */
    public void addToCruz(ToCruz toCruz) {
        this.toCruz = toCruz;
        System.out.println("O To Cruz moveu-se para a divisão: " + this.name);
    }

    /**
     * Retorna a lista de inimigos presentes na divisão.
     *
     * @return A lista de inimigos.
     */
    public UnorderedListADT<Inimigo> getInimigos() {
        return inimigos;
    }

    /**
     * Adiciona um inimigo à divisão.
     *
     * @param inimigo O inimigo a ser adicionado.
     * @throws NullPointerException quando o inimigo recebido como paramêtro é null.
     */
    @Override
    public void addInimigo(Inimigo inimigo) throws NullPointerException {
        if (inimigo == null) {
            throw new NullPointerException("O inimigo a adicionar nao pode ser nulo");
        }
        this.inimigos.addToRear(inimigo);
    }

    /**
     * Remove um inimigo específico da divisão.
     *
     * @param inimigo O inimigo a ser removido.
     * @return O inimigo removido.
     */
    @Override
    public Inimigo removeInimigo(Inimigo inimigo) {
        Inimigo inimigo1 = null;
        try {
            inimigo1 = this.inimigos.remove(inimigo);
        } catch (EmptyCollectionException | NullPointerException e) {
            System.out.println(e.getMessage());
        }

        return inimigo1;
    }

    /**
     * Retorna o item presente na divisão.
     *
     * @return O item da divisão.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Altera o item presente na divisão.
     *
     * @param item O item para alterar.
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Retorna o alvo presente na divisão.
     *
     * @return O alvo da divisão.
     */
    public Alvo getAlvo() {
        return alvo;
    }

    /**
     * Altera o alvo presente na divisão.
     *
     * @param alvo O alvo para alterar.
     */
    public void setAlvo(Alvo alvo) {
        this.alvo = alvo;
    }

    /**
     * Verifica se o To Cruz está numa saída.
     *
     * @return {@code true} se o To Cruz estiver na saída, {@code false} caso contrário.
     */
    @Override
    public boolean isToCruzInExit() {
        boolean is = false;

        if (toCruz != null && entrada_saida) {
            is = true;
        }

        return is;
    }

    /**
     * Verifica se o To Cruz está na divisão com o alvo.
     *
     * @return {@code true} se o To Cruz estiver com o alvo, {@code false} caso contrário.
     */
    @Override
    public boolean isToCruzInDivisaoAlvo() {
        boolean is = false;

        if (toCruz != null && alvo != null) {
            is = true;
        }

        return is;
    }

    /**
     * Verifica se há inimigos na divisão.
     *
     * @return {@code true} se houver inimigos, {@code false} caso contrário.
     */
    @Override
    public boolean haveInimigo() {
        boolean have = false;

        if (!inimigos.isEmpty()) {
            have = true;
        }

        return have;
    }

    /**
     * Verifica se há um confronto entre o To Cruz e os inimigos.
     *
     * @return {@code true} se houver confronto, {@code false} caso contrário.
     */
    @Override
    public boolean haveConfronto() {
        boolean confronto = false;

        if (toCruz != null && !this.inimigos.isEmpty()) {
            confronto = true;
        }

        return confronto;
    }

    /**
     * Faz com que um inimigo ataque o To Cruz.
     *
     * @param inimigo O inimigo que vai atacar o To Cruz.
     */
    @Override
    public void attackInimigo(Inimigo inimigo) {
        long vidaTo = toCruz.getVida() - inimigo.getPoder();
        toCruz.setVida(vidaTo);

        System.out.println("O imimigo " + inimigo.getNome() + " atacou o To Cruz");

        if (toCruz.isDead()) {
            System.out.println("O inimigo " + inimigo.getNome() + " matou o To Cruz");
            toCruz.setVida(0);
        } else {
            System.out.println("To Cruz resiste ao ataque e fica com " + vidaTo + " HP");
        }
    }

    /**
     * Marca o alvo como atingido pelo To Cruz.
     */
    @Override
    public void ToCruzGetAlvo() {
        this.alvo.setAtinigido(true);
        toCruz.setColectedAlvo(true);
        System.out.println("To Cruz esta com o alvo, agora so falta sair do edificio com vida");
    }

    /**
     * Faz com que todos os inimigos ataquem o To Cruz.
     *
     * @param dead_inimigos Stack onde os inimigos mortos serão armazenados.
     */
    @Override
    public void attackToCruz(UnorderedListADT<Inimigo> dead_inimigos) {
        Iterator<Inimigo> iterator = this.inimigos.iterator();
        UnorderedListADT<Inimigo> inimigosDead = new LinearLinkedUnorderedList<>();

        System.out.println("Turno do To Cruz atacar!");
        while (iterator.hasNext()) {
            Inimigo inimigo = iterator.next();
            inimigo.setVida(inimigo.getVida() - this.toCruz.getPoder());

            if (inimigo.isDead()) {
                System.out.println("O To Cruz matou o inimigo " + inimigo.getNome());
                inimigosDead.addToRear(inimigo);
            } else {
                System.out.println("O inimigo " + inimigo.getNome() + " resitiu ao ataque do To Cruz e ficou com: " + inimigo.getVida() + " HP");
            }
        }

        while (!inimigosDead.isEmpty()) {
            Inimigo inimigo = inimigosDead.removeFirst();
            inimigo.setVida(0);
            dead_inimigos.addToRear(inimigo);
            removeInimigo(inimigo);
        }
    }

    /**
     * Utiliza o item presente na divisão, se aplicável.
     */
    @Override
    public void usarItemDivisao() {
        if (!this.item.isCollected() && (item instanceof ItemCura)) {
            try {
                this.toCruz.usarItem((ItemCura) this.item);
                this.item.setCollected(true);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("O item desta divisao ja foi usado anteriormente");
        }
    }

    /**
     * Centraliza um texto para exibição.
     *
     * @param texto   Texto a ser centralizado.
     * @param largura Largura total disponível.
     * @return Texto centralizado.
     */
    private String centralizarTexto(String texto, int largura) {
        int espacosTotal = largura - texto.length();
        int espacosEsquerda = espacosTotal / 2;
        int espacosDireita = espacosTotal - espacosEsquerda;
        String temp = "";

        for (int i = 0; i < espacosEsquerda; i++) {
            temp += " ";
        }

        temp += texto;

        for (int i = 0; i < espacosDireita; i++) {
            temp += " ";
        }
        return temp;
    }

    /**
     * Desenha a representação visual da divisão na consola.
     *
     * @return Uma string com o desenho da divisão
     */
    @Override
    public String drawnDivisao() {

        String dados_sala = "";

        if (this.toCruz != null) {
            dados_sala += this.toCruz.getNome() + " ";
        }

        if (this.item != null && item instanceof ItemCura) {
            dados_sala += ((ItemCura) item).getType() + " " + ((ItemCura) item).getVida_recuperada() + " HP ";
        }

        if (this.alvo != null) {
            dados_sala += this.alvo.getNome() + " ";
        }

        if (this.entrada_saida) {
            dados_sala += "Saida" + " ";
        }

        long damage_to = 0;
        if (!this.inimigos.isEmpty()) {
            for (Inimigo inimigo : this.inimigos) {
                dados_sala += inimigo.getNome() + " ";
                damage_to += inimigo.getPoder();
            }
        }

        int num_hifens;
        String nome_sala;
        if (damage_to > 0) {
            nome_sala = this.name + "  Custo: " + damage_to;
        } else {
            nome_sala = this.name;
        }

        if (dados_sala.length() > this.name.length()) {
            num_hifens = dados_sala.length();
        } else {
            num_hifens = nome_sala.length();
        }

        num_hifens = num_hifens + 7;
        String bordas = "";
        for (int i = 0; i < num_hifens; i++) {
            bordas = bordas + "-";
        }


        String nome_sala_central = "|" + centralizarTexto(nome_sala.trim(), bordas.length() - 2) + "|";
        String dados_sala_central = "|" + centralizarTexto(dados_sala.trim(), bordas.length() - 2) + "|";

        String resultado = bordas + "\n" +
                nome_sala_central + "\n" +
                dados_sala_central + "\n" +
                bordas;

        return resultado;
    }

    /**
     * Retorna uma representação em string da divisão.
     *
     * @return String a representar a divisão.
     */
    @Override
    public String toString() {
        return "Divisao{" +
                "id_divisao=" + id_divisao +
                ", name='" + name + '\'' +
                ", entrada_saida=" + entrada_saida +
                ", toCruz=" + toCruz +
                ", inimigos=" + inimigos +
                ", item=" + item +
                ", alvo=" + alvo +
                '}';
    }

    /**
     * Compara esta divisão com outra, com base no ID.
     *
     * @param o Outro objeto a ser comparado.
     * @return 1 se esta divisão tiver um ID maior, -1 se for menor, 0 se forem iguais.
     */
    @Override
    public int compareTo(Object o) {
        int compare = 0;
        if (this.id_divisao > ((Divisao) o).id_divisao) {
            compare = 1;
        } else if (this.id_divisao < ((Divisao) o).id_divisao) {
            compare = -1;
        }
        return compare;
    }

    /**
     * Verifica se esta divisão é igual a outra.
     *
     * @param o Outro objeto a ser comparado.
     * @return {@code true} se forem iguais, {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (this.id_divisao == ((Divisao) o).id_divisao || this.name == ((Divisao) o).name) {
            return true;
        }

        return false;
    }

    /**
     * Calcula o código hash da divisão com base no nome.
     *
     * @return O código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}