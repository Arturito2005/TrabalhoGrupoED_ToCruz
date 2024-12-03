package LinkedTree;

import Interfaces.BinaryTreeADT;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import LinkedList.LinearLinkedUnorderedList;
import Queue.LinkedQueue;
import java.util.Iterator;

public abstract class LinkedBinaryTree<T> implements BinaryTreeADT<T> {

    protected int count;

    protected BinaryTreeNode<T> root;

    public LinkedBinaryTree() {
        this.count = 0;
        this.root = null;
    }

    public LinkedBinaryTree(T element) {
        this.count = 0;
        this.root = new BinaryTreeNode<>(element);
    }

    @Override
    public T getRoot() {
        return this.root.element;
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
        boolean contains = false;
        T target = null;

        try {
            target = find(targetElement);
        } catch (ElementNotFoundException | EmptyCollectionException ex) {
            System.out.println(ex.getMessage());
        }

        if (target != null) {
            contains = true;
        }

        return contains;
    }

    private BinaryTreeNode<T> findAgain(T targetElement, BinaryTreeNode<T> next) {
        if (next == null) {
            return null;
        }

        if (next.element.equals(targetElement)) {
            return next;
        }

        BinaryTreeNode<T> temp = findAgain(targetElement, next.left);

        if (temp == null) {
            temp = findAgain(targetElement, next.right);
        }

        return temp;
    }

    @Override
    public T find(T targetElement) throws ElementNotFoundException, EmptyCollectionException {
        if (this.count == 0) {
            throw new EmptyCollectionException("A lista está vazia");
        }

        BinaryTreeNode<T> current = findAgain(targetElement, this.root);

        if (current == null) {
            throw new ElementNotFoundException("O target introduzido não existe na arvore");
        }

        return current.element;
    }

    protected void inorder(BinaryTreeNode<T> node, LinearLinkedUnorderedList<T> tempList) {
        if (node != null) {
            inorder(node.left, tempList);
            tempList.addToRear(node.element);
            inorder(node.right, tempList);
        }
    }

    @Override
    public Iterator<T> iteratorInOrder() {
        LinearLinkedUnorderedList<T> tempList = new LinearLinkedUnorderedList<>();
        inorder(root, tempList);

        return tempList.iterator();
    }

    protected void preOrder(BinaryTreeNode<T> node, LinearLinkedUnorderedList<T> tempList) {
        if (node != null) {
            tempList.addToRear(node.element);
            preOrder(node.left, tempList);
            preOrder(node.right, tempList);
        }
    }

    @Override
    public Iterator<T> iteratorPreOrder() {
        LinearLinkedUnorderedList<T> tempList = new LinearLinkedUnorderedList<>();
        preOrder(root, tempList);

        return tempList.iterator();
    }

    protected void posOrder(BinaryTreeNode<T> node, LinearLinkedUnorderedList<T> tempList) {
        if (node != null) {
            posOrder(node.left, tempList);
            posOrder(node.right, tempList);
            tempList.addToRear(node.element);
        }
    }

    @Override
    public Iterator<T> iteratorPostOrder() {
        LinearLinkedUnorderedList<T> tempList = new LinearLinkedUnorderedList<>();
        preOrder(root, tempList);

        return tempList.iterator();
    }

    protected void levelOrder(BinaryTreeNode<T> node, LinearLinkedUnorderedList<T> tempList) {
        LinkedQueue<BinaryTreeNode<T>> nodeQueue = new LinkedQueue<>();

        if (node != null) {
            nodeQueue.enqueue(node);

            while (!nodeQueue.isEmpty()) {
                try {
                    BinaryTreeNode<T> current = nodeQueue.dequeue();
                    if (current != null) {
                        tempList.addToRear(current.element);

                        if (current.left != null) {
                            nodeQueue.enqueue(current.left);
                        }

                        if (current.right != null) {
                            nodeQueue.enqueue(current.right);
                        }
                    }
                } catch (EmptyCollectionException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    @Override
    public Iterator<T> iteratorLevelOrder() {
        LinearLinkedUnorderedList<T> results = new LinearLinkedUnorderedList<>();
        levelOrder(this.root, results);

        return results.iterator();
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
