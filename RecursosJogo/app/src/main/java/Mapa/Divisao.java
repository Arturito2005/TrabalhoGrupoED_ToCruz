package Mapa;

import java.util.Objects;

/*
* Alterar provavelemten o importar
* */
public class Divisao implements Comparable {

    private static int ID_DIVISAO_CONT = 0;

    private int id_divisao;

    private String name;

    private boolean entrada_saida;

    public Divisao(String name, boolean entradas_saidas) {
        this.id_divisao = ID_DIVISAO_CONT++;
        this.name = name;
        this.entrada_saida = entradas_saidas;
    }

    public Divisao(String name) {
        this.id_divisao = ID_DIVISAO_CONT++;
        this.name = name;
        this.entrada_saida = false;
    }

    public Divisao() {
        this.id_divisao = ID_DIVISAO_CONT++;
        this.name = "";
        this.entrada_saida = false;
    }

    public int getId_divisao() {
        return id_divisao;
    }

    public String getName() {
        return name;
    }

    public boolean isEntrada_saida() {
        return entrada_saida;
    }

    public void setEntrada_saida(boolean entrada_saida) {
        this.entrada_saida = entrada_saida;
    }

    @Override
    public String toString() {
        return "Divisao{" +
                "id_divisao=" + id_divisao +
                ", name='" + name + '\'' +
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

        if(!(o instanceof Divisao)) {
            return false;
        }

        if(this.id_divisao == ((Divisao) o).id_divisao) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_divisao, name);
    }

    @Override
    public int compareTo(Object o) {
        int compare = 0;
        if(this.id_divisao > ((Divisao) o).id_divisao) {
            compare = 1;
        } else if (this.id_divisao < ((Divisao) o).id_divisao) {
            compare = -1;
        }
        return compare;
    }
}
