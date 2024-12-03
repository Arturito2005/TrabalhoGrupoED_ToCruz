package LinkedTree;

import Interfaces.BinarySearchTreeADT;
import Exceptions.ElementNotFoundException;

public class LinkedBinarySearchTree<T> extends LinkedBinaryTree<T> implements BinarySearchTreeADT<T> {

    public LinkedBinarySearchTree() {
        super();
    }

    public LinkedBinarySearchTree(T element) {
        super(element);
    }

    @Override
    public void addElement(T element) {
        BinaryTreeNode<T> temp = new BinaryTreeNode<>(element);
        Comparable<T> comparableElement = (Comparable<T>) element;

        if (this.isEmpty()) {
            this.root = temp;
        } else {
            BinaryTreeNode<T> current = this.root;
            boolean added = false;

            while (!added) {
                if (comparableElement.compareTo(current.element) < 0) {
                    if (current.left == null) {
                        current.left = temp;
                        added = true;
                    } else {
                        current = current.left;
                    }
                } else {
                    if (current.right == null) {
                        current.right = temp;
                        added = true;
                    } else {
                        current = current.right;
                    }
                }
            }
        }

        count++;
    }

    protected BinaryTreeNode<T> replacement(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> result;

        if ((node.left == null) && (node.right == null)) {
            result = null;
        } else if ((node.left != null) && (node.right == null)) {
            result = node.left;
        } else if ((node.left == null) && (node.right != null)) {
            result = node.right;
        } else {
            BinaryTreeNode<T> current = node.right;
            BinaryTreeNode<T> parent = node;

            while (current.left != null) {
                parent = current;
                current = current.left;
            }

            if (node.right == current) {
                current.left = node.left;
            } else {
                parent.left = current.right;
                current.right = node.right;
                current.left = node.left;
            }

            result = current;
        }

        return result;
    }

    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {
        T result = null;

        if (!isEmpty()) {
            if (((Comparable) targetElement).equals(root.element)) {
                result = root.element;
                root = replacement(root);
                count--;
            } else {
                BinaryTreeNode<T> current, parent = root;
                boolean found = false;

                if (((Comparable) targetElement).compareTo(root.element) < 0) {
                    current = root.left;
                } else {
                    current = root.right;
                }

                while (current != null && !found) {
                    if (targetElement.equals(current.element)) {
                        found = true;
                        count--;
                        result = current.element;

                        if (current == parent.left) {
                            parent.left = replacement(current);
                        } else {
                            parent.right = replacement(current);
                        }
                    } else {
                        parent = current;

                        if (((Comparable) targetElement).compareTo(current.element) < 0) {
                            current = current.left;
                        } else {
                            current = current.right;
                        }
                    }
                }

                if (!found) {
                    throw new ElementNotFoundException("O elemento a remover não existe na arvore!");
                }
            }
        }

        return result;
    }

    @Override
    public void removeAllOccurences(T targetElement) {
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
        T result = null;

        if (!isEmpty()) {
            BinaryTreeNode<T> current = this.root;

            while (current != null) {
                if (current.left == null) {
                    result = current.element;
                }

                current = current.left;
            }
        } else {
            System.out.println("A arvore não possui nenhum elemento");
        }

        return result;
    }

    @Override
    public T findMax() {
        T result = null;

        if (!isEmpty()) {
            BinaryTreeNode<T> current = this.root;

            while (current != null) {
                if (current.right == null) {
                    result = current.element;
                }

                current = current.right;
            }
        } else {
            System.out.println("A arvore não possui nenhum elemento");
        }

        return result;
    }
}
