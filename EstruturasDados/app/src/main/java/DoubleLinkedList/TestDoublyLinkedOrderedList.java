package DoubleLinkedList;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import java.util.Iterator;

/**
 * Classe de teste para a implementação de uma lista duplamente encadeada ordenada (DoublyLinkedOrderedList).
 *
 * Este programa realiza operações de inserção, remoção e iteração sobre uma lista duplamente encadeada ordenada.
 * Ele testa a adição de elementos, remoção de elementos específicos (primeiro, último e por valor),
 * e inverte a lista para verificar o funcionamento do método de inversão.
 */
public class TestDoublyLinkedOrderedList {

    /**
     * Método principal que executa os testes na lista duplamente encadeada ordenada.
     *
     * Este método cria uma lista duplamente encadeada ordenada, adiciona elementos em ordem, remove elementos
     * específicos (como o primeiro, último e por valor), itera sobre a lista para exibir seus valores e testa
     * a inversão da lista.
     *
     * @param args os argumentos da linha de comando (não são usados neste teste)
     */
    public static void main(String[] args) {
        DoublyLinkedOrderedList<Integer> linkedOrd1 = new DoublyLinkedOrderedList();

        linkedOrd1.add(1);
        linkedOrd1.add(2);
        linkedOrd1.add(2);
        linkedOrd1.add(3);
        linkedOrd1.add(4);

        System.out.println(linkedOrd1.toString());
        DoublyLinkedOrderedList<Integer> linkedOrd2 = new DoublyLinkedOrderedList();

        linkedOrd2.add(4);
        linkedOrd2.add(2);
        linkedOrd2.add(1);
        linkedOrd2.add(3);
        linkedOrd2.add(7);
        linkedOrd2.add(5);
        linkedOrd2.add(6);

        System.out.println(linkedOrd2.toString());

        try {
            linkedOrd2.remove(3);
        } catch (EmptyCollectionException | ElementNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        Iterator<Integer> interador = linkedOrd2.iterator();
        while (interador.hasNext()) {
            System.out.println("Valor --> " + interador.next());
        }

        try {
            linkedOrd2.removeFirst();
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(" ");
        Iterator<Integer> interador2 = linkedOrd2.iterator();
        while (interador2.hasNext()) {
            System.out.println("Valor --> " + interador2.next());
        }

        try {
            linkedOrd2.removeLast();
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(" ");
        Iterator<Integer> interador3 = linkedOrd2.iterator();
        while (interador3.hasNext()) {
            System.out.println("Valor --> " + interador3.next());
        }

        ///Testes inversão
        try {
            DoublyLinkedList<Integer> invertido;
            invertido = linkedOrd1.inverse();
            System.out.println(invertido.toString());
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
