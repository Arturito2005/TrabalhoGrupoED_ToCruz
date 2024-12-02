package Personagens;

import Interfaces.InteracoesPersonagens;
import Mapa.Divisao;

public abstract class Personagem implements InteracoesPersonagens {
    private static int ID_PERSONAGEM_CONT = 0; //Variável estática para controlar o ID único para cada personagem.

    private static final int DEFAULT_VIDA = 100; //Variável estasticas para definir um valor default da vida do personagem

    private static final int DEFAULT_PODER = 40; //Variável estasticas para definir um valor default do poder do personagem

    private int id_personagem; //Identificador único de cada personagem.

    private String nome;  //Nome do personagem.
    
    private double vida; //Quantidade de vida que o personagem possui.
    
    private double poder; //Poder do personagem.
    
    private Divisao divisao; //Divisão do mapa onde o personagem está localizado.

    private boolean confronto; //Indica se o personagem está em confronto.

    //Construtor principal com todos os atributos.
    public Personagem(String nome, double vida, double poder , Divisao divisao) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = vida;
        this.poder = poder;
        this.divisao = divisao;
        this.confronto = false;
    }

    //Construtor com nome e divisão, usa os valores padrão para vida e poder.
    public Personagem(String nome, Divisao divisao) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = DEFAULT_PODER;
        this.divisao = divisao;
        this.confronto = false;
    }

    //Construtor apenas com nome, sem divisão especificada, usa valores padrão.
    public Personagem(String nome) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = DEFAULT_PODER;
        this.divisao = null;
        this.confronto = false;
    }

    //Construtor com nome, poder e divisão, usa o valor padrão para vida.
    public Personagem(String nome, double poder, Divisao divisao) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = poder;
        this.divisao = divisao;
    }

    //Métodos Getters e Setters para acessar e modificar os atributos.
    public int getId_personagem() {
        return id_personagem;
    }

    public String getNome() {
        return nome;
    }

    public double getVida() {
        return vida;
    }

    public void setVida(double vida) {
        this.vida = vida;
    }
    
    public double getPoder() {
        return poder;
    }

    public Divisao getDivisao() {
        return divisao;
    }

    public void setDivisao(Divisao divisao) {
        this.divisao = divisao;
    }

    public void setVida(Divisao divisao) {
        this.divisao = divisao;
    }

    public boolean isConfronto() {
        return confronto;
    }

    public void setConfronto(boolean confronto) {
        this.confronto = confronto;
    }

    public boolean isDead() {
        boolean dead = false;

        if(this.vida <= 0) {
            dead = true;
        }

        return dead;
    }

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
