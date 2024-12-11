package Interfaces;

/**
 * Esta interface define o ADT para uma lista ordenada genérica. É uma
 * sub-interface de {@code ListADT<T>}, adicionando a funcionalidade específica de
 * inserir elementos em uma ordem definida.
 * <p>
 * A implementação desta interface deve garantir que os elementos sejam mantidos
 * na ordem correta após cada inserção.
 *
 * @param <T> o tipo dos elementos armazenados nesta lista ordenada
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public interface OrderedListADT<T> extends ListADT<T> {

    /**
     * Adiciona o elemento especificado a esta lista na localização apropriada.
     *
     * @param element o elemento a ser adicionado a esta lista
     */
    public void add(T element);
}
