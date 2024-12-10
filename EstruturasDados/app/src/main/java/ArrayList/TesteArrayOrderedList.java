package ArrayList;

import Exceptions.EmptyCollectionException;
import java.util.Iterator;

public class TesteArrayOrderedList {

    public static void main(String[] args) {
        ArrayOrderedList<Integer> order1 = new ArrayOrderedList<Integer>();

        order1.add(1);
        order1.add(2);
        order1.add(3);
        order1.add(4);

        try {
            System.out.println(order1.removeFirst());
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(order1.toString());

        try {
            System.out.println(order1.removeLast());
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(order1.toString());

        ArrayOrderedList<Integer> order2 = new ArrayOrderedList<Integer>();

        order2.add(2);
        order2.add(4);
        order2.add(3);
        order2.add(1);

        try {
            order2.remove(9);
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            order2.remove(3);
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        Iterator<Integer> interador = order2.iterator();

        while (interador.hasNext()) {
            System.out.println("Valor --> " + interador.next());
        }
    }
}
