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
 * @author Francisco Oliveria
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
     * Construtor de deepCopy da classe Divisao
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

    public int getId_divisao() {
        return id_divisao;
    }

    /**
     * Obtém o nome da divisão.
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
     * Define se a divisão é uma entrada/saída.
     *
     * @param entrada_saida {@code true} para definir como entrada/saída.
     */
    public void setEntrada_saida(boolean entrada_saida) {
        this.entrada_saida = entrada_saida;
    }

    /**
     * Obtém o personagem "ToCruz" presente na divisão.
     *
     * @return O personagem "ToCruz".
     */
    public ToCruz getToCruz() {
        return toCruz;
    }

    /**
     * Remove o personagem "ToCruz" da divisão.
     */
    public void removeToCruz() {
        this.toCruz = null;
    }

    /**
     * Adiciona o personagem "ToCruz" à divisão.
     *
     * @param toCruz O personagem "ToCruz" a ser adicionado.
     */
    public void addToCruz(ToCruz toCruz) {
        this.toCruz = toCruz;
    }

    /**
     * Obtém a lista de inimigos presentes na divisão.
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
     * @throws NullPointerException quando o inimigo recebido como parametro é null
     */
    @Override
    public void addInimigo(Inimigo inimigo) throws NullPointerException {
        if(inimigo == null) {
            throw new NullPointerException("O inimigo a adicionar não pode ser nulo");
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

        return null;
    }

    /**
     * Obtém o item presente na divisão.
     *
     * @return O item da divisão.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Define o item presente na divisão.
     *
     * @param item O item a ser adicionado.
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Obtém o alvo presente na divisão.
     *
     * @return O alvo da divisão.
     */
    public Alvo getAlvo() {
        return alvo;
    }

    /**
     * Define o alvo presente na divisão.
     *
     * @param alvo O alvo a ser definido.
     */
    public void setAlvo(Alvo alvo) {
        this.alvo = alvo;
    }

    /**
     * Verifica se "ToCruz" está em uma saída.
     *
     * @return {@code true} se "ToCruz" estiver na saída, {@code false} caso contrário.
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
     * Verifica se "ToCruz" está na divisão com o alvo.
     *
     * @return {@code true} se "ToCruz" estiver com o alvo, {@code false} caso contrário.
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
     * Verifica se há um confronto entre "ToCruz" e os inimigos.
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
     * Faz com que um inimigo ataque "ToCruz".
     *
     * @param inimigo O inimigo que atacará "ToCruz".
     */
    @Override
    public void attackInimigo(Inimigo inimigo) {
        double vidaTo = toCruz.getVida() - inimigo.getPoder();
        toCruz.setVida(vidaTo);

        System.out.println("O imimigo" + inimigo.getNome() + " atacou o To Cruz");

        if (toCruz.isDead()) {
            System.out.println("O inimigo " + inimigo.getNome() + "matou o To Cruz");
            toCruz.setVida(0);
        } else {
            System.out.println("To Cruz resiste ao ataque e fica com " + vidaTo + " HP");
        }
    }

    /**
     * Marca o alvo como atingido por "ToCruz".
     */
    @Override
    public void ToCruzGetAlvo() {
        this.alvo.setAtinigido(true);
        System.out.println("To Cruz está com o alvo, agora só falta sair do edificio com vida");
    }

    /**
     * Faz com que todos os inimigos ataquem "ToCruz".
     *
     * @param dead_inimigos Pilha onde inimigos mortos serão armazenados.
     */
    @Override
    public void attackToCruz(UnorderedListADT<Inimigo> dead_inimigos) {
        Iterator<Inimigo> iterator = this.inimigos.iterator();
        UnorderedListADT<Inimigo> inimigosDead = new LinearLinkedUnorderedList<>();

        while (iterator.hasNext()) {
            Inimigo inimigo = iterator.next();
            inimigo.setVida(inimigo.getVida() - this.toCruz.getPoder());

            if (inimigo.isDead()) {
                System.out.println("O inimigo " + inimigo.getNome());
                inimigosDead.addToRear(inimigo);
            } else {
                System.out.println("Vida do inimigo " + inimigo.getNome() + ": " + inimigo.getVida());
            }
        }

        while (!inimigosDead.isEmpty()) {
            Inimigo inimigo = inimigosDead.removeFirst();
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
            System.out.println("O item desta divisao já foi usado anteriormente");
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
     * Desenha a representação visual da divisão no console.
     */
    @Override
    public void drawnDivisao() {
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

        if (!this.inimigos.isEmpty()) {
            for (Inimigo inimigo : this.inimigos) {
                dados_sala += inimigo.getNome() + " ";
            }
        }

        int num_hifens;
        if (dados_sala.length() > this.name.length()) {
            num_hifens = dados_sala.length();
        } else {
            num_hifens = this.name.length();
        }

        num_hifens = num_hifens + 7;
        String bordas = "";
        for (int i = 0; i < num_hifens; i++) {
            bordas = bordas + "-";
        }

        String nome_sala_central = "|" + centralizarTexto(this.name.trim(), bordas.length() - 2) + "|";
        String dados_sala_central = "|" + centralizarTexto(dados_sala.trim(), bordas.length() - 2) + "|";

        System.out.println(bordas);
        System.out.println(nome_sala_central);
        System.out.println(dados_sala_central);
        System.out.println(bordas);
    }

    /**
     * Retorna uma representação em string da divisão.
     *
     * @return String representando a divisão.
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
     * Compara esta divisão com outra com base no ID.
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