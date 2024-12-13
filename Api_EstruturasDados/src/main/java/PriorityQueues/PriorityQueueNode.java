package PriorityQueues;

/**
 * Classe que representa um nó em uma fila de prioridade.
 * Cada nó contém um elemento, uma prioridade e uma ordem de inserção para garantir a ordenação.
 * A classe implementa a interface Comparable para que os nós possam ser comparados com base na prioridade.
 * Em caso de empate na prioridade, a ordem de inserção é usada para determinar a ordem.
 *
 * @param <T> Tipo de elemento armazenado no nó da fila de prioridade.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanografico: 8230148
 * @version 1.0
 */
public class PriorityQueueNode<T> implements Comparable<PriorityQueueNode> {

    /**
     * Variável estática que mantém a próxima ordem de inserção para garantir a ordem de chegada dos elementos.
     */
    private static int nextorder = 0;

    /**
     * Prioridade do nó. Quanto maior a prioridade, maior a precedência na fila.
     */
    private int priority;

    /**
     * Ordem de inserção do nó, usada para desempatar quando os nós têm a mesma prioridade.
     */
    private int order;

    /**
     * Elemento armazenado no nó.
     */
    private T element;

    /**
     * Creates a new PriorityQueueNode with the specified data.
     *
     * @param obj  the element of the new priority queue node
     * @param prio the integer priority of the new queue node
     */
    public PriorityQueueNode(T obj, int prio) {
        this.element = obj;
        this.priority = prio;
        this.order = nextorder;
        nextorder++;
    }

    /**
     * Returns the element in this node.
     *
     * @return the element contained within this node
     */
    public T getElement() {
        return element;
    }

    /**
     * Returns the priority value for this node.
     *
     * @return the integer priority for this node
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * Returns the order for this node.
     *
     * @return the integer order for this node
     */
    public int getOrder() {
        return this.order;
    }

    /**
     * Returns a string representation for this node.
     */
    @Override
    public String toString() {
        String temp = (element.toString() + priority + order);
        return temp;
    }

    /**
     * Returns the 1 if the current node has higher priority than the given node
     * and -1 otherwise.
     *
     * @param obj the node to compare to this node
     * @return the integer result of the comparison of the obj node and this one
     */
    @Override
    public int compareTo(PriorityQueueNode obj) {
        int result;
        PriorityQueueNode<T> temp = obj;

        if (this.priority > temp.getPriority()) {
            result = 1;
        } else if (this.priority < temp.getPriority()) {
            result = -1;
        } else if (this.order > temp.getOrder()) {
            result = 1;
        } else {
            result = -1;
        }
        return result;
    }

}
