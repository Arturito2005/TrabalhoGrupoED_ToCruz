package ArrayHeaps;

import Interfaces.HeapADT;

public class TestArrayHeap {
    public static void main(String[] args) {
        HeapADT<Integer> heap = new ArrayHeap<>();
        
        heap.addElement(5);
        heap.addElement(4);
        heap.addElement(3);
        heap.addElement(8);
        heap.addElement(7);
        heap.addElement(9);
        
        System.out.println(heap.toString());
        heap.addElement(2);
        System.out.println(heap.toString());
        System.out.println(heap.removeMin());
        System.out.println(heap.toString());
    } 
}
