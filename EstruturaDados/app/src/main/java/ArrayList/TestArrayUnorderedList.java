package ArrayList;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

public class TestArrayUnorderedList {

    public static void main(String[] args) {
        ArrayUnorderedList<Integer> order1 = new ArrayUnorderedList<>();

        order1.addToFront(1);
        order1.addToRear(2);
        order1.addToFront(3);
        order1.addToRear(4);
        order1.addToRear(5);
        order1.addToRear(6);

        System.out.println(order1.toString());

        try {
            System.out.println(order1.remove(2));
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(order1.toString());

        try {
            order1.addAfter(2, 9);
        } catch (ElementNotFoundException el) {
            System.out.println(el.getMessage());
        }

        System.out.println(order1.toString());

        try {
            System.out.println(order1.removeFirst());
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(order1.removeLast());
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(order1.toString());
    }

}
