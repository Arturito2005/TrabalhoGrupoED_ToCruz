package LinkedTree;

/**
 * Representa um nó em uma árvore binária. Cada nó contém um elemento e referências para os seus filhos esquerdo e direito.
 *
 * @param <T> o tipo de dado armazenado no nó
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class BinaryTreeNode<T> {

    /**
     * O elemento armazenado no nó.
     */
    protected T element;

    /**
     * Referência para o filho esquerdo do nó e o filho da direita.
     */
    protected BinaryTreeNode<T> left, right;


    /**
     * Constrói um nó binário com o elemento {@code null} e sem filhos (esquerdo e direito são {@code null}).
     */
    public BinaryTreeNode() {
        this.element = null;
        this.left = null;
        this.right = null;
    }

    /**
     * Constrói um nó binário com o elemento especificado e sem filhos (esquerdo e direito são {@code null}).
     *
     * @param obj o dado a ser armazenado no nó
     */
    public BinaryTreeNode(T obj) {
        this.element = obj;
        this.left = null;
        this.right = null;
    }

    /**
     * Retorna o número total de filhos (direito e esquerdo) deste nó, contando recursivamente os filhos dos filhos.
     *
     * @return o número total de filhos do nó
     */
    public int numChildren() {
        int numChildren = 0;

        if (this.left != null) {
            numChildren = 1 + this.left.numChildren();
        }

        if (this.right != null) {
            numChildren = numChildren + 1 + this.right.numChildren();
        }

        return numChildren;
    }
}
