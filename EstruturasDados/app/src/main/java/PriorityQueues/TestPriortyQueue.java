package PriorityQueues;

public class TestPriortyQueue {

    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        /*Por causa do Compare que está no ((Comparable) temp).compareTo(tree[(next - 1) / 2]) < 0))
        é que ele está a conseguir meter os elementos com maior prioridade no inicio da tree.
        Quando menor o numero da prioridade, maior é a prioridade desse numero 
        */
        priorityQueue.addElement(1, 2);
        priorityQueue.addElement(2, 2);
        priorityQueue.addElement(3, 1);
        priorityQueue.addElement(8, 3);

        priorityQueue.addElement(9, 2);
        priorityQueue.addElement(10, 1);
        priorityQueue.addElement(4, 1);

        System.out.println(priorityQueue.toString());
        System.out.println(priorityQueue.removeNext());
        System.out.println(priorityQueue.toString());
        System.out.println(priorityQueue.removeNext());
        System.out.println(priorityQueue.toString());
        System.out.println(priorityQueue.removeNext());
        System.out.println(priorityQueue.toString());
    }

}
