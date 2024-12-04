package DoubleLinkedList;

import Exceptions.ElementNotFoundException;
import java.util.Iterator;

public class TestDoublyLinkedUnorderedList {

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
