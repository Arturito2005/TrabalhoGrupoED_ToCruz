package ArrayList;

import Interfaces.List.ArrayUnorderedADT;

/**
 * Implementação de uma lista não ordenada utilizando um array.
 * A classe herda de {@link ArrayUnorderedList} e implementa a interface {@link ArrayUnorderedADT}.
 * Os elementos são armazenados na lista sem uma ordem específica.
 *
 * @param <T> Tipo dos elementos na lista.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class ExtendedArrayUnordered<T> extends ArrayUnorderedList<T> implements ArrayUnorderedADT<T> {

    /**
     * Constrói uma lista não ordenada com a capacidade padrão.
     */
    public ExtendedArrayUnordered() {
        super();
    }

    /**
     * Encontra e retorna o elemento na posição especificada do array.
     * Caso o índice seja inválido (fora dos limites da lista), uma exceção {@link ArrayIndexOutOfBoundsException}
     * é lançada.
     *
     * @param index O índice do elemento a ser encontrado.
     * @return O elemento na posição especificada do array.
     * @throws ArrayIndexOutOfBoundsException Se o índice estiver fora dos limites da lista.
     */
    @Override
    public T find(int index) throws ArrayIndexOutOfBoundsException {
        if(index < 0 || index >= size()) {
            throw new ArrayIndexOutOfBoundsException("O index introduzido supera os limites do Array");
        }

        return this.list[index];
    }
}
