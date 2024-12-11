package Interfaces;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import java.util.Iterator;

/**
 * Esta interface define o ADT para uma árvore binária.
 *
 * @param <T> o tipo de elementos armazenados na árvore binária
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public interface BinaryTreeADT<T> {

    /**
     * Retorna uma referência para o elemento raiz.
     *
     * @return uma referência para a raiz
     */
    public T getRoot();

    /**
     * Indica se a árvore está vazia ou não.
     *
     * @return verdadeiro se a árvore não contém elementos, falso caso contrário
     */
    public boolean isEmpty();

    /**
     * Retorna o número de elementos nesta árvore binária.
     *
     * @return o número inteiro de elementos nesta árvore
     */
    public int size();

    /**
     * Retorna verdadeiro se a árvore binária contém um elemento que corresponde ao
     * elemento especificado, e falso caso contrário.
     *
     * @param targetElement o elemento procurado na árvore
     * @return verdadeiro se a árvore contém o elemento alvo
     */
    public boolean contains(T targetElement);

    /**
     * Retorna uma referência ao elemento especificado se ele for encontrado nesta
     * árvore binária. Lança uma exceção se o elemento especificado não for encontrado.
     *
     * @param targetElement o elemento procurado na árvore
     * @return uma referência ao elemento especificado
     * @throws ElementNotFoundException
     * @throws EmptyCollectionException
     */
    public T find(T targetElement) throws ElementNotFoundException, EmptyCollectionException;

    /**
     * Retorna a representação em string da árvore binária.
     *
     * @return uma representação em string da árvore binária
     */
    @Override
    public String toString();

    /**
     * Realiza uma travessia em ordem nesta árvore binária chamando um método
     * recursivo sobrecarregado de travessia em ordem que começa pela raiz.
     *
     * @return um iterador sobre os elementos desta árvore binária
     */
    public Iterator<T> iteratorInOrder();

    /**
     * Realiza uma travessia em pré-ordem nesta árvore binária chamando um método
     * recursivo sobrecarregado de travessia em pré-ordem que começa pela raiz.
     *
     * @return um iterador sobre os elementos desta árvore binária
     */
    public Iterator<T> iteratorPreOrder();

    /**
     * Realiza uma travessia em pós-ordem nesta árvore binária chamando um método
     * recursivo sobrecarregado de travessia em pós-ordem que começa pela raiz.
     *
     * @return um iterador sobre os elementos desta árvore binária
     */
    public Iterator<T> iteratorPostOrder();

    /**
     * Realiza uma travessia em ordem de nível na árvore binária, utilizando uma fila.
     *
     * @return um iterador sobre os elementos desta árvore binária
     */
    public Iterator<T> iteratorLevelOrder();
}
