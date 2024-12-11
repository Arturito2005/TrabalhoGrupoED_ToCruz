package Interfaces;

import Missoes.Missao;

/**
 * Interface que define o contrato para exportação de dados.
 * Esta interface deve ser implementada por classes que necessitem
 * exportar informações para um ficheiro em um formato específico.
 * As classes que implementam esta interface serão responsáveis por
 * fornecer a lógica de exportação dos dados de forma que seja possível
 * salvar informações sobre o percurso ou versões de um objeto (como o ToCruz).
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 *
 * @version 1.0
 */
public interface ExportarInt {

    /**
     * Método responsável por exportar os dados das versões do percurso do ToCruz
     * para um ficheiro em um formato determinado pela implementação.
     * A exportação incluirá todas as versões presentes na lista fornecida,
     * sendo que o caminho do ficheiro de destino será indicado pelo parâmetro
     * 'path'.
     *
     * @param path O caminho completo para salvar o ficheiro exportado.
     *             Este parâmetro define onde o ficheiro será armazenado no sistema de arquivos.
     * @param listaVersoes A lista de versões do percurso do ToCruz que será exportada.
     *                     Este parâmetro contém os dados que devem ser gravados no ficheiro.
     */
    public void exportarVersoes(String path, UnorderedListADT<Missao> listaVersoes);
}
