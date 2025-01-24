package Mapa;

import Exceptions.EmptyCollectionException;
import Exceptions.ContainElementException;
import Interfaces.Mapa.DivisaoIt;
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
public class Divisao implements Comparable, DivisaoIt {

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
    private UnorderedListADT<Item> itens;

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
        this.itens = new LinearLinkedUnorderedList<>();
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
    public Divisao(int id_divisao, String name, boolean entrada_saida, Alvo alvo, UnorderedListADT<Item> item, UnorderedListADT<Inimigo> inimigos, ToCruz toCruz) {
        this.id_divisao = id_divisao;
        this.name = name;
        this.entrada_saida = entrada_saida;


        Alvo temAlvo = null;
        if (alvo != null) {
            temAlvo = new Alvo(alvo.getId_alvo(), alvo.getNome());
        }

        this.alvo = temAlvo;

        UnorderedListADT<Item> tempListaItens = new LinearLinkedUnorderedList<>();
        for (Item itens : tempListaItens) {
            Item tempItem = null;

            if (itens instanceof ItemCura) {
                tempItem = new ItemCura(itens.getId_item(), ((ItemCura) itens).getType(), ((ItemCura) itens).getVida_recuperada());
            }

            tempListaItens.addToRear(tempItem);
        }
        this.itens = tempListaItens;

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
     * Retorna a lista de inimigos presentes na divisão.
     *
     * @return A lista de inimigos.
     */
    public UnorderedListADT<Inimigo> getInimigos() {
        return inimigos;
    }

    public UnorderedListADT<Item> getItens() {
        return itens;
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
     * Adiciona a personagem "ToCruz" à divisão.
     *
     * @param toCruz personagem "ToCruz" a ser adicionada.
     */
    @Override
    public void addToCruz(ToCruz toCruz) {
        this.toCruz = toCruz;
        System.out.println("O To Cruz moveu-se para a divisão: " + this.name);
    }

    @Override
    public ToCruz removeToCruz() {
        return this.toCruz = null;
    }

    /**
     * Adiciona um inimigo à divisão.
     *
     * @param inimigo O inimigo a ser adicionado.
     * @throws NullPointerException quando o inimigo recebido como paramêtro é null.
     */
    @Override
    public void addInimigo(Inimigo inimigo) throws NullPointerException, ContainElementException {
        if (inimigo == null) {
            throw new NullPointerException("O inimigo a adicionar nao pode ser nulo");
        }

        if (containInimigo(inimigo)) {
            throw new ContainElementException("O inimigo ( " + inimigo.getNome() + " ) já está na divisão");
        }

        this.inimigos.addToRear(inimigo);
    }

    @Override
    public boolean containInimigo(Inimigo inimigo) {
        boolean found = false;

        Iterator<Inimigo> itrInimigos = this.inimigos.iterator();
        while (itrInimigos.hasNext() && !found) {
            Inimigo tempInimigo = itrInimigos.next();

            if (tempInimigo.equals(inimigo)) {
                found = true;
            }
        }

        return found;
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

    @Override
    public void addItem(Item item) throws NullPointerException, ContainElementException {
        if (item == null) {
            throw new NullPointerException("O item nao pode ser nulo");
        }

        if(containItem(item)) {
            throw new ContainElementException("O item introduzido já existe na divisao");
        }

        this.itens.addToRear(item);
    }

    @Override
    public boolean containItem(Item item) {
        boolean found = false;

        Iterator<Item> itrItem = this.itens.iterator();
        while (itrItem.hasNext() && !found) {
            Item tempItem = itrItem.next();

            if (tempItem.equals(item)) {
                found = true;
            }
        }

        return found;
    }

    @Override
    public Item removeItem(Item item) {
        Item item1 = null;

        try {
            item1 = this.itens.remove(item);
        } catch (EmptyCollectionException | NullPointerException e) {
            System.out.println(e.getMessage());
        }

        return item1;
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
                ", itens=" + itens +
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