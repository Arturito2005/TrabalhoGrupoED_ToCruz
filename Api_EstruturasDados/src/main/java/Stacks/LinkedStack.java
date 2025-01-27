package Stacks;

import Exceptions.EmptyCollectionException;
import Interfaces.StackADT;
import Nodes.LinearNode;

/**
 * Implementação de uma stack (stack) utilizando uma lista encadeada.
 *
 * @param <T> Tipo de dado dos elementos armazenados na stack.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class LinkedStack<T> extends Stack<T> {

    /**
     * Cabeça da lista encadeada, representando o topo da stack.
     */
    private LinearNode<T> head;

    /**
     * Construtor padrão que cria uma stack vazia.
     */
    public LinkedStack() {
        super();
        this.head = null;
    }

    /**
     * Adiciona um elemento ao topo da stack.
     * Este metodo cria um novo nó que será colocado no início da lista encadeada.
     *
     * @param element O elemento a ser adicionado à stack.
     */
    @Override
    public void push(T element) {
        LinearNode<T> newNod = new LinearNode<>(element);

        newNod.setNext(this.head);

        this.head = newNod;
        this.top++;
    }

    /**
     * Remove e retorna o elemento do topo da stack.
     * Se a stack estiver vazia, uma exceção será lançada.
     *
     * @return O elemento removido do topo da stack.
     * @throws EmptyCollectionException Se a stack estiver vazia.
     */
    @Override
    public T pop() throws EmptyCollectionException {
        if (this.top == 0) {
            throw new EmptyCollectionException("A Stack está vazia, logo não é possível remover!");
        }

        T temp_element = this.head.getElement();

        if (this.top == 1) {
            this.head = null;
        } else {
            this.head = this.head.getNext();
        }

        this.top--;
        return temp_element;
    }

    /**
     * Retorna o elemento do topo da stack sem removê-lo.
     * Se a stack estiver vazia, uma exceção será lançada.
     *
     * @return O elemento do topo da stack.
     * @throws EmptyCollectionException Se a stack estiver vazia.
     */
    @Override
    public T peek() throws EmptyCollectionException {
        if (this.top == 0) {
            throw new EmptyCollectionException("A Stack está vazia, logo não dá para consultar o ultimo elemento!");
        }

        return this.head.getElement();
    }

    /**
     * Retorna uma representação em String dos elementos da stack.
     * Os elementos são listados do topo para o fundo da stack.
     *
     * @return A representação em String da stack.
     */
    @Override
    public String toString() {
        String temp = "";
        LinearNode<T> tempLinked = this.head;

        for (int i = 0; i < this.top; i++) {
            temp += tempLinked.getElement().toString() + " ";
            tempLinked = tempLinked.getNext();
        }

        return temp;
    }
}
