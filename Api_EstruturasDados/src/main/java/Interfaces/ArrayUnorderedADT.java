package Interfaces;

/**
 * Interface que define os métodos específicos para uma lista não ordenada baseada em array.
 * Esta interface estende a interface {@link UnorderedListADT}, adicionando a funcionalidade de
 * encontrar um elemento pelo índice.
 *
 * @param <T> o tipo dos elementos na lista.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public interface ArrayUnorderedADT<T> extends UnorderedListADT<T> {

    /**
     * Encontra o elemento no índice especificado na lista.
     *
     * @param index o índice do elemento a ser encontrado.
     * @return o elemento no índice especificado.
     * @throws ArrayIndexOutOfBoundsException se o índice estiver fora dos limites da lista.
     */
    public T find(int index) throws ArrayIndexOutOfBoundsException;
}
