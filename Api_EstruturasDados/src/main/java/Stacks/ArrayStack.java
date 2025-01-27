package Stacks;

import Exceptions.EmptyCollectionException;
import Interfaces.StackADT;

/**
 * Classe que representa uma Stack implementada com array
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 * @param <T> O tipo de elementos armazenados na stack.
 */
public class ArrayStack<T> extends Stack<T> {

    /**
     * Tamanho padrão da stack.
     */
    private static final int DEFAULT_SIZE = 50;

    /**
     * Array que armazena os elementos da stack.
     */
    private T[] stack;

    /**
     * Construtor padrão que cria uma stack com tamanho padrão.
     */
    public ArrayStack() {
        super();
        this.stack = (T[]) (new Object[DEFAULT_SIZE]);
    }

    /**
     * Expande o tamanho da stack quando ela está cheia.
     * O novo tamanho será o dobro do tamanho anterior.
     */
    private void expandStack() {
        T[] tempStack = (T[]) (new Object[this.top * 2]);

        System.arraycopy(this.stack, 0, tempStack, 0, top);

        this.stack = tempStack;
    }

    /**
     * Adiciona um elemento ao topo da stack.
     * Caso a stack esteja cheia, ela será expandida automaticamente.
     *
     * @param element O elemento a ser adicionado à stack.
     */
    @Override
    public void push(T element) {
        if (this.top == this.stack.length) {
            expandStack();
        }

        this.stack[this.top++] = element;
    }

    /**
     * Remove e retorna o elemento do topo da stack.
     *
     * @return O elemento removido do topo da stack.
     * @throws EmptyCollectionException Se a stack estiver vazia.
     */
    @Override
    public T pop() throws EmptyCollectionException {
        if (this.top == 0) {
            throw new EmptyCollectionException("A stack está vazia");
        }

        T oldTop = this.stack[top - 1];

        this.top--;
        this.stack[this.top] = null;

        return oldTop;
    }

    /**
     * Retorna o elemento do topo da stack sem removê-lo.
     *
     * @return O elemento do topo da stack.
     * @throws EmptyCollectionException Se a stack estiver vazia.
     */
    @Override
    public T peek() throws EmptyCollectionException {
        if (this.top == 0) {
            throw new EmptyCollectionException("A Stack está vazia");
        }

        return this.stack[this.top - 1];
    }

    /**
     * Retorna uma representação em String dos elementos da stack.
     * Os elementos são listados do fundo para o topo.
     *
     * @return A representação em String da stack.
     */
    @Override
    public String toString() {
        String temp = "";

        for (int i = 0; i < this.top; i++) {
            temp += this.stack[i].toString() + " ";
        }

        return temp;
    }
}
