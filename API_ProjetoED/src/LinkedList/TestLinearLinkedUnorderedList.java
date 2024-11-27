package LinkedList;

import Interfaces.UnorderedListADT;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

public class TestLinearLinkedUnorderedList {

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
