package ArrayTree;

import Interfaces.BinarySearchTreeADT;
import ArrayList.ArrayUnorderedList;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import java.util.Iterator;

public class ArrayBinarySearchTree<T> extends ArrayBinaryTree<T> implements BinarySearchTreeADT<T> {

    protected int height;

    protected int maxIndex;

    public ArrayBinarySearchTree() {
        super();
        height = 0;
        maxIndex = -1;
    }

    public ArrayBinarySearchTree(T element) {
        super(element);
        height = 1;
        maxIndex = 0;
    }

    @Override
    public void addElement(T element) {
        if (tree.length < maxIndex * 2 + 3) {
            expandCapacity();
        }

        Comparable<T> tempelement = (Comparable<T>) element;
        if (isEmpty()) {
            tree[0] = element;
            maxIndex = 0;
        } else {
            boolean added = false;
            int currentIndex = 0;
            while (!added) {
                if (tempelement.compareTo((tree[currentIndex])) < 0) {
                    if (tree[currentIndex * 2 + 1] == null) {
                        tree[currentIndex * 2 + 1] = element;
                        added = true;
                        if (currentIndex * 2 + 1 > maxIndex) {
                            maxIndex = currentIndex * 2 + 1;
                        }
                    } else {
                        currentIndex = currentIndex * 2 + 1;
                    }
                } else {
                    if (tree[currentIndex * 2 + 2] == null) {
                        tree[currentIndex * 2 + 2] = element;
                        added = true;
                        if (currentIndex * 2 + 2 > maxIndex) {
                            maxIndex = currentIndex * 2 + 2;
                        }
                    } else {
                        currentIndex = currentIndex * 2 + 2;
                    }
                }
            }
        }

        height = (int) (Math.log(maxIndex + 1) / Math.log(2)) + 1;
        count++;
    }

    protected void replace(int targetIndex) {
        int currentIndex, oldIndex, newIndex;
        ArrayUnorderedList<Integer> oldlist = new ArrayUnorderedList<Integer>();
        ArrayUnorderedList<Integer> newlist = new ArrayUnorderedList<Integer>();
        ArrayUnorderedList<Integer> templist = new ArrayUnorderedList<Integer>();
        Iterator<Integer> oldIt, newIt;

        if ((targetIndex * 2 + 1 >= tree.length) || (targetIndex * 2 + 2 >= tree.length)) {
            tree[targetIndex] = null;
        } else if ((tree[targetIndex * 2 + 1] == null) && (tree[targetIndex * 2 + 2] == null)) {
            tree[targetIndex] = null;
        } else if ((tree[targetIndex * 2 + 1] != null) && (tree[targetIndex * 2 + 2] == null)) {
            currentIndex = targetIndex * 2 + 1;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                try {
                    currentIndex = (templist.removeFirst());
                    newlist.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                } catch (EmptyCollectionException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            currentIndex = targetIndex;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                try {
                    currentIndex = (templist.removeFirst());
                    oldlist.addToRear(currentIndex);

                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                } catch (EmptyCollectionException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            oldIt = oldlist.iterator();
            newIt = newlist.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();
                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        } else if ((tree[targetIndex * 2 + 1] == null) && (tree[targetIndex * 2 + 2] != null)) {
            currentIndex = targetIndex * 2 + 2;
            templist.addToRear(currentIndex);

            while (!templist.isEmpty()) {
                try {
                    currentIndex = (templist.removeFirst());
                    newlist.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                } catch (EmptyCollectionException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            currentIndex = targetIndex;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                try {
                    currentIndex = (templist.removeFirst());
                    oldlist.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                } catch (EmptyCollectionException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            oldIt = oldlist.iterator();
            newIt = newlist.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();

                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        } else {
            currentIndex = targetIndex * 2 + 2;

            while (tree[currentIndex * 2 + 1] != null) {
                currentIndex = currentIndex * 2 + 1;
            }

            tree[targetIndex] = tree[currentIndex];

            int currentRoot = currentIndex;

            if (tree[currentRoot * 2 + 2] != null) {
                currentIndex = currentRoot * 2 + 2;
                templist.addToRear(currentIndex);
                while (!templist.isEmpty()) {
                    try {
                        currentIndex = (templist.removeFirst());
                        newlist.addToRear(currentIndex);

                        if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                            templist.addToRear(currentIndex * 2 + 1);
                            templist.addToRear(currentIndex * 2 + 2);
                        }
                    } catch (EmptyCollectionException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                currentIndex = currentRoot;
                templist.addToRear(currentIndex);
                while (!templist.isEmpty()) {
                    try {
                        currentIndex = (templist.removeFirst());
                        oldlist.addToRear(currentIndex);
                        if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                            templist.addToRear(currentIndex * 2 + 1);
                            templist.addToRear(currentIndex * 2 + 2);
                        }
                    } catch (EmptyCollectionException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                oldIt = oldlist.iterator();
                newIt = newlist.iterator();
                while (newIt.hasNext()) {
                    oldIndex = oldIt.next();
                    newIndex = newIt.next();

                    tree[oldIndex] = tree[newIndex];
                    tree[newIndex] = null;
                }
            } else {
                tree[currentRoot] = null;
            }
        }
    }

    private int findIndex(Comparable<T> tempElement, int value) {
        int pos = -1;

        for (int ct = 0; ct < this.maxIndex; ct++) {
            if (this.tree[ct] != null && tempElement.compareTo(this.tree[ct]) == 0) {
                pos = ct;
            }
        }

        return pos;
    }

    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {
        if (isEmpty() || targetElement == null) {
            throw new ElementNotFoundException("A lista está vazia");
        }

        Comparable<T> tempElement = (Comparable<T>) targetElement;
        int targetIndex = findIndex(tempElement, 0);

        if (targetIndex == -1) {
            throw new ElementNotFoundException("O elemento a remover não existe na lista");
        }

        T result = tree[targetIndex];
        replace(targetIndex);
        count--;

        int temp = maxIndex;
        maxIndex = -1;

        for (int i = 0; i <= temp; i++) {
            if (tree[i] != null) {
                maxIndex = i;
            }
        }

        height = (int) (Math.log(maxIndex + 1) / Math.log(2)) + 1;

        return result;
    }

    @Override
    public void removeAllOccurences(T targetElement) throws ElementNotFoundException {
        try {
            if (removeElement(targetElement) != null) {
                removeAllOccurences(targetElement);
            }
        } catch (ElementNotFoundException ex) {
        }
    }

    @Override
    public T removeMin() throws ElementNotFoundException {
        return this.removeElement(findMin());
    }

    @Override
    public T removeMax() throws ElementNotFoundException {
        return this.removeElement(findMax());
    }

    @Override
    public T findMin() {
        T min = null;
        Comparable<T> comparableElement = (Comparable<T>) this.tree[0];

        for (int i = 0; i < this.maxIndex; i++) {
            if (tree[i] != null && comparableElement.compareTo(tree[i]) > 0) {
                comparableElement = (Comparable<T>) this.tree[i];
                min = this.tree[i];
            }
        }

        return min;
    }

    @Override
    public T findMax() {
        T max = null;
        Comparable<T> comparableElement = (Comparable<T>) this.tree[0];

        for (int i = 0; i < this.maxIndex; i++) {
            if (tree[i] != null && comparableElement.compareTo(tree[i]) < 0) {
                comparableElement = (Comparable<T>) this.tree[i];
                max = this.tree[i];
            }
        }

        return max;
    }
}
