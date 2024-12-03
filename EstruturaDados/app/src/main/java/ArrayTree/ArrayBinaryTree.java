package ArrayTree;

import Interfaces.BinaryTreeADT;
import ArrayList.ArrayUnorderedList;
import Queue.CircularArrayQueue;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import java.util.Iterator;

public abstract class ArrayBinaryTree<T> implements BinaryTreeADT<T> {

    private static int DEFAULT_CAPACITY = 50;

    protected int count;

    protected T[] tree;

    public ArrayBinaryTree() {
        this.count = 0;
        this.tree = (T[]) ((new Object[DEFAULT_CAPACITY]));
    }

    public ArrayBinaryTree(T element) {
        count = 1;
        tree = (T[]) new Object[DEFAULT_CAPACITY];
        tree[0] = element;
    }

    protected void expandCapacity() {
        T[] temp = (T[]) (new Object[(this.tree.length * 2)]);

        System.arraycopy(this.tree, 0, temp, 0, this.tree.length);

        this.tree = temp;
    }

    @Override
    public T getRoot() {
        return this.tree[0];
    }

    @Override
    public boolean isEmpty() {
        boolean empty = false;

        if (this.count == 0) {
            empty = true;
        }

        return empty;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean contains(T targetElement) {
        boolean contain = false;
        T target = null;

        try {
            target = find(targetElement);
        } catch (ElementNotFoundException | EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        if (target != null) {
            contain = true;
        }

        return contain;
    }

    @Override
    public T find(T targetElement) throws ElementNotFoundException, EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        boolean found = false;
        T temp = null;

        for (int ct = 0; ct < this.count && !found; ct++) {
            if (targetElement.equals(this.tree[ct])) {
                found = true;
                temp = this.tree[ct];
            }
        }

        if (!found) {
            throw new ElementNotFoundException("binary tree");
        }

        return temp;
    }

    protected void inOrder(int n, ArrayUnorderedList<T> tempList) {
        if (n < this.tree.length) {
            if (tree[n] != null) {
                inOrder((2 * n) + 1, tempList);
                tempList.addToRear(this.tree[n]);
                inOrder(2 * (n + 1), tempList);
            }
        }
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inOrder(0, tempList);

        return tempList.iterator();
    }

    protected void preOrder(int n, ArrayUnorderedList<T> tempList) {
        if (n < this.tree.length) {
            if (tree[n] != null) {
                tempList.addToRear(this.tree[n]);
                preOrder((2 * n) + 1, tempList);
                preOrder(2 * (n + 1), tempList);
            }
        }
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preOrder(0, tempList);

        return tempList.iterator();
    }

    protected void postOrder(int n, ArrayUnorderedList<T> tempList) {
        if (n < this.tree.length) {
            if (tree[n] != null) {
                postOrder((2 * n) + 1, tempList);
                postOrder(2 * (n + 1), tempList);
                tempList.addToRear(this.tree[n]);
            }
        }
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        postOrder(0, tempList);

        return tempList.iterator();
    }

    protected void levelOrder(int n, ArrayUnorderedList<T> tempList, CircularArrayQueue<T> nodes) {
        if (n < this.tree.length) {
            nodes.enqueue(this.tree[n]);

            while (!nodes.isEmpty()) {
                try {
                    T current = nodes.dequeue();
                    if (current != null) {
                        tempList.addToRear(current);

                        if (((2 * n) + 1) < this.tree.length && this.tree[(2 * n) + 1] != null) {
                            nodes.enqueue(this.tree[(2 * n) + 1]);
                        }

                        if ((2 * (n + 1)) < this.tree.length && this.tree[2 * (n + 1)] != null) {
                            nodes.enqueue(this.tree[2 * (n + 1)]);
                        }

                        n++;
                    }
                } catch (EmptyCollectionException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        CircularArrayQueue<T> arrayQueue = new CircularArrayQueue<>();
        ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        levelOrder(0, tempList, arrayQueue);

        return tempList.iterator();
    }

    @Override
    public String toString() {
        String temp = "";
        Iterator<T> itr = this.iteratorLevelOrder();

        while (itr.hasNext()) {
            temp = temp + itr.next() + " ";
        }

        return temp;
    }
}
