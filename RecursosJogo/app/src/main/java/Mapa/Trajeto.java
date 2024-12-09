package Mapa;

public class Trajeto implements Comparable<Trajeto> {
    private long versao;

    private Divisao divisao;

    private double pontos_Vida;

    public Trajeto(long versao, Divisao divisao, double pontos_Vida) {
        this.versao = versao;
        this.divisao = divisao;
        this.pontos_Vida = pontos_Vida;
    }

    @Override
    public int compareTo(Trajeto o) {
        int value = 0;
        if (this.pontos_Vida > o.pontos_Vida) {
            value = 1;
        } else if (this.pontos_Vida < o.pontos_Vida) {
            value = -1;
        } else {
            if(this.versao > o.versao) {
                value = 1;
            } else if (this.versao < o.versao) {
                value = -1;
            }
        }

        return value;
    }

    @Override
    public String toString() {
        return "Trajeto{" +
                "versao=" + versao +
                ", divisao=" + divisao +
                ", pontos_Vida=" + pontos_Vida +
                '}';
    }
}
