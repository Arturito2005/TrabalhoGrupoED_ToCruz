package Exceptions;

/**
 * Esta classe define uma exceção personalizada chamada
 * {@code ElementNotFoundException}.
 *
 * @author Artur Pinto
 * Nº mecanográfico: 8230138
 * @author Francisco Oliveira
 * Nº mecanográfico: 8230148
 * @version 1.0
 */
public class ElementNotFoundException extends RuntimeException {

    /**
     * Cria uma nova instância de <code>ElementNotFoundException</code> sem
     * mensagem detalhada.
     */
    public ElementNotFoundException() {
    }

    /**
     * Constrói uma instância de <code>ElementNotFoundException</code> com a
     * mensagem detalhada especificada.
     *
     * @param msg a mensagem detalhada.
     */
    public ElementNotFoundException(String msg) {
        super(msg);
    }
}
