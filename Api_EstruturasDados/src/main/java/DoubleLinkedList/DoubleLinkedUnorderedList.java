package DoubleLinkedList;

import Exceptions.ElementNotFoundException;
import Interfaces.List.UnorderedListADT;

/**
 * Esta classe implementa uma lista duplamente ligada não ordenada.
 * Ela fornece métodos para adicionar elementos à frente, ao final e após
 * um elemento específico da lista. Esta implementação estende a classe
 * <code>DoublyLinkedList</code> e implementa a interface <code>UnorderedListADT</code>.
 *
 * @param <T> o tipo dos elementos armazenados na lista.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class DoubleLinkedUnorderedList<T> extends DoublyLinkedList<T> implements UnorderedListADT<T> {

    /**
     * Construtor que cria uma nova lista duplamente ligada não ordenada.
     * Inicializa a lista com cabeça e cauda como nulas e o contador de elementos como zero.
     */
    public DoubleLinkedUnorderedList() {
        super();
    }

    /**
     * Adiciona um elemento no início da lista (cabeça).
     *
     * @param element o elemento a ser adicionado no início da lista.
     */
    @Override
    public void addToFront(T element) {
        DoubleNode<T> new_node = new DoubleNode<>(element);

        if (this.head == null) {
            this.tail = new_node;
        } else {
            new_node.setNext_ele(this.head);
            this.head.setPrevious_ele(new_node);
        }

        this.head = new_node;
        this.count++;
        this.modCount++;
    }

    /**
     * Adiciona um elemento no final da lista (cauda).
     *
     * @param element o elemento a ser adicionado no final da lista.
     */
    @Override
    public void addToRear(T element) {
        DoubleNode<T> new_node = new DoubleNode<>(element);

        if (this.tail == null) {
            this.head = new_node;
        } else {
            new_node.setPrevious_ele(this.tail);
            this.tail.setNext_ele(new_node);
        }

        this.tail = new_node;
        this.count++;
        this.modCount++;
    }

    /**
     * Adiciona um elemento após um elemento específico na lista.
     *
     * @param element o elemento a ser adicionado após o elemento alvo.
     * @param target  o elemento alvo após o qual o novo elemento será inserido.
     * @throws ElementNotFoundException se o elemento alvo não for encontrado na lista.
     */
    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException {
        DoubleNode<T> current = this.head;
        boolean find = false;

        while (current != null && find == false) {
            if (current.getElement().equals(target)) {
                find = true;
            } else {
                current = current.getNext_ele();
            }
        }

        if (!find) {
            throw new ElementNotFoundException("O target introduzido (" + target + ") não pertence há lista!");
        }

        DoubleNode<T> new_node = new DoubleNode<>(element);

        new_node.setNext_ele(current.getNext_ele());
        new_node.setPrevious_ele(current);

        current.setNext_ele(new_node);

        this.count++;
        this.modCount++;
    }
}
