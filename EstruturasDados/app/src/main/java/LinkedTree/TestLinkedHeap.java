package LinkedTree;

import Interfaces.HeapADT;

/**
 * Classe de teste para a implementação de um heap utilizando lista encadeada.
 *
 * Este programa testa a funcionalidade de um heap, implementado por meio de
 * uma lista encadeada, com operações de adição e remoção de elementos.
 * Os elementos são adicionados ao heap e o mínimo é removido e impresso.
 */
public class TestLinkedHeap {

    /**
     * Método principal que executa os testes no heap.
     *
     * Este método cria um heap de inteiros, adiciona elementos a ele, e
     * testa a remoção do mínimo, imprimindo o estado do heap após cada operação.
     *
     * @param args os argumentos da linha de comando (não são usados neste teste)
     */
    public static void main(String[] args) {
        HeapADT<Integer> heap = new LinkedHeap<>();
        
        heap.addElement(5);
        heap.addElement(4);
        heap.addElement(3);
        heap.addElement(8);
        heap.addElement(7);
        heap.addElement(9);
        
        System.out.println(heap.toString());
        heap.addElement(2);
        System.out.println(heap.toString());
        System.out.println(heap.removeMin());
        System.out.println(heap.toString());
        System.out.println(heap.removeMin());
        System.out.println(heap.toString());
    }
    
}
