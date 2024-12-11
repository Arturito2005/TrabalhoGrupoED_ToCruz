package LinkedTree;

import Exceptions.ElementNotFoundException;
import Interfaces.BinarySearchTreeADT;

/**
 * Implementação de uma árvore binária de pesquisa (BST) utilizando uma árvore binária encadeada.
 * A classe oferece operações para adicionar, remover e buscar elementos, bem como métodos para
 * encontrar o menor e o maior elemento na árvore.
 *
 * @param <T> Tipo genérico dos elementos armazenados na árvore, que deve ser comparável.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class LinkedBinarySearchTree<T> extends LinkedBinaryTree<T> implements BinarySearchTreeADT<T> {

    /**
     * Constrói uma árvore binária de pesquisa vazia.
     */
    public LinkedBinarySearchTree() {
        super();
    }

    /**
     * Constrói uma árvore binária de pesquisa com um elemento raiz.
     *
     * @param element Elemento que será inserido como raiz da árvore.
     */
    public LinkedBinarySearchTree(T element) {
        super(element);
    }

    /**
     * Adiciona um elemento à árvore de pesquisa binária.
     *
     * @param element Elemento a ser adicionado à árvore.
     */
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

    /**
     * Encontra e substitui um nó na árvore binária de pesquisa, de acordo com as regras de remoção.
     *
     * @param node Nó a ser substituído.
     * @return O nó substituto após a remoção.
     */
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

    /**
     * Remove um elemento da árvore binária de pesquisa.
     *
     * @param targetElement Elemento a ser removido.
     * @return O elemento removido da árvore.
     * @throws ElementNotFoundException Se o elemento não for encontrado na árvore.
     */
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

    /**
     * Remove todas as ocorrências de um elemento da árvore.
     *
     * @param targetElement Elemento a ser removido.
     */
    @Override
    public void removeAllOccurences(T targetElement) {
        try {
            if (removeElement(targetElement) != null) {
                removeAllOccurences(targetElement);
            }
        } catch (ElementNotFoundException ex) {
        }
    }

    /**
     * Remove o menor elemento da árvore.
     *
     * @return O menor elemento removido da árvore.
     * @throws ElementNotFoundException Se a árvore estiver vazia.
     */
    @Override
    public T removeMin() throws ElementNotFoundException {
        return this.removeElement(findMin());
    }

    /**
     * Remove o maior elemento da árvore.
     *
     * @return O maior elemento removido da árvore.
     * @throws ElementNotFoundException Se a árvore estiver vazia.
     */
    @Override
    public T removeMax() throws ElementNotFoundException {
        return this.removeElement(findMax());
    }

    /**
     * Encontra o menor elemento na árvore.
     *
     * @return O menor elemento da árvore.
     */
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

    /**
     * Encontra o maior elemento na árvore.
     *
     * @return O maior elemento da árvore.
     */
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
