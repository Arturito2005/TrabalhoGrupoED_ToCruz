package Stacks;

import Exceptions.EmptyCollectionException;
import Interfaces.StackADT;

public class TestArrayStack {

    public static void main(String[] args) {
        StackADT<Integer> arrayStackInt = new ArrayStack<>();

        try {
            System.out.println(arrayStackInt.pop());
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        arrayStackInt.push(1);
        arrayStackInt.push(4);
        arrayStackInt.push(6);
        arrayStackInt.push(7);
        arrayStackInt.push(9);
        arrayStackInt.push(12);

        System.out.println(arrayStackInt.toString());

        try {
            System.out.println(arrayStackInt.pop());
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println(arrayStackInt.peek());
        } catch (EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(arrayStackInt.toString());
    }
}
