package Personagens;

public abstract class Personagem {    
    private String nome;
    
    private double vida;
    
    private double poder;
    
    //private Divisao divisao;

    public Personagem(String nome, double vida, double poder /*, Divisao divisao*/) {
        this.nome = nome;
        this.vida = vida;
        this.poder = poder;
        //this.divisao = divisao;
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
    
    /*
    public Divisao getDivisao() {
        return divisao;
    }
    
    public void setVida(Divisao divisao) {
        this.divisao = divisao;
    } 
    */
    
    @Override
    public String toString() {
        return "";
    }
}
