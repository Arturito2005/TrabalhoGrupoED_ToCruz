package Personagens;

import Interfaces.InteracoesPersonagens;
import Mapa.Divisao;

public abstract class Personagem implements InteracoesPersonagens {
    private static int ID_PERSONAGEM_CONT = 0;

    private static final int DEFAULT_VIDA = 100;

    private static final int DEFAULT_PODER = 40;

    private int id_personagem;

    private String nome;
    
    private double vida;
    
    private double poder;
    
    private Divisao divisao;

    public Personagem(String nome, double vida, double poder , Divisao divisao) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = vida;
        this.poder = poder;
        this.divisao = divisao;
    }

    public Personagem(String nome, Divisao divisao) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = DEFAULT_PODER;
        this.divisao = divisao;
    }

    public Personagem(String nome) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = DEFAULT_PODER;
        this.divisao = null;
    }

    public Personagem(String nome, double poder, Divisao divisao) {
        this.id_personagem = ID_PERSONAGEM_CONT++;
        this.nome = nome;
        this.vida = DEFAULT_VIDA;
        this.poder = poder;
        this.divisao = divisao;
    }

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
    
    public void setVida(Divisao divisao) {
        this.divisao = divisao;
    }

    @Override
    public abstract void mudarDivisao();

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
