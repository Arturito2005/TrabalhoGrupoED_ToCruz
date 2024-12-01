package Interfaces;

import Missoes.Missao;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Importar {
    public void importarDados(String path, Missao missao) throws FileNotFoundException, IOException;
}
