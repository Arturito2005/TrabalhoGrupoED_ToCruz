package Importar;

import Missoes.Missao;

import java.io.IOException;

public class MimiTestImportar {

    public static void main(String[] args) {
        ImportarMapa importar = new ImportarMapa();
        Missao missao = null;
        try {
            missao = importar.gerarMapa("C:/Users/artur/OneDrive/Desktop/universidade/2_ano/ED/TrabalhoGrupoED_ToCruz/JSON/FicheiroMissao.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(missao.toString());
    }
}
