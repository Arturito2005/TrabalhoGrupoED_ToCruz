package LinkedTree;

import Interfaces.BinarySearchTreeADT;

public class TestLinkedBinarySearch {

    public static void main(String[] args) {
        BinarySearchTreeADT<Integer> searchTree = new LinkedBinarySearchTree<>();

        /*
        searchTree.addElement(10);
        searchTree.addElement(1);
        searchTree.addElement(2);
        searchTree.addElement(3);
        searchTree.addElement(4);
        searchTree.addElement(5);
        searchTree.addElement(6);
        searchTree.addElement(7);
        searchTree.addElement(8);
        searchTree.addElement(9);
         */
        searchTree.addElement(10);
        searchTree.addElement(5);
        searchTree.addElement(15);
        searchTree.addElement(5);
        searchTree.addElement(13);
        searchTree.addElement(13);
        searchTree.addElement(5);
        searchTree.addElement(3);
        searchTree.addElement(15);

        System.out.println(searchTree.toString());

        searchTree.removeMin();

        searchTree.addElement(2);
        searchTree.addElement(3);

        System.out.println(searchTree.toString());

        searchTree.removeMin();

        System.out.println(searchTree.toString());

        searchTree.removeAllOccurences(15);

        System.out.println(searchTree.toString());

        searchTree.removeAllOccurences(5);

        System.out.println(searchTree.toString());
    }

}
