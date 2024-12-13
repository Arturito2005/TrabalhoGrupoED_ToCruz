package DoubleLinkedList;

/**
 * Esta classe representa um nó duplamente ligado, utilizado numa lista
 * duplamente ligada. Cada nó contém um elemento de tipo genérico, além de
 * referências para o próximo nó e o nó anterior.
 *
 * @param <T> o tipo do elemento armazenado no nó.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class DoubleNode<T> {

    private T element;
    private DoubleNode<T> next_ele;
    private DoubleNode<T> previous_ele;

    /**
     * Constrói um nó vazio, sem elementos e sem referências para o próximo ou
     * o nó anterior.
     */
    public DoubleNode() {
        this.element = null;
        this.next_ele = null;
        this.previous_ele = null;
    }

    /**
     * Constrói um nó com o elemento especificado, sem referências para o próximo
     * ou o nó anterior.
     *
     * @param element o elemento a ser armazenado neste nó.
     */
    public DoubleNode(T element) {
        this.element = element;
        this.next_ele = null;
        this.previous_ele = null;
    }

    /**
     * Retorna o elemento armazenado neste nó.
     *
     * @return o elemento armazenado neste nó.
     */
    public T getElement() {
        return element;
    }

    /**
     * Define o elemento armazenado neste nó.
     *
     * @param element o elemento a ser armazenado neste nó.
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Retorna o próximo nó na lista.
     *
     * @return o próximo nó na lista.
     */
    public DoubleNode<T> getNext_ele() {
        return next_ele;
    }

    /**
     * Define o próximo nó na lista.
     *
     * @param next_ele o próximo nó na lista.
     */
    public void setNext_ele(DoubleNode<T> next_ele) {
        this.next_ele = next_ele;
    }

    /**
     * Retorna o nó anterior na lista.
     *
     * @return o nó anterior na lista.
     */
    public DoubleNode<T> getPrevious_ele() {
        return previous_ele;
    }

    /**
     * Define o nó anterior na lista.
     *
     * @param previous_ele o nó anterior na lista.
     */
    public void setPrevious_ele(DoubleNode<T> previous_ele) {
        this.previous_ele = previous_ele;
    }
}
