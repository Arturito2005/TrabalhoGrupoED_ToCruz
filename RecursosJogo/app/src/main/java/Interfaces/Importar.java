package Interfaces;

import Missoes.Missao;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface que define o contrato para importar dados e gerar um mapa.
 * A interface é utilizada por classes que precisam ler um arquivo de dados
 * de missões e gerar um objeto do tipo {@link Missao} a partir desse arquivo.
 *
 * <p>A implementação de classes que utilizam esta interface deve fornecer
 * a lógica para carregar o mapa de missões a partir de um arquivo localizado
 * no caminho fornecido.</p>
 *
 * @author Artur
 * @version 1.0
 */
public interface Importar {
    public Missao gerarMapa(String path) throws FileNotFoundException, IOException;
}
