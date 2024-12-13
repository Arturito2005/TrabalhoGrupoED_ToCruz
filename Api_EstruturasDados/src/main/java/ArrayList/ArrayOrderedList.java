package ArrayList;

import Interfaces.OrderedListADT;

/**
 * Implementação de uma lista ordenada utilizando um array.
 * A classe herda de {@link ArrayList} e implementa a interface {@link OrderedListADT}.
 * Os elementos são mantidos em ordem crescente à medida que são adicionados à lista.
 *
 * @param <T> Tipo dos elementos na lista, que deve ser comparável com outros elementos do mesmo tipo.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    /**
     * Constrói uma lista ordenada com a capacidade default.
     */
    public ArrayOrderedList() {
        super();
    }

    /**
     * Constrói uma lista ordenada com a capacidade especificada.
     *
     * @param capacity A capacidade inicial do array que armazena os elementos da lista.
     */
    public ArrayOrderedList(int capacity) {
        super(capacity);
    }

    /**
     * Adiciona um elemento à lista, mantendo a ordem crescente.
     * Caso a lista esteja cheia, o array de armazenamento é expandido.
     * O elemento é inserido na posição correta, movendo os elementos posteriores para abrir espaço.
     *
     * @param element O elemento a ser adicionado à lista.
     */
    @Override
    public void add(T element) {
        if (list.length == this.count) {
            expandArray();
        }

        Comparable<T> temp = (Comparable<T>) element;
        int pos = 0;

        while ((this.list[pos] == null || temp.compareTo(this.list[pos]) > 0) && pos != this.count) {
            pos++;
        }

        if (this.count != pos) {
            for (int y = this.count; y > pos; y--) {
                list[y] = list[y - 1];
            }
        }

        this.list[pos] = element;
        this.count++;
        this.modCount++;
    }
}
