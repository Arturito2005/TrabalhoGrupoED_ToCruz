package Nodes;

/**
 * A class that represents a node in a linked list structure.
 * Each node holds an element of type T and a reference to the next node.
 *
 * @param <T> the type of element stored in this node
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class LinearNode<T> {

    /**
     * O elemento armazenado neste nó.
     */
    private T element;

    /**
     * O próximo nó na lista encadeada.
     */
    private LinearNode<T> next;

    /**
     * Construtor que cria um nó com um elemento especificado e sem próximo nó.
     *
     * @param element o elemento a ser armazenado no nó
     */
    public LinearNode(T element) {
        this.element = element;
        this.next = null;
    }

    /**
     * Retorna o elemento armazenado neste nó.
     *
     * @return o elemento armazenado neste nó
     */
    public T getElement() {
        return this.element;
    }

    /**
     * Define o elemento deste nó.
     *
     * @param element o novo elemento a ser definido
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Retorna o próximo nó na lista encadeada.
     *
     * @return o próximo nó
     */
    public LinearNode<T> getNext() {
        return this.next;
    }

    /**
     * Define o próximo nó na lista encadeada.
     *
     * @param next o próximo nó a ser definido
     */
    public void setNext(LinearNode<T> next) {
        this.next = next;
    }

    /**
     * Retorna uma representação em forma de string do nó, incluindo seu elemento e o próximo nó.
     *
     * @return uma string representando este nó
     */
    @Override
    public String toString() {
        return "LinearNode{" + "element=" + this.element + ", linear=" + this.next + '}';
    }
}

