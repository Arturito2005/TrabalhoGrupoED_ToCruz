package ArrayList;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Interfaces.ListADT;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação abstrata de uma lista genérica baseada em array.
 * Esta classe fornece funcionalidades básicas para manipulação de listas, como adição, remoção, verificação de conteúdo,
 * e iteração sobre os elementos. Também inclui métodos para acessar o primeiro e último elemento, e expandir a capacidade do array.
 *
 * @param <T> Tipo dos elementos da lista.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public abstract class ArrayList<T> implements ListADT<T> {

    /** Capacidade padrão inicial do array */
    private static final int CAPACITY_DEFAULT = 100;

    /** Contador de modificações na lista, usado para detectar modificações concorrentes */
    protected int modCount;

    /** Contador de elementos na lista */
    protected int count;

    /** Array que armazena os elementos da lista */
    protected T[] list;

    /**
     * Construtor padrão que inicializa a lista com a capacidade padrão de 100.
     */
    public ArrayList() {
        this.modCount = 0;
        this.count = 0;
        this.list = (T[]) (new Object[CAPACITY_DEFAULT]);
    }

    /**
     * Construtor que inicializa a lista com uma capacidade especificada.
     * Se a capacidade fornecida for menor ou igual a 0, a capacidade padrão será utilizada.
     *
     * @param capacity A capacidade inicial da lista.
     */
    public ArrayList(int capacity) {
        this.modCount = 0;
        this.count = 0;

        if (capacity > 0) {
            this.list = (T[]) (new Object[capacity]);
        } else {
            this.list = (T[]) (new Object[CAPACITY_DEFAULT]);
        }
    }

    /**
     * Expande o array interno para o dobro da sua capacidade atual.
     * Este método é chamado quando a capacidade do array é atingida.
     */
    protected void expandArray() {
        T[] temp = (T[]) (new Object[(this.list.length * 2)]);

        System.arraycopy(this.list, 0, temp, 0, this.list.length);

        this.list = temp;
    }

    /**
     * Remove o primeiro elemento da lista.
     * Lança uma exceção {@link EmptyCollectionException} se a lista estiver vazia.
     *
     * @return O elemento removido.
     * @throws EmptyCollectionException Se a lista estiver vazia.
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vazia!");
        }

        T elementRemove = this.list[0];
        this.list[0] = null;

        for (int i = 0; i < this.count; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.list[this.count - 1] = null;
        this.count--;
        this.modCount++;
        return elementRemove;
    }

    /**
     * Remove o último elemento da lista.
     * Lança uma exceção {@link EmptyCollectionException} se a lista estiver vazia.
     *
     * @return O elemento removido.
     * @throws EmptyCollectionException Se a lista estiver vazia.
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vazia!");
        }

        this.count--;
        T elementRemove = this.list[this.count];
        this.list[this.count] = null;
        this.modCount++;

        return elementRemove;
    }

    /**
     * Remove o elemento especificado da lista.
     * Lança uma exceção {@link EmptyCollectionException} se a lista estiver vazia,
     * e uma {@link ElementNotFoundException} se o elemento não for encontrado.
     *
     * @param element O elemento a ser removido.
     * @return O elemento removido.
     * @throws EmptyCollectionException Se a lista estiver vazia.
     * @throws ElementNotFoundException Se o elemento não for encontrado.
     */
    @Override
    public T remove(T element) throws EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        if (!contains(element)) {
            throw new ElementNotFoundException("O elemento introduzido não existe");
        }

        int pos = -1;

        do {
            pos++;
        } while (!this.list[pos].equals(element));

        T elementRem = this.list[pos];
        this.list[pos] = null;

        for (int i = pos; i < this.count; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.list[this.count - 1] = null;
        this.count--;
        this.modCount++;
        return elementRem;
    }

    /**
     * Retorna o primeiro elemento da lista.
     * Lança uma exceção {@link EmptyCollectionException} se a lista estiver vazia.
     *
     * @return O primeiro elemento da lista.
     * @throws EmptyCollectionException Se a lista estiver vazia.
     */
    @Override
    public T first() {
        return this.list[0];
    }

    /**
     * Retorna o último elemento da lista.
     * Lança uma exceção {@link EmptyCollectionException} se a lista estiver vazia.
     *
     * @return O último elemento da lista.
     * @throws EmptyCollectionException Se a lista estiver vazia.
     */
    @Override
    public T last() {
        return this.list[this.count - 1];
    }


    /**
     * Verifica se a lista contém o elemento especificado.
     *
     * @param target O elemento a ser buscado.
     * @return true se o elemento estiver na lista, false caso contrário.
     */
    @Override
    public boolean contains(T target) {
        boolean targetFound = false;

        for (T currentEl : this.list) {
            if (target.equals(currentEl)) {
                targetFound = true;
            }
        }

        return targetFound;
    }

    /**
     * Verifica se a lista está vazia.
     *
     * @return true se a lista estiver vazia, false caso contrário.
     */
    @Override
    public boolean isEmpty() {
        boolean empty = false;

        if (this.count == 0) {
            empty = true;
        }

        return empty;
    }

    /**
     * Retorna o número de elementos na lista.
     *
     * @return O número de elementos na lista.
     */
    @Override
    public int size() {
        return this.count;
    }

    /**
     * Retorna um iterador para a lista.
     *
     * @return Um iterador para percorrer os elementos da lista.
     */
    @Override
    public Iterator<T> iterator() {
        return new MyIterator<>();
    }

    /**
     * Retorna uma representação em string da lista.
     *
     * @return A representação em string dos elementos da lista.
     */
    @Override
    public String toString() {
        String temp = "";

        for (int i = 0; i < this.count; i++) {
            temp = temp + this.list[i] + " ";
        }

        return temp;
    }

    /**
     * Classe interna para implementação do iterador da lista.
     * Realiza a iteração sobre os elementos da lista e garante a segurança em relação a modificações concorrentes.
     */
    private class MyIterator<E> implements Iterator<E> {
        /**
         * Variável que armazena a posição atual do iterador na lista.
         * Inicializa com o valor 0, representando o início da iteração.
         */
        private int current;

        /**
         * Variável que armazena o valor de modCount no momento da criação do iterador.
         * Usada para detectar modificações concorrentes na lista enquanto a iteração está em andamento.
         */
        private int exceptedModCount;

        /**
         * Variável booleana que indica se a remoção de um elemento é permitida no estado atual do iterador.
         * Inicializa com 'false', e é alterada para 'true' quando o próximo elemento é acessado, permitindo a remoção.
         */
        private boolean isOkToRemove;

        /**
         * Construtor do meu Iterator
         * */
        private MyIterator() {
            this.current = 0;
            this.exceptedModCount = modCount;
            this.isOkToRemove = false;
        }

        /**
         * Verifica se há mais elementos para iterar.
         *
         * @return true se houver mais elementos, false caso contrário.
         */
        @Override
        public boolean hasNext() {
            return (this.current != size());
        }

        /**
         * Retorna o próximo elemento da lista.
         * Lança uma exceção {@link NoSuchElementException} se não houver mais elementos.
         * Lança uma exceção {@link ConcurrentModificationException} se houver modificações concorrentes.
         *
         * @return O próximo elemento da lista.
         * @throws ConcurrentModificationException Se houver modificações concorrentes.
         * @throws NoSuchElementException Se não houver mais elementos.
         */
        @Override
        public E next() {
            if (this.exceptedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            this.isOkToRemove = true;
            this.current++;
            return (E) list[current - 1];
        }

        /**
         * Remove o elemento atual da lista.
         * Lança uma exceção {@link ConcurrentModificationException} se houver modificações concorrentes.
         * Lança uma exceção {@link IllegalStateException} se a remoção não for permitida.
         *
         * @throws ConcurrentModificationException Se houver modificações concorrentes.
         * @throws IllegalStateException Se a remoção não for permitida.
         */
        @Override
        public void remove() {
            if (this.exceptedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            if (!this.isOkToRemove) {
                throw new IllegalStateException();
            }

            try {
                ArrayList.this.remove(list[current - 1]);
            } catch (EmptyCollectionException ex) {
                System.out.print(ex.getMessage());
            }

            this.exceptedModCount++;
            this.isOkToRemove = false;
            this.current--;
        }
    }
}
