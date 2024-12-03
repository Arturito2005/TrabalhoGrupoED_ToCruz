package LinkedList;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import java.util.Iterator;

/*
Testar esta classe unica classe sem os testes feitos quando a abri
ela não tinha testes dela, tinha comentado os testes da double
 */
public class TestLinearLinkedOrderedList {

    public static void main(String[] args) {

        LinearLinkedOrderedList<Integer> linkedOrd1 = new LinearLinkedOrderedList();

        linkedOrd1.add(1);
        linkedOrd1.add(2);
        linkedOrd1.add(2);
        linkedOrd1.add(3);
        linkedOrd1.add(4);

        System.out.println(linkedOrd1.toString());
        LinearLinkedOrderedList<Integer> linkedOrd2 = new LinearLinkedOrderedList();

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

    }
}
