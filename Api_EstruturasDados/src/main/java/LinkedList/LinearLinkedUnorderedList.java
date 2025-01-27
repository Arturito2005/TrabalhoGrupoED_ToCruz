package LinkedList;

import Exceptions.ElementNotFoundException;
import Interfaces.UnorderedListADT;
import Nodes.LinearNode;

/**
 * Implementação de uma lista ligada não ordenada. Permite adicionar elementos ao início, ao final,
 * e após um elemento específico, mas sem manter os elementos em ordem.
 * Esta classe estende {@link LinearLinkedList} e implementa a interface {@link UnorderedListADT}.
 *
 * @param <T> o tipo dos elementos na lista.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class LinearLinkedUnorderedList<T> extends LinearLinkedList<T> implements UnorderedListADT<T> {

    /**
     * Constrói uma nova lista ligada não ordenada, inicializando a lista vazia.
     */
    public LinearLinkedUnorderedList() {
        super();
    }

    /**
     * Adiciona um novo elemento à frente da lista. Este método insere o elemento no início da lista,
     * fazendo com que ele seja o primeiro elemento da lista.
     *
     * @param element o elemento a ser adicionado à frente da lista.
     */
    @Override
    public void addToFront(T element) {
        LinearNode<T> new_node = new LinearNode<>(element);

        if (this.head == null) {
            this.tail = new_node;
        } else {
            new_node.setNext(this.head);
        }

        this.head = new_node;

        this.count++;
        this.modCount++;
    }

    /**
     * Adiciona um novo elemento ao final da lista. Este método insere o elemento como o último
     * nó da lista.
     *
     * @param element o elemento a ser adicionado ao final da lista.
     */
    @Override
    public void addToRear(T element) {
        LinearNode<T> new_node = new LinearNode<>(element);

        if (this.tail == null) {
            this.head = new_node;
        } else {
            this.tail.setNext(new_node);
        }

        this.tail = new_node;
        this.count++;
        this.modCount++;
    }

    /**
     * Adiciona um novo elemento após o elemento especificado (target) na lista.
     * Se o elemento alvo não for encontrado, uma exceção {@link ElementNotFoundException} é lançada.
     *
     * @param element o elemento a ser adicionado após o alvo.
     * @param target  o elemento após o qual o novo elemento será inserido.
     * @throws ElementNotFoundException se o elemento alvo não for encontrado na lista.
     */
    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException {
        LinearNode<T> current = this.head;
        boolean find = false;
        int pos_find = this.count;

        while (pos_find > 0 && !find) {
            if (current.getElement().equals(target)) {
                find = true;
            } else {
                current = current.getNext();
                pos_find--;
            }
        }

        if (!find) {
            throw new ElementNotFoundException("O target introduzido (" + target + ") não pertence há lista!");
        }

        LinearNode<T> new_node = new LinearNode<>(element);

        if (this.head == null) {
            this.head = new_node;
            this.tail = new_node;
        } else if (target.equals(this.head.getElement()) && pos_find == this.count) {
            new_node.setNext(this.head);
            this.head = new_node;
        } else if (target.equals(this.tail.getElement()) && pos_find == 1) {
            this.tail.setNext(new_node);
            this.tail = new_node;
        } else {
            new_node.setNext(current.getNext());
            current.setNext(new_node);
        }

        this.count++;
        this.modCount++;
    }
}
