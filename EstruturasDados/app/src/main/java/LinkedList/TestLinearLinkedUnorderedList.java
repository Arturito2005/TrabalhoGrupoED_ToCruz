package LinkedList;

import Interfaces.UnorderedListADT;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

/**
 * Classe de teste para a implementação de uma lista não ordenada encadeada (LinearLinkedUnorderedList).
 *
 * Esta classe realiza testes para as operações básicas de uma lista não ordenada encadeada, como
 * adicionar elementos à frente, adicionar ao final, adicionar após um elemento específico, remover
 * o primeiro e o último elemento, e imprimir o estado da lista após cada operação.
 */
public class TestLinearLinkedUnorderedList {

    /**
     * Método principal que executa os testes na lista não ordenada encadeada.
     *
     * Este método cria uma lista não ordenada encadeada, adiciona elementos em várias posições
     * (à frente, ao final e após um elemento específico), realiza remoções do primeiro e último
     * elemento, e imprime o estado da lista após cada operação. Também realiza verificações de
     * exceções lançadas em situações de erro, como quando um elemento não é encontrado para a operação
     * de adição após um elemento específico.
     *
     * @param args os argumentos da linha de comando (não são usados neste teste)
     */
    public static void main(String[] args) {
        UnorderedListADT<Integer> linkedUndo1 = new LinearLinkedUnorderedList<>();

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
            linkedUndo1.addAfter(8, 3);
            linkedUndo1.addAfter(10, 6);
            linkedUndo1.addAfter(9, 0);
        } catch (ElementNotFoundException el) {
            System.out.println(el.getMessage());
        }

        try {
            linkedUndo1.removeFirst();
            linkedUndo1.removeLast();
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(linkedUndo1.toString());

        //Iterator<Integer> iterator = linkedUndo1.iterator();
        /**
         * int[] array = new int[linkedUndo1.count]; int i = 0;
         *
         * while(iterator.hasNext()) { array[i++] = iterator.next(); }
         *
         * for(int y = 0; y < array.length; y++) { System.out.println(array[y]);
         * }
         */
    }
}
