package DoubleLinkedList;

import Exceptions.ElementNotFoundException;
import java.util.Iterator;

/**
 * Classe de teste para a implementação de uma lista duplamente encadeada não ordenada (DoubleLinkedUnorderedList).
 *
 * Este programa realiza operações de inserção e remoção de elementos em uma lista duplamente encadeada não ordenada,
 * incluindo adições à frente, ao final e após um elemento específico. Além disso, realiza a iteração sobre a lista
 * e a impressão de seus elementos.
 */
public class TestDoublyLinkedUnorderedList {

    /**
     * Método principal que executa os testes na lista duplamente encadeada não ordenada.
     *
     * Este método cria uma lista duplamente encadeada não ordenada, adiciona elementos na frente, no final
     * e após um elemento específico, além de iterar sobre a lista para exibir os valores. Também trata exceções
     * ao tentar adicionar um elemento após um item inexistente.
     *
     * @param args os argumentos da linha de comando (não são usados neste teste)
     */
    public static void main(String[] args) {
        DoubleLinkedUnorderedList<Integer> linkedUndo1 = new DoubleLinkedUnorderedList<>();

        linkedUndo1.addToFront(1);
        linkedUndo1.addToFront(2);
        linkedUndo1.addToFront(3);

        System.out.println(linkedUndo1.toString());

        linkedUndo1.addToRear(4);
        linkedUndo1.addToRear(5);
        linkedUndo1.addToRear(6);
        System.out.println(linkedUndo1.toString());

        try {
            linkedUndo1.addAfter(7, 1);
            linkedUndo1.addAfter(9, 0);
        } catch (ElementNotFoundException el) {
            System.out.println(el.getMessage());
        }

        System.out.println(linkedUndo1.toString());

        Iterator<Integer> iterator = linkedUndo1.iterator();

        int[] array = new int[linkedUndo1.count];
        int i = 0;

        while (iterator.hasNext()) {
            array[i++] = iterator.next();
        }

        for (int y = 0; y < array.length; y++) {
            System.out.println(array[y]);
        }
    }
}
