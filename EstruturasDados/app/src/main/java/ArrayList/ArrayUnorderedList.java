package ArrayList;

import Exceptions.ElementNotFoundException;
import Interfaces.UnorderedListADT;

/**
 * Implementação de uma lista não ordenada utilizando um array.
 * A classe herda de {@link ArrayList} e implementa a interface {@link UnorderedListADT}.
 * Os elementos são armazenados na lista sem uma ordem específica, e os elementos podem ser adicionados
 * no início, no final ou após um elemento existente.
 *
 * @param <T> Tipo dos elementos na lista.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    /**
     * Constrói uma lista não ordenada com a capacidade padrão.
     */
    public ArrayUnorderedList() {
        super();
    }

    /**
     * Constrói uma lista não ordenada com a capacidade especificada.
     *
     * @param capacity A capacidade inicial do array.
     */
    public ArrayUnorderedList(int capacity) {
        super(capacity);
    }

    /**
     * Adiciona um elemento ao início da lista.
     * Se o array estiver cheio, o array é expandido para acomodar o novo elemento.
     *
     * @param element O elemento a ser adicionado ao início da lista.
     */
    @Override
    public void addToFront(T element) {
        if (this.count == this.list.length) {
            expandArray();
        }

        for (int i = this.count; i > 0; i--) {
            this.list[i] = this.list[i - 1];
        }

        this.list[0] = element;
        this.count++;
        this.modCount++;
    }

    /**
     * Adiciona um elemento ao final da lista.
     * Se o array estiver cheio, o array é expandido para acomodar o novo elemento.
     *
     * @param element O elemento a ser adicionado ao final da lista.
     */
    @Override
    public void addToRear(T element) {
        if (this.count == this.list.length) {
            expandArray();
        }

        this.list[this.count++] = element;
        this.modCount++;
    }

    /**
     * Adiciona um elemento após um elemento específico na lista.
     * Se o elemento alvo não for encontrado, uma exceção {@link ElementNotFoundException} é lançada.
     *
     * @param element O elemento a ser adicionado.
     * @param target  O elemento após o qual o novo elemento será inserido.
     * @throws ElementNotFoundException Se o elemento alvo não for encontrado na lista.
     */
    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException {
        if (!contains(target)) {
            throw new ElementNotFoundException("O elemento target não existe!");
        }

        if (this.count == this.list.length) {
            expandArray();
        }

        int pos = -1;

        do {
            pos++;
        } while (!this.list[pos].equals(target));

        for (int i = this.count; i > pos; i--) {
            this.list[i] = this.list[i - 1];
        }

        this.list[pos + 1] = element;
        this.count++;
        this.modCount++;
    }
}
