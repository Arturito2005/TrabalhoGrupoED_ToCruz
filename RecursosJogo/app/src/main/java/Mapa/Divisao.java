package Mapa;

import java.util.Objects;

public class Divisao {

    private static int ID_DIVISAO_CONT = 0;

    private int id_divisao;

    private String name;

    private boolean entradas_saidas;

    public Divisao(String name, boolean entradas_saidas) {
        this.id_divisao = ID_DIVISAO_CONT++;
        this.name = name;
        this.entradas_saidas = entradas_saidas;
    }

    public int getId_divisao() {
        return id_divisao;
    }

    public String getName() {
        return name;
    }

    public boolean isEntradas_saidas() {
        return entradas_saidas;
    }

    @Override
    public String toString() {
        return "Divisao{" +
                "id_divisao=" + id_divisao +
                ", name='" + name + '\'' +
                ", entradas_saidas=" + entradas_saidas +
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

        if(this.name.equals(((Divisao) o).name) && this.entradas_saidas == ((Divisao) o).entradas_saidas) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_divisao, name, entradas_saidas);
    }
}
