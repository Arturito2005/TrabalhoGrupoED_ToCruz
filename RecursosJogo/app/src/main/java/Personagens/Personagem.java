package Personagens;

import Mapa.Divisao;

/**
 * Classe abstrata que representa cada personagem no jogo
 *
 * @author Artur
 * @version 1.0
 */
public abstract class Personagem {
    private static int ID_PERSONAGEM_CONT = 0; //Variável estática para controlar o ID único para cada personagem.

    private static final int DEFAULT_VIDA = 100; //Variável estasticas para definir um valor default da vida do personagem

    private static final int DEFAULT_PODER = 40; //Variável estasticas para definir um valor default do poder do personagem

    private int id_personagem; //Identificador único de cada personagem.

    private String nome;  //Nome do personagem.
    
    private double vida; //Quantidade de vida que o personagem possui.
    
    private double poder; //Poder do personagem.
    
    private Divisao divisao; //Divisão do mapa onde o personagem está localizado.

    private boolean confronto; //Indica se o personagem está em confronto.

    /**Construtor principal com todos os atributos.*/
    public Personagem(String nome, double vida, double poder , Divisao divisao) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = vida;
        this.poder = poder;
        this.divisao = divisao;
        this.confronto = false;
    }

    /**Construtor com nome e divisão, usa os valores padrão para vida e poder.*/
    public Personagem(String nome, Divisao divisao) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = DEFAULT_PODER;
        this.divisao = divisao;
        this.confronto = false;
    }

    /**Construtor apenas com nome, sem divisão especificada, usa valores padrão.*/
    public Personagem(String nome) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = DEFAULT_PODER;
        this.divisao = null;
        this.confronto = false;
    }

    /**Construtor com nome, poder e divisão, usa o valor padrão para vida.*/
    public Personagem(String nome, double poder, Divisao divisao) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = poder;
        this.divisao = divisao;
    }

    /**
     * Retorna o id do personagem.
     *
     *
     * @return O identificador do personagem.
     */
    public int getId_personagem() {
        return id_personagem;
    }

    /**
     * Retorna o nome do personagem.
     *
     * @return O nome do personagem.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a quantidade de vida do personagem.
     *
     * @return A quantidade de vida do personagem.
     */
    public double getVida() {
        return vida;
    }

    /**
     * Setter para alterar a quantidade de vida do personagem.
     *
     * @param vida O novo valor de vida a ser atribuído ao personagem.
     */
    public void setVida(double vida) {
        this.vida = vida;
    }

    /**
     * Retorna o poder do personagem.
     *
     * @return O poder do personagem.
     */
    public double getPoder() {
        return poder;
    }

    /**
     * Retorna a divisão à qual o personagem pertence.
     *
     * @return A divisão do personagem.
     */
    public Divisao getDivisao() {
        return divisao;
    }

    /**
     * Altera a divisão do personagem.
     *
     * @param divisao A nova divisão a ser atribuída ao personagem.
     */
    public void setDivisao(Divisao divisao) {
        this.divisao = divisao;
    }

    /**
     * Retorna o status de confronto do personagem.
     *
     * @return `true` se o personagem está em confronto, caso contrário `false`.
     */
    public boolean isConfronto() {
        return confronto;
    }

    /**
     * Define o status de confronto do personagem.
     *
     * @param confronto O novo valor para o status de confronto do personagem.
     */
    public void setConfronto(boolean confronto) {
        this.confronto = confronto;
    }

    /**
     * Retorna se o personagem está morto.
     *
     * @return `true` se o personagem está morto, caso contrário `false`.
     */
    public boolean isDead() {
        boolean dead = false;

        if(this.vida <= 0) {
            dead = true;
        }

        return dead;
    }

    /**
     * Retorna uma String com os dados do Personagem.
     *
     * @return toda a informação do Personagem
     */
    @Override
    public String toString() {
        return "Personagem{" +
                "nome='" + this.nome + '\'' +
                ", vida=" + this.vida +
                ", poder=" + this.poder +
                ", divisao=" + this.divisao +
                '}';
    }
}
