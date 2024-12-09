package Interfaces;

/**
 * Interface que define o contrato para exportação de dados.
 * Esta interface deve ser implementada por classes que necessitem
 * exportar informações para um ficheiro em um formato específico.
 *
 * @author Artur Pinto
 * @author Francisco
 * @version 1.0
 */
public interface ExportarInt {

    /**
     * Metodo que permite exportar os dados do percurso do ToCruz, para um ficherio
     *
     * @param path o caminho para guardar o ficheiro
     */
    public void exportarDados(String path);
}
