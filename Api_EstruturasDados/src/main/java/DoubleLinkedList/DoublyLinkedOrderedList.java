package DoubleLinkedList;

import Exceptions.EmptyCollectionException;
import Interfaces.OrderedListADT;

/**
 * Esta classe representa uma lista duplamente ligada ordenada.
 * Ela implementa a interface {@link OrderedListADT} e herda de {@link DoublyLinkedList}.
 * Elementos são armazenados na lista de forma ordenada de acordo com a ordem definida
 * pela interface {@link Comparable}.
 *
 * @param <T> Tipo dos elementos na lista, que deve ser comparável com outros elementos do mesmo tipo.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class DoublyLinkedOrderedList<T> extends DoublyLinkedList<T> implements OrderedListADT<T> {

    /**
     * Constrói uma nova lista duplamente ligada ordenada vazia.
     */
    public DoublyLinkedOrderedList() {
        super();
    }

    /**
     * Adiciona um elemento na lista de forma ordenada.
     * O novo elemento será inserido na posição correta para manter a ordem da lista.
     *
     * @param element O elemento a ser adicionado à lista.
     * @throws ClassCastException Caso o elemento não seja comparável com outros elementos.
     */
    @Override
    public void add(T element) {
        Comparable<T> temp = (Comparable<T>) element;
        DoubleNode<T> new_node = new DoubleNode<>(element);

        if (this.head == null) {
            this.tail = new_node;
            this.head = new_node;
        } else {
            DoubleNode<T> current_node = this.head;

            while (current_node != null && temp.compareTo(current_node.getElement()) > 0) {
                current_node = current_node.getNext_ele();
            }

            if (current_node == this.head) {
                new_node.setNext_ele(this.head);
                this.head.setPrevious_ele(new_node);
                this.head = new_node;
            } else if (current_node == null) {
                new_node.setPrevious_ele(tail);
                this.tail.setNext_ele(new_node);
                this.tail = new_node;
            } else {
                DoubleNode<T> previous_node = current_node.getPrevious_ele();
                new_node.setNext_ele(current_node);
                new_node.setPrevious_ele(previous_node);

                if (previous_node != null) {
                    previous_node.setNext_ele(new_node);
                }
                current_node.setPrevious_ele(new_node);
            }
        }

        this.count++;
        this.modCount++;
    }

    /**
     * Inverte a ordem dos elementos na lista e retorna uma nova lista com os elementos invertidos.
     *
     * @return Uma nova lista com os elementos invertidos.
     * @throws EmptyCollectionException Se a lista estiver vazia, lançará uma exceção.
     */
    public DoublyLinkedList<T> inverse() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vaiza, logo não é possivel inverter");
        }

        DoubleLinkedUnorderedList<T> newDouble = new DoubleLinkedUnorderedList<>();
        DoubleNode<T> current = this.head;

        while (current != null) {
            newDouble.addToFront(current.getElement());
            current = current.getNext_ele();
        }

        DoublyLinkedList<T> newList = newDouble;
        return newList;
    }
}
