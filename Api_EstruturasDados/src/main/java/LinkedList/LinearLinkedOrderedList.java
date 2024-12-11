package LinkedList;

import Interfaces.OrderedListADT;
import Nodes.LinearNode;

/**
 * Implementação de uma lista ligada ordenada, que mantém os elementos em ordem crescente.
 * Esta classe estende a classe {@link LinearLinkedList} e implementa a interface {@link OrderedListADT}.
 *
 * @param <T> o tipo dos elementos na lista. Os elementos devem ser comparáveis entre si.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class LinearLinkedOrderedList<T> extends LinearLinkedList<T> implements OrderedListADT<T> {

    /**
     * Constrói uma nova lista ligada ordenada, inicializando a lista vazia.
     */
    public LinearLinkedOrderedList() {
        super();
    }

    /**
     * Adiciona um novo elemento à lista, mantendo a ordem crescente.
     * O elemento é inserido de maneira que a lista permaneça ordenada.
     * A lista é percorrida até encontrar a posição correta para o novo elemento.
     *
     * @param element o elemento a ser adicionado à lista. Deve ser comparável com outros elementos.
     * @throws ClassCastException se o elemento não for comparável com outros da lista.
     */
    @Override
    public void add(T element) {
        Comparable<T> temp = (Comparable<T>) element;
        LinearNode<T> new_node = new LinearNode<>(element);

        if (this.head == null) {
            this.tail = new_node;
            this.head = new_node;
        } else {
            LinearNode<T> current_node = this.head;
            LinearNode<T> previous_node = null;

            while (current_node != null && temp.compareTo(current_node.getElement()) > 0) {
                previous_node = current_node;
                current_node = current_node.getNext();
            }

            if (current_node == this.head) {
                new_node.setNext(this.head);
                this.head = new_node;
            }  else if (current_node == null) {
                previous_node.setNext(new_node);
                this.tail = new_node;
            } else {
                previous_node.setNext(new_node);
                new_node.setNext(current_node);
            }
        }

        this.count++;
        this.modCount++;
    }
}
