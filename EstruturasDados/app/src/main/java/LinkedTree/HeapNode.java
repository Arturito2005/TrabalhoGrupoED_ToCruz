package LinkedTree;

/**
 * Representa um nó em uma estrutura de heap (uma árvore binária), que contém um valor e uma referência para o seu nó pai.
 *
 * @param <T> Tipo genérico dos elementos armazenados na árvore, que deve ser comparável.
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class HeapNode<T> extends BinaryTreeNode<T> {

    /**
     * Referência para o nó pai do nó atual.
     */
    protected HeapNode<T> parent;

    /**
     * Cria um novo nó de heap com os dados especificados.
     * O nó é inicializado com o dado fornecido, e o nó pai é definido como {@code null}.
     *
     * @param obj o dado a ser armazenado no novo nó de heap
     */
    public HeapNode(T obj) {
        super(obj);
        parent = null;
    }
}
