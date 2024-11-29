package Stacks;

import Exceptions.EmptyCollectionException;
import Interfaces.StackADT;

public class TestLinkedStack {

    public static void main(String[] args) {
        StackADT<String> linkedStackString = new LinkedStack<>();

        linkedStackString.push("Ola");

        try {
            linkedStackString.pop();
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        linkedStackString.push("Ola");
        linkedStackString.push("Adeus");
        linkedStackString.push("Good");
        linkedStackString.push("Bad");
        linkedStackString.push("Ultimo");

        System.out.println(linkedStackString.toString());

        try {
            System.out.println(linkedStackString.pop());
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(linkedStackString.toString());

        try {
            System.out.println(linkedStackString.peek());
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
