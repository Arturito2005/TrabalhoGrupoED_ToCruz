package ArrayTree;

/**
 * Classe de teste para a implementação de uma árvore binária de busca (ArrayBinarySearchTree).
 *
 * Este programa testa a adição de elementos a uma árvore binária de busca, além de realizar operações de remoção,
 * como remover o mínimo e remover todas as ocorrências de um valor específico. A árvore é exibida após cada operação
 * para verificar os resultados.
 */
public class TestArrayBinarySearchTree {

    /**
     * Método principal que executa os testes na árvore binária de busca.
     *
     * Este método cria uma árvore binária de busca, adiciona elementos, realiza remoções (do mínimo e de todas as ocorrências)
     * e imprime o estado da árvore após cada operação. Ele também verifica a estrutura da árvore após inserções e remoções.
     *
     * @param args os argumentos da linha de comando (não são usados neste teste)
     */
    public static void main(String[] args) {
        ArrayBinarySearchTree<Integer> searchTree = new ArrayBinarySearchTree<>();

        /*
        searchTree.addElement(10);
        searchTree.addElement(1);
        searchTree.addElement(2);
        searchTree.addElement(3);
        searchTree.addElement(4);
        searchTree.addElement(5);
        searchTree.addElement(6);
        searchTree.addElement(7);
        searchTree.addElement(8);
        searchTree.addElement(9);
         */
        searchTree.addElement(10);
        searchTree.addElement(5);
        searchTree.addElement(15);
        searchTree.addElement(5);
        searchTree.addElement(13);
        searchTree.addElement(13);
        searchTree.addElement(5);
        searchTree.addElement(3);
        searchTree.addElement(15);

        System.out.println(searchTree.toString());

        searchTree.removeMin();

        searchTree.addElement(2);
        searchTree.addElement(3);

        System.out.println(searchTree.toString());

        searchTree.removeMin();

        System.out.println(searchTree.toString());

        searchTree.removeAllOccurences(15);

        System.out.println(searchTree.toString());

        searchTree.removeAllOccurences(5);

        System.out.println(searchTree.toString());
    }
}
