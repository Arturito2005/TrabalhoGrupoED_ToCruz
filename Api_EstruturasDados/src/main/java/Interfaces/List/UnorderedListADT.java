package Interfaces.List;

import Exceptions.ElementNotFoundException;

/**
 * Esta interface define o ADT (Tipo Abstrato de Dados) para uma lista genérica
 * não ordenada. Ela é uma subinterface de {@code ListADT<T>} e fornece métodos
 * para adicionar elementos à frente, atrás ou após um elemento alvo específico na lista.
 *
 * @param <T> o tipo dos elementos armazenados nesta lista não ordenada
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public interface UnorderedListADT<T> extends ListADT<T> {

    /**
     * Adiciona o elemento especificado à frente desta lista.
     *
     * @param element o elemento a ser adicionado à frente desta lista
     */
    public void addToFront(T element);

    /**
     * Adiciona o elemento especificado à traseira desta lista.
     *
     * @param element o elemento a ser adicionado à traseira desta lista
     */
    public void addToRear(T element);

    /**
     * Adiciona o elemento especificado após o alvo especificado.
     *
     * @param element o elemento a ser adicionado após o alvo
     * @param target o alvo é o item após o qual o elemento será adicionado
     * @throws ElementNotFoundException quando a lista estiver vazia
     */
    public void addAfter(T element, T target) throws ElementNotFoundException;
}
