package Exceptions;

/**
 * Esta classe define uma exceção personalizada chamada
 * <code>EmptyCollectionException</code>, que é lançada quando uma operação
 * é realizada em uma coleção vazia.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class EmptyCollectionException extends RuntimeException {

    /**
     * Cria uma nova instância de <code>EmptyCollectionException</code> sem
     * mensagem detalhada.
     */
    public EmptyCollectionException() {
    }

    /**
     * Constrói uma instância de <code>EmptyCollectionException</code> com a
     * mensagem detalhada especificada.
     *
     * @param msg a mensagem detalhada.
     */
    public EmptyCollectionException(String msg) {
        super(msg);
    }
}
