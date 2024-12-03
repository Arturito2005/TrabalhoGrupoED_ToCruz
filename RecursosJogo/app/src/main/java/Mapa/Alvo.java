package Mapa;

import java.util.Objects;

public class Alvo {

    private static int ID_ALVO_CONT = 0;

    private int id_alvo;

    private String nome;

    private Divisao divisao;

    private boolean atinigido;

    public Alvo(String nome, Divisao divisao) {
        this.id_alvo = ID_ALVO_CONT++;
        this.nome = nome;
        this.divisao = divisao;
        this.atinigido = false;
    }

    public Alvo() {
        this.id_alvo = ID_ALVO_CONT++;
        this.nome = "";
        this.divisao = new Divisao();
    }

    public int getId_alvo() {
        return id_alvo;
    }

    public String getNome() {
        return nome;
    }

    public Divisao getDivisao() {
        return divisao;
    }

    public boolean isAtinigido() {
        return atinigido;
    }

    public void setAtinigido(boolean atinigido) {
        this.atinigido = atinigido;
    }

    @Override
    public String toString() {
        return "Alvo{" +
                "id_alvo=" + id_alvo +
                ", nome='" + nome + '\'' +
                ", divisao=" + divisao +
                ", atinigido=" + atinigido +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Alvo alvo = (Alvo) o;

        if(id_alvo == alvo.id_alvo) {
            return true;
        }
        return Objects.equals(nome, alvo.nome) && Objects.equals(divisao, alvo.divisao);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nome);
    }
}
