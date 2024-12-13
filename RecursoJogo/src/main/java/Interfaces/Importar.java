package Interfaces;

import Missoes.Missao;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface que define permite importar e gerar o mapa do jogo.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 *
 * @version 1.0
 */
public interface Importar {

    /**
     * Gera um objeto do tipo {@link Missao} que representa a missão do To Cruz.
     *
     * @param path o caminho para o ficheiro de entrada que contém os dados das missões.
     * @return um objeto {@link Missao} que representa o mapa gerado a partir dos dados.
     * @throws FileNotFoundException se o ficheiro especificado não for encontrado.
     * @throws IOException se ocorrerem erros de input/output durante a leitura do ficheiro.
     */
    public Missao gerarMapa(String path) throws NullPointerException, FileNotFoundException, IOException;
}
