package Interfaces;

import Missoes.Missao;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface que define perimite importar e gerar o mapa do jogo.
 *
 * <p>A implementação de classes que utilizam esta interface deve fornecer
 * a lógica para carregar o mapa de missões a partir de um ficheiro localizado
 * no caminho fornecido.</p>
 *
 * @author Artur
 * @version 1.0
 */
public interface Importar {

    /**
     * Gera um objeto do tipo {@link Missao} representando a missão do To Cruz.
     *
     * @param path o caminho para o ficheiro de entrada contendo os dados das missões.
     * @return um objeto {@link Missao} representando o mapa gerado a partir dos dados.
     * @throws FileNotFoundException se o ficheiro especificado não for encontrado.
     * @throws IOException           se ocorrerem erros de E/S durante a leitura do ficheiro.
     */
    public Missao gerarMapa(String path) throws FileNotFoundException, IOException;
}
