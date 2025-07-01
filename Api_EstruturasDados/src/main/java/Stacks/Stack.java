package Stacks;

import Exceptions.EmptyCollectionException;
import Interfaces.Stack.StackADT;

/**
 * Classe abstrata de uma stack, que possui todos os metodos que uma Stack precisa de ter.
 *
 * @param <T> Tipo de dado dos elementos armazenados na stack.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public abstract class Stack<T> implements StackADT<T> {

    /**
     * Índice do topo da stack, indicando a próxima posição disponível.
     */
    protected int top;

    /**
     * Construtor padrão que inicializa o top da Stack.
     */
    public Stack() {
        this.top = 0;
    }

    /**
     * Adiciona um elemento ao topo da stack.
     * Este metodo cria um novo nó que será colocado no início da lista encadeada.
     *
     * @param element O elemento a ser adicionado à stack.
     */
    @Override
    public abstract void push(T element);

    /**
     * Remove e retorna o elemento do topo da stack.
     * Se a stack estiver vazia, uma exceção será lançada.
     *
     * @return O elemento removido do topo da stack.
     * @throws EmptyCollectionException Se a stack estiver vazia.
     */
    @Override
    public abstract T pop() throws EmptyCollectionException;

    /**
     * Retorna o elemento do topo da stack sem removê-lo.
     * Se a stack estiver vazia, uma exceção será lançada.
     *
     * @return O elemento do topo da stack.
     * @throws EmptyCollectionException Se a stack estiver vazia.
     */
    @Override
    public abstract T peek() throws EmptyCollectionException;

    /**
     * Verifica se a stack está vazia.
     *
     * @return true se a stack estiver vazia, caso contrário false.
     */
    @Override
    public boolean isEmpty() {
        boolean empty = false;

        if (this.top == 0) {
            empty = true;
        }

        return empty;
    }

    /**
     * Retorna o número de elementos na stack.
     *
     * @return O número de elementos na stack.
     */
    @Override
    public int size() {
        return this.top;
    }

    /**
     * Retorna uma representação em String dos elementos da stack.
     * Os elementos são listados do topo para o fundo da stack.
     *
     * @return A representação em String da stack.
     */
    @Override
    public abstract String toString();
}
