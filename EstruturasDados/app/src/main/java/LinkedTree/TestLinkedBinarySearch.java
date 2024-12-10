package LinkedTree;

import Interfaces.BinarySearchTreeADT;

/**
 * Classe de teste para a implementação de uma Árvore Binária de Pesquisa (BST) utilizando lista encadeada.
 *
 * Este programa testa a funcionalidade de uma árvore binária de pesquisa, realizando operações de
 * inserção de elementos, remoção do mínimo, e remoção de todas as ocorrências de determinados valores.
 * Após cada operação, o estado da árvore é impresso.
 */
public class TestLinkedBinarySearch {

    /**
     * Método principal que executa os testes na árvore binária de pesquisa.
     *
     * Este método cria uma árvore binária de pesquisa, insere elementos nela, remove o mínimo,
     * adiciona novos elementos e remove todas as ocorrências de valores específicos, imprimindo
     * o estado da árvore após cada operação.
     *
     * @param args os argumentos da linha de comando (não são usados neste teste)
     */
    public static void main(String[] args) {
        BinarySearchTreeADT<Integer> searchTree = new LinkedBinarySearchTree<>();

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
