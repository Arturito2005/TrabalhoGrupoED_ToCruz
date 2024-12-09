package Missoes;

import Interfaces.Importar;
import Interfaces.UnorderedListADT;
import LinkedList.LinearLinkedUnorderedList;

public class Missoes {

    private static int Tot_Missoes = 0;

    private int contMissoes;

    private UnorderedListADT<Missao> listaMissao;

    public Missoes() {
        listaMissao = new LinearLinkedUnorderedList<Missao>();
    }

    public void ImportarMissoes( ){
        ImportarMapa importar = new ImportarMapa();

        //Meter para selecionar Ficheiros a importar
        do {

        } while()

        //Selecionar a Missao a jogar
        do {

        } while()
    }
}
