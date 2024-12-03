package Interfaces;

import Missoes.Missao;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Importar {
    public Missao gerarMapa(String path) throws FileNotFoundException, IOException;
}
